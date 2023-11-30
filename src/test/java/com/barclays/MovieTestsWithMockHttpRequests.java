package com.barclays;

import com.barclays.model.Movie;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings("All")
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

        int expectedLength = 9;

        ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.get("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        Movie[] movies = mapper.readValue(contentAsString, Movie[].class);

        assertAll("Testing movies endpoint",
                () -> assertEquals(expectedLength, movies.length),
                () -> assertEquals("Goodfellas", movies[1].getTitle()),
                () -> assertEquals("Casino", movies[2].getTitle()),
                () -> assertEquals("Al Pacino", movies[3].getLeadActor()),
                () -> assertEquals(1997, movies[4].getReleaseYear()),
                () -> assertEquals(7.4, movies[5].getRating()));

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

    @Test
    @Rollback
    public void testUpdateMovie() throws Exception {


        ObjectMapper mapper;


        Movie movie = new Movie();
        movie.setTitle("Harry Potter and The Chamber");
        movie.setDirector("JK Rowling");
        movie.setGenre("Fantasy");
        movie.setReleaseYear(2007);


        mapper = new ObjectMapper();

        resultActions = this.mockMvc.perform(MockMvcRequestBuilders.put("/movies")
                        .content(mapper.writeValueAsString(movie))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        movie = mapper.readValue(contentAsString, Movie.class);

        assertEquals("Harry Potter and The Chamber", movie.getTitle());
    }

    @Test
    @Rollback
    public void testDeleteMovie() throws Exception {



        ObjectMapper mapper;
        mapper = new ObjectMapper();


        Movie movie = new Movie();
        movie.setTitle("Harry Potter and The Chamber");
        movie.setDirector("JK Rowling");
        movie.setGenre("Fantasy");
        movie.setReleaseYear(2007);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/movies")
                        .content(mapper.writeValueAsString(movie))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        resultActions =  this.mockMvc.perform(MockMvcRequestBuilders.delete("/movies")
                        .content(mapper.writeValueAsString(movie))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        MvcResult result = resultActions.andReturn();

        String contentAsString = result.getResponse().getContentAsString();

        System.out.println("Content as string" + contentAsString);

        Assertions.assertTrue(contentAsString.equals(""));
    }


}
