package com.barclays;

import com.barclays.model.Book;
import com.barclays.model.Movie;
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
public class MovieTestsWithMockHttpRequests {

    @Autowired
    MockMvc mockMvc;
    ResultActions resultActions;

    ObjectMapper mapper = new ObjectMapper();

    @Test
    void testGettingAllMembers() throws Exception {

        int expectedLength = 8;

        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.get("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        Movie[] movies = mapper.readValue(contentAsString, Movie[].class);

        assertAll("Testing movies endpoint",
                () -> assertEquals(expectedLength, movies.length),
                () -> assertEquals("Titanic", movies[1].getTitle()),
                () -> assertEquals("Goodfellas", movies[2].getTitle()),
                () -> assertEquals("Robert De Niro", movies[3].getLeadActor()),
                () -> assertEquals(1983, movies[4].getReleaseYear()),
                () -> assertEquals(8.3, movies[5].getRating()));

    }

    @Test
    @Rollback
    public void testCreateMovie() throws Exception {


        ObjectMapper mapper;


        Movie movie = new Movie();
        movie.setTitle("Harry Potter and The Chamber of Secrets");
        movie.setDirector("JK Rowling");
        movie.setGenre("Fantasy");
        movie.setReleaseYear(2007);


        mapper = new ObjectMapper();

        resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post("/movies")
                        .content(mapper.writeValueAsString(movie))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isCreated());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        movie = mapper.readValue(contentAsString, Movie.class);

        assertEquals("Harry Potter and The Chamber of Secrets", movie.getTitle());
    }


}
