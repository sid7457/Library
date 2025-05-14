package com.example.Library.service.impl;

import com.example.Library.dto.BookCreateRequest;
import com.example.Library.dto.BookDTO;
import com.example.Library.dto.BookUpdateRequest;
import com.example.Library.model.Author;
import com.example.Library.model.Book;
import com.example.Library.service.BookService;
import com.example.Library.service.mapper.BookMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import com.example.Library.repository.AuthorRepository;
import com.example.Library.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookMapper bookMapper; // For converting between DTO and entity

    @Override
    public BookDTO createBook(BookCreateRequest request) {
        if (bookRepository.existsByIsbn(request.getIsbn())) {
            throw new IllegalArgumentException("Duplicate ISBN");
        }

        Author author = authorRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new EntityNotFoundException("Author not found"));

        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setIsbn(request.getIsbn());
        book.setPublishedYear(request.getPublishedYear());
        book.setAuthor(author);
        book.setAvailabilityStatus(Book.Status.AVAILABLE); // default
        book.setDescription(request.getDescription());
        book.setGenre(request.getGenre());

        Book saved = bookRepository.save(book);
        return bookMapper.toDto(saved);
    }


    @Override
    public Page<BookDTO> getBooks(Optional<String> author, Optional<Integer> publishedYear,
                                  Optional<String> availabilityStatus, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Specification<Book> spec = Specification.where(null);

        if (author.isPresent() && !author.get().isBlank()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("author").get("name")), "%" + author.get().toLowerCase() + "%"));
        }

        if (publishedYear.isPresent()) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("publishedYear"), publishedYear.get()));
        }

        if (availabilityStatus.isPresent() && !availabilityStatus.get().isBlank()) {
            try {
                Book.Status statusEnum = Book.Status.valueOf(availabilityStatus.get().toUpperCase());
                spec = spec.and((root, query, cb) ->
                        cb.equal(root.get("availabilityStatus"), statusEnum));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid availabilityStatus: " + availabilityStatus.get());
            }
        }

        // This is Page<Book>
        Page<Book> bookPage = bookRepository.findAll(spec, pageable);
        return bookPage.map(bookMapper::toDto);
    }

    @Override
    public BookDTO getBookById(Long id) {
        return bookRepository.findById(id)
                .map(bookMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));
    }

    @Override
    public BookDTO updateBook(Long id, BookUpdateRequest request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));

        if (request.getTitle() != null) book.setTitle(request.getTitle());
        if (request.getPublishedYear() != null) book.setPublishedYear(request.getPublishedYear());
        if (request.getAvailabilityStatus() != null) book.setAvailabilityStatus(request.getAvailabilityStatus());

        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("Book not found");
        }
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookDTO> searchBooksByTitleOrAuthor(String query) {
        return bookRepository.searchByTitleOrAuthor(query.toLowerCase())
                .stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }
    @Override
    public List<BookDTO> createBooks(List<BookCreateRequest> requests) {
        return requests.stream()
                .map(this::createBook)
                .collect(Collectors.toList());
    }
}

