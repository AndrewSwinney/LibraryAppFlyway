package com.barclays.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_seq")
    @SequenceGenerator(

            name = "book_seq",
            sequenceName = "book_seq",
            initialValue = 1,
            allocationSize = 1)

    private Long id;

    private int isbn;

    private String genre;

    private String title;

    private String author;

    private int yearPublished;

    public Book(String title) {
        this.title = title;
    }

    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;


}
