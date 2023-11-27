package com.barclays;


import com.barclays.controller.BookController;
import com.barclays.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class MemberControllerIntegrationTests {

    @Autowired
    BookController bookController;

    @Test
    public void testGetAllBooks() {
        List<Book> books = bookController.getAllBooks("", "", "");
        assertEquals(5, books.size());
    }

    @Test
    public void testGetBook1() {
        Book book = bookController.getBook(1);
        assertEquals("Remains of The Day", book.getTitle());
    }

    @Test
    void testFilterBookByGenre() {
        List<Book> books = bookController.getAllBooks("", "", "Romance");
        assertEquals(1, books.size());
    }

    @Test
    void testFilterBookByTitle() {
        List<Book> books = bookController.getAllBooks("Remains", "", "");
        System.out.println(books.size());
        String actualTitle = "";

        for (Book book : books) {

            actualTitle = book.getTitle();
        }
        assertEquals("Remains of The Day", actualTitle);
    }
}
