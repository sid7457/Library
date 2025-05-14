package com.example.Library.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookCopy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Book book;

    @Enumerated(EnumType.STRING)
    private Status status; // AVAILABLE, BORROWED, RESERVED

    public enum Status {
        AVAILABLE, BORROWED, RESERVED
    }
}
