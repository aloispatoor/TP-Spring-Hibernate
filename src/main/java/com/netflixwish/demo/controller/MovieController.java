package com.netflixwish.demo.controller;

import com.netflixwish.demo.entity.Movie;
import com.netflixwish.demo.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    private MovieRepository repository;

    @PostMapping("/")
    public Movie create(@RequestBody Movie movie){
        repository.persist(movie);
        return movie;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> get(@PathVariable Long id){
        Movie movie = repository.find(id);
        return movie != null ? ResponseEntity.ok(movie) : ResponseEntity.notFound().build();
    }

    @PutMapping("/update")
    public ResponseEntity<Movie> update(@RequestBody Movie movie){
        Optional<Movie> result = repository.updateMovie(movie);
        return result.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(result.get());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Movie> delete(@PathVariable Long id){
        boolean isRemoved = repository.delete(id);
        return isRemoved ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }


}
