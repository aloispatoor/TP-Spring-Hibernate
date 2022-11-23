package com.netflixwish.demo.repository;

import com.netflixwish.demo.entity.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MovieRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(MovieRepository.class);
    @PersistenceContext
    EntityManager entityManager;
//     API qui est utilisée pour créer et supprimer des instances de peristance de données
//     pour trouver les entités avec leur clef primaire (id)
//     ou même pour faire des requêtes via nos entités
    @Transactional
    public void persist(Movie movie){
        LOGGER.trace("EM : " + entityManager.contains(movie));
        entityManager.persist(movie);
        LOGGER.trace("EM : " + entityManager.contains(movie));
    }

    @Transactional
    public Movie updateMovie(Movie movie){
        return entityManager.merge(movie);
    }

    @Transactional
    public void delete(Long id){
        Movie movie = entityManager.find(Movie.class, id);
        entityManager.remove(movie);
    }

//    PAS BESOIN DE TRANSACTION CAR AUCUNE MODIF EN BDD
    public Movie find(Long id){
        Movie movie = entityManager.find(Movie.class, id);
        LOGGER.trace("EM : " + entityManager.contains(movie));
        return movie;
    }

    public List<Movie> findAll(){
//      SYNTAXE JPQL / ATTENTION A LA MAJUSCULE AU NOM DE LA TABLE
        String jpql = "SELECT m FROM Movie m";
        TypedQuery<Movie> query = entityManager.createQuery(jpql, Movie.class);

        return query.getResultList();
    }
}
