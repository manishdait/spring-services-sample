package com.example.servicesample.core.book;

import com.openelements.spring.base.data.AbstractEntity;
import com.openelements.spring.base.data.image.EntityWithImage;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Custom BookEntity that extend the AbstractEntity from spring-services. Provides id (UUID),
 * createdAt (Instance), updatedAt (Instance)
 */
@Entity
@Table(name = "book")
public class BookEntity extends AbstractEntity implements EntityWithImage {
  @Column(name = "title", nullable = false, length = 100)
  private String title;

  @Column(name = "author", nullable = false, length = 50)
  private String author;

  @Column(name = "isbn", unique = true, length = 17)
  private String isbn;

  @Column(name = "cover_image")
  private byte[] rawImageData;

  @Column(name = "cover_content_type", length = 50)
  private String contentType;

  public BookEntity() {}

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getIsbn() {
    return isbn;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  // Override EntityWithImage methods

  @Override
  public byte[] getRawImageData() {
    return rawImageData;
  }

  @Override
  public void setRawImageData(byte[] rawImageData) {
    this.rawImageData = rawImageData;
  }

  @Override
  public String getContentType() {
    return contentType;
  }

  @Override
  public void setContentType(String contentType) {
    this.contentType = contentType;
  }
}
