package com.barclays.service;

import com.barclays.model.Book;
import com.barclays.model.Movie;
import org.springframework.stereotype.Service;

import java.util.List;


public interface MovieService {
    Movie createMovie(Movie movie);

    List<Movie> findAllMovies();

    Movie findByMovieId(int id);

    List<Movie> findByMovieTitleContains(String filter);

    List<Movie> findByGenreContains(String genre);

    Movie save(Movie m);

    void delete(Movie movie);


}
