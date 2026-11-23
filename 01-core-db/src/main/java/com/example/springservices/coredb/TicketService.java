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
public class TicketService extends AbstractDbBackedDataService<TicketEntity, TicketDao> {
  private final TicketRepository ticketRepository;

  public TicketService(
      TicketRepository ticketRepository, @NonNull ApplicationEventPublisher eventPublisher) {
    super(eventPublisher);
    this.ticketRepository = ticketRepository;
  }

  @Override
  protected @NonNull TicketEntity createDetachedEntity() {
    return new TicketEntity();
  }

  @Override
  protected void updateEntity(@NonNull TicketEntity entity, @NonNull TicketDao data) {
    if (data.title() != null) {
      entity.setTitle(data.title());
    }
    if (data.description() != null) {
      entity.setDescription(data.description());
    }
  }

  @Override
  protected @NonNull TicketDao toData(@NonNull TicketEntity entity) {
    return new TicketDao(entity.getId(), entity.getTitle(), entity.getDescription());
  }

  @Override
  protected @NonNull EntityRepository<TicketEntity> getRepository() {
    return this.ticketRepository;
  }
}
