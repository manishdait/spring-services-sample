package com.example.springservices.coredb;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Ticket", description = "API for managing ticket")
public class TicketController {
  private final TicketService ticketService;

  public TicketController(TicketService ticketService) {
    this.ticketService = ticketService;
  }

  @GetMapping
  @Operation(summary = "Get all Tickets", description = "Fetch all the available tickets")
  @ApiResponse(responseCode = "200", description = "Tickets found successfully")
  public ResponseEntity<List<TicketDao>> findAll() {
    return ResponseEntity.status(HttpStatus.OK).body(ticketService.getAll());
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get Ticket by ID", description = "Fetch ticket by Id")
  @ApiResponse(responseCode = "200", description = "Ticket found successfully")
  @ApiResponse(responseCode = "404", description = "Ticket not found")
  public ResponseEntity<TicketDao> findById(@PathVariable UUID id) {
    return ResponseEntity.status(HttpStatus.OK).body(ticketService.findByIdOrThrow(id));
  }

  @PostMapping
  @Operation(summary = "Create Tickets", description = "Create a new ticket")
  @ApiResponse(responseCode = "201", description = "Ticket created successfully")
  public ResponseEntity<TicketDao> createTicket(@RequestBody TicketDao request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(ticketService.save(request));
  }

  @PutMapping("/{id}")
  @Operation(summary = "Update Tickets", description = "Update a ticket")
  @ApiResponse(responseCode = "200", description = "Ticket updated successfully")
  public ResponseEntity<TicketDao> updateTicket(
      @PathVariable UUID id, @RequestBody TicketDao request) {
    var updateRequest = new TicketDao(id, request.title(), request.description());
    return ResponseEntity.status(HttpStatus.OK).body(ticketService.save(updateRequest));
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete Tickets", description = "Delete a new ticket")
  @ApiResponse(responseCode = "200", description = "Ticket deleted successfully")
  public ResponseEntity<Void> deleteTicket(@PathVariable UUID id) {
    ticketService.delete(id);
    return ResponseEntity.status(HttpStatus.OK).body(null);
  }
}
