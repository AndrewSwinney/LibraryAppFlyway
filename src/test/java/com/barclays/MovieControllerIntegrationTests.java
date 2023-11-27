package com.barclays;


import com.barclays.controller.MovieController;
import com.barclays.model.Movie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class MovieControllerIntegrationTests {

    @Autowired
    MovieController movieController;

    @Test
    void testGetAllMovies() {
        List<Movie> movies = movieController.getAllMovies("", "");
        assertEquals(8, movies.size());
    }

    @Test
    void testFilterByGenreCrime() {
        List<Movie> movies = movieController.getAllMovies("", "Crime");
        assertEquals(4, movies.size());
    }

    @Test
    void testFilterByTitleContains() {
        List<Movie> movies = movieController.getAllMovies("Goodfellas", "");
        String actualTitle = "";
        for (Movie movie: movies
        ) {
            actualTitle = movie.getTitle();
        }

        assertEquals("Goodfellas", actualTitle);
    }


    @Test
    void testGetMovieByGenreRomance() {
        List<Movie> movies = movieController.getAllMovies("", "Romance");

        String actualTitle = "";

        for (Movie movie : movies) {

            actualTitle = movie.getTitle();
        }

        assertEquals("Titanic", actualTitle);
    }

    @Test
    void testInvalidGenre() {
        List<Movie> movies = movieController.getAllMovies("", "Funny");
        assertEquals(0, movies.size());
    }

    @Test
    void testGetMovie1() {
        Movie movie = movieController.getMovie(2);
        assertEquals("Titanic", movie.getTitle());
    }

    @Test
    void testGetMovie2() {
        Movie movie = movieController.getMovie(2);
        assertEquals("Titanic", movie.getTitle());
    }

    @Test
    void testGetMovie2AssertByDirector() {
        Movie movie = movieController.getMovie(2);
        assertEquals("Titanic", movie.getTitle());
    }


}
