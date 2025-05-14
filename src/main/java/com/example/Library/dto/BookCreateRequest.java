package com.example.Library.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookCreateRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String isbn;

    @NotNull
    private Integer publishedYear;

    @NotNull
    private Long authorId;

    private String genre;
    private String description;
}
