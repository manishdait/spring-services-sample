package com.example.servicesample.core.book;

import com.openelements.spring.base.data.AbstractDbBackedDataService;
import com.openelements.spring.base.data.EntityRepository;
import com.openelements.spring.base.problem.ProblemException;
import com.openelements.spring.base.problem.StandardProblemType;
import java.util.UUID;
import org.jspecify.annotations.NonNull;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * BookService that use the AbstractDbBackedService form the spring-services core. Provides:
 * getAll(), findById(), save()
 */
@Service
public class BookDataService extends AbstractDbBackedDataService<BookEntity, BookDto> {
  @NonNull private final BookRepository bookRepository;

  public BookDataService(
      @NonNull BookRepository bookRepository, @NonNull ApplicationEventPublisher eventPublisher) {
    super(eventPublisher);
    this.bookRepository = bookRepository;
  }

  @Override
  protected @NonNull BookEntity createDetachedEntity() {
    return new BookEntity();
  }

  @Override
  protected void updateEntity(@NonNull BookEntity entity, @NonNull BookDto data) {
    entity.setTitle(data.title());
    entity.setAuthor(data.author());
    entity.setIsbn(data.isbn());
  }

  @Override
  protected @NonNull BookDto toData(@NonNull BookEntity entity) {
    return new BookDto(entity.id(), entity.getTitle(), entity.getAuthor(), entity.getIsbn());
  }

  @Override
  protected @NonNull EntityRepository<BookEntity> getRepository() {
    return bookRepository;
  }

  // custom methods

  @Transactional(readOnly = true)
  public BookDto getByIdOrNotFound(UUID id) {
    return findById(id)
        .orElseThrow(
            () -> new ProblemException(StandardProblemType.NOT_FOUND, "No book with id " + id));
  }
}
