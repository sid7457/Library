package com.example.Library.service;

import com.example.Library.dto.BookCreateRequest;
import com.example.Library.dto.BookDTO;
import com.example.Library.dto.BookUpdateRequest;
import org.springframework.data.domain.Page;


import java.util.List;
import java.util.Optional;

public interface BookService {
    BookDTO createBook(BookCreateRequest request);
    Page<BookDTO> getBooks(Optional<String> author, Optional<Integer> publishedYear,
                           Optional<String> availabilityStatus, int page, int size);
    BookDTO getBookById(Long id);
    BookDTO updateBook(Long id, BookUpdateRequest request);
    void deleteBook(Long id);
    List<BookDTO> searchBooksByTitleOrAuthor(String query);

    List<BookDTO> createBooks(List<BookCreateRequest> requests);
}
