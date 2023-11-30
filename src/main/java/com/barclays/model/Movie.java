package com.barclays.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movie_seq")
    @SequenceGenerator(
            name = "movie_seq",
            sequenceName = "movie_seq",
            initialValue = 1,
            allocationSize = 1
    )

    private Long id;
    private String title;
    private String leadActor;
    private String director;
    private String screenWriter;
    private int releaseYear;
    private String genre;
    private double rating;

    public Movie(String title) {
        this.title = title;
    }

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;
}
