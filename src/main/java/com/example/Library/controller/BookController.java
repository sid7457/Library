package com.example.Library.controller;

import com.example.Library.dto.BookCreateRequest;
import com.example.Library.dto.BookDTO;
import com.example.Library.dto.BookUpdateRequest;
import com.example.Library.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@Validated
public class BookController {

    private final BookService bookService;

    // Create a new book
    @PostMapping
    public ResponseEntity<BookDTO> createBook(@Valid @RequestBody BookCreateRequest request) {
        BookDTO created = bookService.createBook(request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<BookDTO>> createBooks(@Valid @RequestBody List<BookCreateRequest> requests) {
        List<BookDTO> createdBooks = bookService.createBooks(requests);
        return new ResponseEntity<>(createdBooks, HttpStatus.CREATED);
    }

    // Get books with optional filters and pagination
    @GetMapping
    public Page<BookDTO> getBooks(
            @RequestParam Optional<String> author,
            @RequestParam Optional<Integer> publishedYear,
            @RequestParam Optional<String> availabilityStatus,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return bookService.getBooks(author, publishedYear, availabilityStatus, page, size);
    }

    // Get a book by ID
    @GetMapping("/{id}")
    public BookDTO getBook(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    // Update book info (e.g., availability)
    @PutMapping("/{id}")
    public BookDTO updateBook(@PathVariable Long id, @Valid @RequestBody BookUpdateRequest request) {
        return bookService.updateBook(id, request);
    }

    // Delete a book
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    // Search by title or author
    @GetMapping("/search")
    public List<BookDTO> searchBooks(@RequestParam String query) {
        return bookService.searchBooksByTitleOrAuthor(query);
    }


}
