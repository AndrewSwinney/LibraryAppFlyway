package com.barclays.service;

import com.barclays.model.Book;
import com.barclays.model.Movie;
import com.barclays.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public Movie createMovie(Movie movie) {
        return movieRepository.save(movie);

    }

    @Override
    public List<Movie> findAllMovies() {
        List<Movie> movies = new ArrayList<>();
        movieRepository.findAll().forEach(movies::add);
        return movies;
    }

    @Override
    public Movie findByMovieId(long id) {
        Optional<Movie> movies = movieRepository.findById((long) id);
        return movies.orElseGet(() -> new Movie("Default Member: Nothing found"));
    }

    @Override
    public List<Movie> findByMovieTitleContains(String filter) {
        return movieRepository.findByTitleContains(filter);
    }

    @Override
    public List<Movie> findByGenreContains(String genre) {
        return movieRepository.findByGenreContains(genre);
    }

    public void delete(Movie movie) {
        movieRepository.delete(movie);
    }

    @Override
    public Movie save(Movie m){
        return movieRepository.save(m);
    }
}
