package com.example.springsamples.core.service;

import com.example.springsamples.core.dao.BookDto;
import com.example.springsamples.core.entity.BookEntity;
import com.example.springsamples.core.repository.BookRepository;
import com.openelements.spring.base.data.AbstractDbBackedDataService;
import com.openelements.spring.base.data.EntityRepository;
import com.openelements.spring.base.problem.ProblemException;
import com.openelements.spring.base.problem.StandardProblemType;
import java.util.Objects;
import org.jspecify.annotations.NonNull;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class BookService extends AbstractDbBackedDataService<BookEntity, BookDto> {
  private final BookRepository repository;

  public BookService(
      @NonNull BookRepository repository, @NonNull ApplicationEventPublisher eventPublisher) {
    Objects.requireNonNull(repository, "repository must not be null");
    Objects.requireNonNull(eventPublisher, "eventPublisher must not be null");

    super(eventPublisher);
    this.repository = repository;
  }

  @Override
  protected @NonNull BookEntity createDetachedEntity() {
    return new BookEntity();
  }

  @Override
  protected void updateEntity(@NonNull BookEntity entity, @NonNull BookDto data) {
    Objects.requireNonNull(entity, "entity must not be null");
    Objects.requireNonNull(data, "data must not be null");

    entity.setId(data.id());
    entity.setAuthor(data.author());
    entity.setTitle(data.title());
    entity.setIsbn(data.isbn());
  }

  @Override
  protected @NonNull BookDto toData(@NonNull BookEntity entity) {
    Objects.requireNonNull(entity, "entity must not be null");
    return BookDto.from(entity);
  }

  @Override
  protected @NonNull EntityRepository<BookEntity> getRepository() {
    return this.repository;
  }

  /* Hook */
  @Override
  protected void preSave(@NonNull final BookDto data) {
    if (data.isbn().length() != 13) {
      throw new ProblemException(
          StandardProblemType.BAD_REQUEST, "ISBN number should contain 13 digits");
    }
  }
}
