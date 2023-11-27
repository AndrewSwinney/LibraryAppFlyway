package com.barclays.repository;

import com.barclays.model.Movie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MovieRepository extends CrudRepository<Movie, Long> {

    List<Movie> findByTitleContains(String filter);

    List<Movie> findByGenreContains(String filter);
}
