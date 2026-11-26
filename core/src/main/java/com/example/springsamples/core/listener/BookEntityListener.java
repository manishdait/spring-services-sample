package com.example.springsamples.core.listener;

import com.example.springsamples.core.dao.BookDto;
import com.openelements.spring.base.events.OnObjectCreate;
import com.openelements.spring.base.events.OnObjectDelete;
import com.openelements.spring.base.events.OnObjectUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class BookEntityListener {
  private static final Logger log = LoggerFactory.getLogger(BookEntityListener.class);

  @EventListener
  public void onCreated(OnObjectCreate<BookDto> event) {
    log.info("Book entity created with id: {}", event.entityId());
  }

  @EventListener
  public void onUpdate(OnObjectUpdate<BookDto> event) {
    log.info("Book entity updated with id: {}", event.entityId());

    if (event.isPreviousKnown()) {
      log.info("Previous State: {}", event.getPrevious());
    }
  }

  @EventListener
  public void afterDelete(OnObjectDelete<BookDto> event) {
    log.info("Book entity deleted with id: {}", event.entityId());
  }
}
