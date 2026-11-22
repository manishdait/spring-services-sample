package com.example.springservices.coredb;

import com.openelements.spring.base.data.WithId;
import java.util.UUID;
import org.jspecify.annotations.Nullable;

/**
 * Ticket Dao to help with the Ticket creation and update.
 *
 * @param id of ticket should be null for creat.
 * @param title of ticket
 * @param description for ticket
 */
public record TicketDao(UUID id, String title, String description) implements WithId {
  @Override
  public @Nullable UUID id() {
    return this.id;
  }
}
