package com.example.springservices.coredb;

import com.openelements.spring.base.data.AbstractDbBackedDataService;
import com.openelements.spring.base.data.EntityRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * TickerService that use the AbstractDbBackedService form the spring-services core. Provides: -
 * getAll() - findById() - save()
 */
@Service
public class TicketService extends AbstractDbBackedDataService<Ticket, TicketDao> {
  private final TicketRepository ticketRepository;

  public TicketService(
      TicketRepository ticketRepository, @NonNull ApplicationEventPublisher eventPublisher) {
    super(eventPublisher);
    this.ticketRepository = ticketRepository;
  }

  @Override
  protected @NonNull Ticket createDetachedEntity() {
    return new Ticket();
  }

  @Override
  protected void updateEntity(@NonNull Ticket entity, @NonNull TicketDao data) {
    entity.setTitle(data.title());
    entity.setDescription(data.description());
  }

  @Override
  protected @NonNull TicketDao toData(@NonNull Ticket entity) {
    return new TicketDao(entity.getId(), entity.getTitle(), entity.getDescription());
  }

  @Override
  protected @NonNull EntityRepository<Ticket> getRepository() {
    return this.ticketRepository;
  }
}
