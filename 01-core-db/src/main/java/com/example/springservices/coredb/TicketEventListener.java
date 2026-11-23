package com.example.springservices.coredb;

import com.openelements.spring.base.events.OnObjectCreate;
import com.openelements.spring.base.events.OnObjectDelete;
import com.openelements.spring.base.events.OnObjectUpdate;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class TicketEventListener {
  @EventListener
  public void onTicketCreated(OnObjectCreate<TicketDao> event) {
    System.out.println("Ticket Created....");
    System.out.println(event.getData());
  }

  @EventListener
  public void onTicketUpdate(OnObjectUpdate<TicketDao> event) {
    System.out.println("Ticket Updated....");
    System.out.println("Previous: " + event.getPrevious());
    System.out.println("New: " + event.getData());
  }

  @EventListener
  public void onTicketDelete(OnObjectDelete<TicketDao> event) {
    System.out.println("Ticket Deleted....");
    System.out.println("Previous: " + event.getData());
  }
}
