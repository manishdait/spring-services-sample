package com.example.springservices.coredb;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Rest endpoint for the entity. */
@RestController
@RequestMapping("/api/v1/public")
public class TicketController {
  private final TicketService ticketService;

  public TicketController(TicketService ticketService) {
    this.ticketService = ticketService;
  }

  @GetMapping
  public ResponseEntity<List<TicketDao>> findAllTickets() {
    return ResponseEntity.status(HttpStatus.OK).body(ticketService.getAll());
  }
}
