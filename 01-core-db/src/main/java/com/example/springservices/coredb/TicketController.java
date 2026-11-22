package com.example.springservices.coredb;

import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Rest endpoint for the entity. */
@RestController
@RequestMapping("/api/v1/ticket")
public class TicketController {
  private final TicketService ticketService;

  public TicketController(TicketService ticketService) {
    this.ticketService = ticketService;
  }

  @GetMapping
  public ResponseEntity<List<TicketDao>> findAll() {
    return ResponseEntity.status(HttpStatus.OK).body(ticketService.getAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<TicketDao> findById(@PathVariable UUID id) {
    return ResponseEntity.status(HttpStatus.OK).body(ticketService.findByIdOrThrow(id));
  }

  @PostMapping
  public ResponseEntity<TicketDao> createTicket(@RequestBody TicketDao request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(ticketService.save(request));
  }

  @PutMapping("/{id}")
  public ResponseEntity<TicketDao> updateTicket(
      @PathVariable UUID id, @RequestBody TicketDao request) {
    var updateRequest = new TicketDao(id, request.title(), request.description());
    return ResponseEntity.status(HttpStatus.OK).body(ticketService.save(updateRequest));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTicket(@PathVariable UUID id) {
    ticketService.delete(id);
    return ResponseEntity.status(HttpStatus.OK).body(null);
  }
}
