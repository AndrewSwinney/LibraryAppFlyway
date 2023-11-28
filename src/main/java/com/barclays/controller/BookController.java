package com.barclays.controller;

import com.barclays.model.Book;
import com.barclays.service.BookService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Book createdBook = bookService.createBook(book);
        return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Book> getAllBooks(@PathParam("filter") String filter,
                                  @PathParam("author") String author,
                                  @PathParam("genre") String genre) {
        if (filter != null && !filter.isEmpty()) {
            return bookService.findByTitleContains(filter);
        } else if (author != null && !author.isEmpty()) {
            return bookService.findByAuthorContains(author);
        } else if (genre != null && !genre.isEmpty()) {
            return bookService.findByGenreContains(genre);
        }
        return bookService.findAllBooks();
    }

    @GetMapping("/{id}")
    public Book getBook(@PathVariable int id) {
        return bookService.findByBookId(id);
    }

    @PutMapping
    public Book updateBook(@RequestBody Book book){
        return bookService.save(book);
    }

    @DeleteMapping
    public void deleteByBook(@RequestBody Book book){
        bookService.delete(book);
    }
}

