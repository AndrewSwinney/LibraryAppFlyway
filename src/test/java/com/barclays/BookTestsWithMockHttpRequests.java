package com.barclays;

import com.barclays.model.Book;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestPropertySource(properties = {"spring.sql.init.mode=never"})
@Transactional

public class BookTestsWithMockHttpRequests {

    @Autowired
    MockMvc mockMvc;
    ResultActions resultActions;

    ObjectMapper mapper = new ObjectMapper();

    @Test
    void testGettingAllBooks() throws Exception {

        int expectedLength = 5;

        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                         .andExpect(MockMvcResultMatchers.status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        Book[] books = mapper.readValue(contentAsString, Book[].class);

        assertAll("Testing books endpoint",
                () -> assertEquals(expectedLength, books.length),
                () -> assertEquals("Remains of The Day", books[0].getTitle()),
                () -> assertEquals("Doctor Sleep", books[1].getTitle()));
    }

    @Test
    @Rollback
    public void testCreateBook() throws Exception {


        ObjectMapper mapper;


        Book book = new Book();
        book.setTitle("Harry Potter and The Chamber of Secrets");
        book.setAuthor("JK Rowling");
        book.setGenre("Fantasy");
        book.setYearPublished(1998);
        book.setIsbn(1237);


        mapper = new ObjectMapper();

        resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post("/books")
                        .content(mapper.writeValueAsString(book))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isCreated());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        book = mapper.readValue(contentAsString, Book.class);

        assertEquals("Harry Potter and The Chamber of Secrets", book.getTitle());
    }


}