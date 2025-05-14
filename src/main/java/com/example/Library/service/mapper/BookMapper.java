package com.example.Library.service.mapper;

import com.example.Library.dto.BookCreateRequest;
import com.example.Library.dto.BookDTO;
import com.example.Library.model.Author;
import com.example.Library.model.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public BookDTO toDto(Book book) {
        if (book == null) return null;

        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setIsbn(book.getIsbn());
        dto.setPublishedYear(book.getPublishedYear());
        dto.setAuthorName(book.getAuthor().getName());
        dto.setAvailabilityStatus(book.getAvailabilityStatus());
        return dto;
    }

    public Book toEntity(BookCreateRequest request, Author author) {
        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setIsbn(request.getIsbn());
        book.setPublishedYear(request.getPublishedYear());
        book.setAuthor(author);
        book.setAvailabilityStatus(Book.Status.AVAILABLE); // default
        return book;
    }
}
