package com.example.Library.service.mapper;

import com.example.Library.dto.AuthorDTO;
import com.example.Library.model.Author;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {

    public AuthorDTO toDto(Author author) {
        return AuthorDTO.builder()
                .id(author.getId())
                .name(author.getName())
                .bio(author.getBiography())
                .build();
    }

    public Author toEntity(AuthorDTO dto) {
        return Author.builder()
                .id(dto.getId())
                .name(dto.getName())
                .biography(dto.getBio())
                .build();
    }
}
