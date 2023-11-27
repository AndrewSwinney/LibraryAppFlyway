package com.barclays.controller;

import com.barclays.model.Book;
import com.barclays.model.Member;
import com.barclays.model.Movie;
import com.barclays.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie) {
        Movie createdMovie = movieService.createMovie(movie);
        return new ResponseEntity<>(createdMovie, HttpStatus.CREATED);
    }

    @PutMapping
    public Movie updateMovie(@RequestBody Movie movie) {
        return movieService.save(movie);
    }

    @GetMapping
    public List<Movie> getAllMovies(@RequestParam(required = false) String filter,
                                    @RequestParam(required = false) String genre) {
        if (filter != null && !filter.isEmpty()) {
            return movieService.findByMovieTitleContains(filter);
        } else if (genre != null && !genre.isEmpty()) {
            return movieService.findByGenreContains(genre);
        }
        return movieService.findAllMovies();
    }

    @GetMapping("/{id}")
    public Movie getMovie(@PathVariable int id) {
        return movieService.findByMovieId(id);
    }

    @DeleteMapping
    public void deleteByMovie(@RequestBody Movie movie){
        movieService.delete(movie);
    }
}

