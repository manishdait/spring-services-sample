# spring-services-samples

Sample applications showing how to use [spring-services](https://github.com/OpenElementsLabs/spring-services) module.  The samples target **spring-services 2.x**, which runs on **Spring Boot 4.1**.

## Getting started

```bash
git clone https://github.com/manishdait/spring-services-sample
cd spring-services-sample

# run docker compose
docker compose up -d

# run the sample
./mvnw -pl <package-name> spring-boot:run
# eg.
./mvnw -pl core spring-boot:run

# build and run all tests
./mvnw verify
```

## Samples Guides
- **[Spring Services Core Sample Guide](core/guide.md)**.
