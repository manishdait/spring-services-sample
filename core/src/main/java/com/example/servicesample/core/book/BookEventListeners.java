package com.example.servicesample.core.book;

import com.openelements.spring.base.events.OnObjectCreate;
import com.openelements.spring.base.events.OnObjectDelete;
import com.openelements.spring.base.events.OnObjectUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/** EventListener for the BookEntity */
@Component
public class BookEventListeners {
  private static final Logger LOG = LoggerFactory.getLogger(BookEventListeners.class);

  @EventListener
  public void onCreated(OnObjectCreate<BookDto> event) {
    LOG.info("Book created: {}", event.getData());
  }

  @EventListener
  public void onUpdate(OnObjectUpdate<BookDto> event) {
    LOG.info("Book updated:");
    if (event.getPrevious().isPresent()) {
      LOG.info("\tPrevious Entry: {}", event.getPrevious().get());
    }

    LOG.info("\tNew Entry: {}", event.getData());
  }

  @EventListener
  public void afterDelete(OnObjectDelete<BookDto> event) {
    LOG.info("Book deleted: {}", event.getData());
  }
}
