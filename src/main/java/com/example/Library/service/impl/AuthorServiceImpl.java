package com.example.Library.service.impl;

import com.example.Library.dto.AuthorDTO;
import com.example.Library.model.Author;
import com.example.Library.repository.AuthorRepository;
import com.example.Library.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    private AuthorDTO mapToDto(Author author) {
        return AuthorDTO.builder()
                .id(author.getId())
                .name(author.getName())
                .bio(author.getBiography())
                .build();
    }

    private Author mapToEntity(AuthorDTO dto) {
        return Author.builder()
                .id(dto.getId())
                .name(dto.getName())
                .biography(dto.getBio())
                .build();
    }

    @Override
    public AuthorDTO createAuthor(AuthorDTO dto) {
        Author saved = authorRepository.save(mapToEntity(dto));
        return mapToDto(saved);
    }

    @Override
    public AuthorDTO getAuthorById(Long id) {
        return authorRepository.findById(id)
                .map(this::mapToDto)
                .orElseThrow(() -> new RuntimeException("Author not found with id " + id));
    }

    @Override
    public List<AuthorDTO> getAllAuthors() {
        return authorRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public AuthorDTO updateAuthor(Long id, AuthorDTO dto) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found with id " + id));

        author.setName(dto.getName());
        return mapToDto(authorRepository.save(author));
    }

    @Override
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }
}
