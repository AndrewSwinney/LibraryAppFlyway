package com.barclays.model;

import jakarta.persistence.*;
import lombok.*;

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

    private int id;
    private String name;

    private String emailAddress;

    public Member(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "member")
    private List<Book> books;

    @OneToMany(mappedBy = "member")
    private List<Movie> movies;
}