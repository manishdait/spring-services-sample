package com.example.springservices.coredb;

import com.openelements.spring.base.data.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Custom Ticket entity that extend the AbstractEntity from spring-services. Provides id (UUID),
 * createdAt (Instance), updatedAt (Instance)
 */
@Entity
@Table(name = "ticket")
public class Ticket extends AbstractEntity {
  @Column(name = "title", nullable = false, length = 100)
  private String title;

  @Column(name = "description", nullable = false)
  private String description;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
