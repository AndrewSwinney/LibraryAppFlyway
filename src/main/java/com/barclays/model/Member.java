package com.barclays.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq")
    @SequenceGenerator(
            name = "member_seq",
            sequenceName = "member_seq",
            initialValue = 1,
            allocationSize = 1
    )

    private Long id;
    private String name;

    private String emailAddress;

    public Member(String name, String emailAddress) {
        this.name = name;
        this.emailAddress = emailAddress;
        List<Movie> movies = new ArrayList<>();
        List<Book> books = new ArrayList<>();
    }

    @JsonManagedReference
    @JoinColumn(name = "member_id")
    @OneToMany
    private List<Book> books;

    @JsonManagedReference
    @JoinColumn(name = "member_id")
    @OneToMany
    private List<Movie> movies;
}