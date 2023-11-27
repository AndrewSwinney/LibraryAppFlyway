package com.barclays.service;

import com.barclays.model.Book;
import com.barclays.model.Member;
import org.springframework.stereotype.Service;

import java.util.List;


public interface BookService {

    Book createBook(Book book);

    List<Book> findAllBooks();

    Book findByBookId(int id);

    List<Book> findByTitleContains(String filter);

    List<Book> findByAuthorContains(String author);

    List<Book> findByGenreContains(String genre);

    Book save(Book b);

     void delete(Book book);
}

