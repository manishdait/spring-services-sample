package com.example.springsamples.core.dao;

import com.example.springsamples.core.entity.BookEntity;
import com.openelements.spring.base.data.WithId;
import java.util.UUID;
import org.jspecify.annotations.Nullable;

public record BookDto(UUID id, String title, String author, String isbn) implements WithId {
  @Override
  public @Nullable UUID id() {
    return this.id;
  }

  public static BookDto from(BookEntity book) {
    return new BookDto(book.id(), book.getTitle(), book.getAuthor(), book.getIsbn());
  }
}
