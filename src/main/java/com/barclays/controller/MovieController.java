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


    // POST endpoint to create movies
    @PostMapping
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie) {
        Movie createdMovie = movieService.createMovie(movie);
        return new ResponseEntity<>(createdMovie, HttpStatus.CREATED);
    }


    // GET endpoint to retrieve all movies, movies can be filtered by title and genre
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


    // GET endpoint to retrieve movies by their id
    @GetMapping("/{id}")
    public Movie getMovie(@PathVariable long id) {
        return movieService.findByMovieId(id);
    }




    // PUT endpoint to update existing movies
    @PutMapping
    public Movie updateMessage(@RequestBody Movie movie){
        return movieService.save(movie);
    }


    // DELETE endpoint to delete movies
    @DeleteMapping
    public void deleteByMovie(@RequestBody Movie movie){
        movieService.delete(movie);
    }
}

