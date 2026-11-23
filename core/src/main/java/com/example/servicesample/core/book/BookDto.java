package com.example.servicesample.core.book;

import com.openelements.spring.base.data.WithId;
import java.util.UUID;
import org.jspecify.annotations.Nullable;

/**
 * BookDto to help with the book creation and update.
 *
 * @param id the book's id; {@code null} when creating, set when updating or reading
 * @param title the book's title, required
 * @param author the author's name, required
 * @param isbn the ISBN-10 or ISBN-13, digits only
 */
public record BookDto(@Nullable UUID id, String title, String author, String isbn)
    implements WithId {
  @Override
  public @Nullable UUID id() {
    return this.id;
  }
}
