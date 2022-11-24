package com.netflixwish.demo.service;

import com.netflixwish.demo.entity.Movie;
import com.netflixwish.demo.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository repository;

    @Transactional
    public void updateDescription(Long id, String description){
        Movie movie = repository.find(id);
        movie.setDescription(description);
//        FAIT L'UPDATE AUTOMATIQUEMENT
    }

}
