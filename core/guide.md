# spring-services-core — Sample Guide

## Overview
`spring-services-core` is a Spring Boot starter that add the dependency and you get JWT + API-key security, a generic CRUD data layer with lifecycle events, tags, settings, comments, an audit log, webhooks, RFC 9457 problem details and a few utilities.

## What core module provides: (WIP)

| Feature | Entry point(s) | Package | 
| :--- | :--- | :--- |
| Generic data layer | `AbstractEntity`, `EntityRepository`, `WithId`, `AbstractDbBackedDataService` | `data` |
| Lifecycle events | `OnObjectCreate`, `OnObjectUpdate` (has `getPrevious()`), `OnObjectDelete` | `events` |
| Security | `SecurityConfig` (two filter chains) | `security` |
 | Problem details | `ProblemException`, `ProblemType`, `StandardProblemType` | `problem` |
 | DB health | `DbHealthService.isDatabaseReachable()` | `data |`
| Images | `ImageData`, `ImageType`, `EntityWithImage` | `data.image` |

## Setup

### 1. Maven

```xml
<!-- Parent POM.xml -->
  <parent>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-parent</artifactId>
  <version>4.1.0</version>
  </parent>

<properties>
  ...
</properties>

<dependencyManagement>
  <dependencies>
    <dependency>
      <groupId>com.open-elements</groupId>
      <artifactId>spring-services-bom</artifactId>
      <version>2.0.0</version>
      <type>pom</type>
      <scope>import</scope>
    </dependency>
  </dependencies>
</dependencyManagement>
```

```xml
<!-- Child POM.xml -->
<dependencies>
  <dependency>
    <groupId>com.open-elements</groupId>
    <artifactId>spring-services-core</artifactId>
  </dependency>
  <dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
  </dependency>
  <!-- optional, for real migrations -->
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-flyway</artifactId>
  </dependency>
</dependencies>
```

Core already brings `spring-boot-starter-webmvc`, `-security`, `-data-jpa`,
`-validation` and `-security-oauth2-resource-server`.


### 2. Application class

```java
   @SpringBootApplication
   public class Application {
     static void main(String[] args) {
       SpringApplication.run(Application.class, args);
     }
   }
```
No `@Import`, `@EntityScan` or `@EnableJpaRepositories` is needed.
`SpringServicesCoreAutoConfiguration` appends com.openelements.spring.base to the auto-configuration packages, so your entities and the library's are both scanned.

### 3. application.yml
```yml
spring:
  application:
    name: spring-service-sample-core
  datasource:
    driver-class-name: org.postgresql.Driver
    url: jdbc:postgresql://localhost:5432/service_db
    username: postgres
    password: password
  jpa:
    database: postgresql
    hibernate:
      ddl-auto: update
    properties:
      jakarta:
        persistence:
          create-database-schemas: true

  #  TDOD: temporary workaround mock values, need to dive deep
  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri: https://your-idp.example.com/realms/demo

server:
  port: 8081
```

---

## The generic data layer (Book CRUD)

### Entity

```java
@Entity
@Table(name = "books")              // your own schema, not oe_spring_services
public class BookEntity extends AbstractEntity {   // UUID id, createdAt, updatedAt

  @Column(nullable = false) private String title;
  @Column(nullable = false) private String author;
  @Column private Integer year;

  // getters + setters
}
```

### Repository

```java
public interface BookRepository extends EntityRepository<BookEntity> {   // JpaRepository<E, UUID>
  List<BookEntity> findByAuthorIgnoreCase(String author);
}

// EntityRepository adds findByIdOrThrow(UUID) and findAllByIds(Iterable<UUID>) (a Stream).
```

### DTO
```java
public record BookDto(
  UUID id,
  String title, 
  String author,
  ...) implements WithId { }

// Keep DTOs as records with copied collections (List.copyOf). The service compares the before/after DTO with equals; an update that changes nothing publishes no event.
```

