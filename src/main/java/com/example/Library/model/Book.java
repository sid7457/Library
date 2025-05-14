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
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(unique = true, nullable = false)
    private String isbn;

    private Integer publishedYear;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    private String genre;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status availabilityStatus = Status.AVAILABLE;
    public enum Status {
        AVAILABLE,
        BORROWED,
        RESERVED
    }
}

