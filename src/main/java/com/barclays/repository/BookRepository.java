package com.barclays.repository;

import com.barclays.model.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

    List<Book> findByTitleContains(String filter);

    List<Book> findByAuthorContains(String author);

    List<Book> findByGenreContains(String genre);
}