### Data service
```java
@Service
public class BookDataService extends AbstractDbBackedDataService<BookEntity, BookDto> {

  private final BookRepository repository;

  public BookDataService(BookRepository repository, ApplicationEventPublisher events) {
    super(events);              // super(events, false) disables lifecycle events
    this.repository = repository;
  }

  @Override protected BookEntity createDetachedEntity() {
    return new BookEntity();
  }

  @Override protected void updateEntity(BookEntity e, BookDto d) {
    e.setTitle(d.title());
    e.setAuthor(d.author());
    e.setYear(d.year());
  }

  @Override protected BookDto toData(BookEntity e) {
    return new BookDto(e.getId(), e.getTitle(), e.getAuthor(), e.getYear());
  }
  
  @Override protected EntityRepository<BookEntity> getRepository() {
    return repository;
  }
  
  ...
}


// The class is @Transactional. Methods you inherit: save, getAll, findAll(Pageable), findById(UUID|String), findByIdOrThrow, existsById, delete(UUID|String|D). Other hooks: postInsert, postUpdate(previous, current), postDelete; call super if you override them, or events stop.
```

### Controller

```java
@RestController
@RequestMapping("/api/books")
class BookController {

    private final BookDataService books;
    BookController(BookDataService books) { this.books = books; }


    @GetMapping
    public ResponseEntity<List<BookDto>> getAllBooks() {
      return ResponseEntity.status(HttpStatus.OK).body(bookService.getAll());
    }

    @GetMapping("/{id}")
    BookDto get(@PathVariable UUID id) {
      return books.findById(id)
      .orElseThrow(() -> new ProblemException(StandardProblemType.NOT_FOUND, "No book " + id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    BookDto create(@Valid @RequestBody BookDto body) {
        return books.save(new BookDto(null, body.title(), body.author(), body.year()));
    }

    @PutMapping("/{id}")
    BookDto update(@PathVariable UUID id, @Valid @RequestBody BookDto body) {
        if (!books.existsById(id)) throw new ProblemException(StandardProblemType.NOT_FOUND);
        return books.save(new BookDto(id, body.title(), body.author(), body.year()));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable UUID id) {
    if (!books.existsById(id)) throw new ProblemException(StandardProblemType.NOT_FOUND);
    books.delete(id);
    }
}
// Check existence first: the data service throws IllegalArgumentException for an unknown id, which the problem layer renders as a 500 internal-error, not a 404.
```

### Images on an entity

```java
@Entity
@Table(name = "authors")
public class AuthorEntity extends AbstractEntity implements EntityWithImage {
    @Lob @Column(name = "image") private byte[] rawImageData;
    @Column(name = "image_type") private String contentType;
    // getRawImageData / setRawImageData / getContentType / setContentType
}
```

---

## Lifecycle events
Every AbstractDbBackedDataService publishes OnObjectCreate, OnObjectUpdate and OnObjectDelete synchronously, inside the transaction. Listeners are generic-typed, so OnObjectUpdate<BookDto> only fires for books (the library's own TypedEventListenerTest proves this).

```java
@Component
class BookEventListeners {

private static final Logger LOG = LoggerFactory.getLogger(BookEventListeners.class);

  // 1. In-transaction: throwing here rolls back the save
  @EventListener
  void onCreate(OnObjectCreate<BookDto> event) {
    LOG.info("Book created: {} ({})", event.getData().title(), event.entityId());
  }

  // 2. Previous state: present when the data service did the update
  @EventListener
  void onUpdate(OnObjectUpdate<BookDto> event) {
    event.getPrevious().ifPresent(before -> {
    if (!Objects.equals(before.title(), event.getData().title())) {
      LOG.info("Renamed '{}' -> '{}'", before.title(), event.getData().title());
      }
    });
  }

  // 3. After commit: safe for e-mails, cache eviction, external calls
  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  void afterDelete(OnObjectDelete<BookDto> event) {
    LOG.info("Book {} is gone for good", event.entityId());
  }

  // 4. Every type, every action
  @EventListener
  void any(GenericDataEvent<?> event) {
    LOG.debug("{} on {}", event.getClass().getSimpleName(), event.getType().getSimpleName());
  }
}

// Useful accessors: getData() (the DTO after the change), getType() (DTO class), entityId(), and on updates isPreviousKnown() / getPrevious(). You can publish your own: publisher.publishEvent(new OnObjectUpdate<>(before, after)); the constructor rejects mismatched classes or ids.

// The library itself listens to these events: the audit log records every create/update/delete, and webhooks fire after commit. Your DTOs therefore show up in both automatically.
```