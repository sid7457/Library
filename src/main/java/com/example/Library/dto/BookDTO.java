package com.example.Library.dto;

import com.example.Library.model.Book;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookDTO {
    private Long id;
    private String title;
    private String isbn;
    private Integer publishedYear;
    private String authorName;
    private Book.Status availabilityStatus;
}
