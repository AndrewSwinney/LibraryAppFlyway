package com.barclays.service;

import com.barclays.model.Book;
import com.barclays.model.Member;
import com.barclays.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book createBook(Book book) {
        return bookRepository.save(book);

    }


    @Override
    public List<Book> findAllBooks() {
        List<Book> books = new ArrayList<>();
        bookRepository.findAll().forEach(books::add);
        return books;
    }

    @Override
    public Book findByBookId(long id) {
        Optional<Book> book = bookRepository.findById((long) id);
        return book.orElseGet(() -> new Book("Default Message: Nothing found"));
    }

    @Override
    public List<Book> findByTitleContains(String filter) {
        return bookRepository.findByTitleContains(filter);
    }

    @Override
    public List<Book> findByAuthorContains(String author) {
        return bookRepository.findByAuthorContains(author);
    }

    @Override
    public List<Book> findByGenreContains(String genre) {
        return bookRepository.findByGenreContains(genre);
    }

    public void delete(Book book) {
        bookRepository.delete(book);
    }

    @Override
    public Book save(Book book){
        return bookRepository.save(book);
    }
}
