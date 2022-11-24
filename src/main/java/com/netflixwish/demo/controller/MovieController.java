package com.netflixwish.demo.controller;

import com.netflixwish.demo.entity.Movie;
import com.netflixwish.demo.repository.MovieRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MovieController {
    private final MovieRepository repository;

    public MovieController(MovieRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/movies")
    List<Movie> all(){
        return repository.findAll();
    }

    @PostMapping("/movies")
    Movie newMovie(@RequestBody Movie newMovie){
        return repository.persist(newMovie);
    }

    @GetMapping("/movie/{id}")
    Movie movie(@PathVariable Long id){
        return repository.find(id);
    }

    @PutMapping("/movies/{id}")
    Movie updateMovie(@RequestBody Movie newMovie, @PathVariable Long id){
        return repository.find(id)
                .map(movie -> {
                    movie.setName(newMovie.getName());
                    movie.setDescription(newMovie.getDescription());
                    return repository.persist(movie);
                })
                .orElseGet(() -> {
                    newMovie.setId(id);
                    return repository.persist(newMovie);
                });
    }

    @DeleteMapping("/movies/{id}")
    void deleteMovie(@PathVariable Long id){
        repository.delete(id);
    }


}
