package com.example.Library.dto;

import com.example.Library.model.Book;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookUpdateRequest {
    private String title;
    private Integer publishedYear;
    private Book.Status availabilityStatus;
}
