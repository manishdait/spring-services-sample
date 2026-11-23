package com.example.servicesample.core.book;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import org.jspecify.annotations.NonNull;
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
@RequestMapping("/api/v1/public/books")
@Tag(name = "Book Entity", description = "API for managing books")
public class BookController {
  private final BookDataService bookService;

  public BookController(@NonNull BookDataService bookService) {
    this.bookService = bookService;
  }

  @GetMapping
  @Operation(summary = "Get Books", description = "Fetch all the available books")
  public ResponseEntity<List<BookDto>> getAllBooks() {
    return ResponseEntity.status(HttpStatus.OK).body(bookService.getAll());
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get Book by ID", description = "Fetch book by id")
  public ResponseEntity<BookDto> getBook(@PathVariable UUID id) {
    return ResponseEntity.status(HttpStatus.OK).body(bookService.getByIdOrNotFound(id));
  }

  @PostMapping
  @Operation(summary = "Create Book", description = "Create a new book")
  public ResponseEntity<BookDto> createBook(@RequestBody BookDto request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(bookService.save(request));
  }

  @PutMapping("/{id}")
  @Operation(summary = "Update Book", description = "Update book details")
  public ResponseEntity<BookDto> updateBook(@PathVariable UUID id, @RequestBody BookDto request) {
    var updateRequest = new BookDto(id, request.title(), request.author(), request.isbn());
    return ResponseEntity.status(HttpStatus.CREATED).body(bookService.save(updateRequest));
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete Book", description = "Delete book by id")
  public ResponseEntity<Void> deleteBook(@PathVariable UUID id) {
    bookService.delete(id);
    return ResponseEntity.status(HttpStatus.CREATED).body(null);
  }
}
