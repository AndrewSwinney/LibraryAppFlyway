package com.barclays.controller;

import com.barclays.model.Movie;
import com.barclays.service.MovieService;
import jakarta.websocket.server.PathParam;
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

    @GetMapping
    public List<Movie> getAllMovies(@PathParam("filter") String filter,
                                    @PathParam("genre") String genre) {
        if (filter != null && !filter.isEmpty()) {
            return movieService.findByMovieTitleContains(filter);
        } else if (genre != null && !genre.isEmpty()) {
            return movieService.findByGenreContains(genre);
        }
        return movieService.findAllMovies();
    }

    @GetMapping("/{id}")
    public Movie getMovie(@PathVariable long id) {
        return movieService.findByMovieId(id);
    }

    @PutMapping
    public Movie updateMessage(@RequestBody Movie movie){
        return movieService.save(movie);
    }

    @DeleteMapping
    public void deleteByMovie(@RequestBody Movie movie){
        movieService.delete(movie);
    }
}

