package com.example.springsamples.core.controller;

import com.example.springsamples.core.dao.BookDto;
import com.example.springsamples.core.service.BookService;
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

@RestController
@RequestMapping("/api/books")
public class BookController {
  private final BookService bookService;

  public BookController(BookService bookService) {
    this.bookService = bookService;
  }

  @GetMapping
  public ResponseEntity<List<BookDto>> getAllBooks() {
    return ResponseEntity.status(HttpStatus.OK).body(bookService.getAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<BookDto> getBookById(@PathVariable UUID id) {
    return ResponseEntity.status(HttpStatus.OK).body(bookService.findByIdOrThrow(id));
  }

  @PostMapping
  public ResponseEntity<BookDto> createBook(@RequestBody BookDto request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(bookService.save(request));
  }

  @PutMapping("/{id}")
  public ResponseEntity<BookDto> updateBook(@PathVariable UUID id, @RequestBody BookDto request) {
    var updateRequest = new BookDto(id, request.title(), request.author(), request.isbn());
    return ResponseEntity.status(HttpStatus.OK).body(bookService.save(updateRequest));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteBook(@PathVariable UUID id) {
    bookService.delete(id);
    return ResponseEntity.status(HttpStatus.OK).body(null);
  }
}
