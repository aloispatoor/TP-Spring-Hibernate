package com.netflixwish.demo.repository;

import com.netflixwish.demo.entity.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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
//        LOGGER.trace("EM : " + entityManager.contains(movie));
        entityManager.persist(movie);
//        LOGGER.trace("EM : " + entityManager.contains(movie));
    }

    @Transactional
    public Optional<Movie> updateMovie(Movie movie){
        if(movie.getId() == null){
            return Optional.empty();
        }
        Movie updatedMovie = entityManager.find(Movie.class, movie.getId());
        if(updatedMovie != null){
            updatedMovie.setName(movie.getName());
            updatedMovie.setDescription(movie.getDescription());
        }
        return Optional.ofNullable(updatedMovie);
    }

    @Transactional
    public void delete(Long id){
        // LE CACHE DE 1er NIVEAU PERMET, SI ONT REPETE LE FIND() ICI, DE NE FAIRE QU'UN SELECT
        Movie movie = entityManager.find(Movie.class, id);
        entityManager.remove(movie);
    }
    @Transactional
    public Movie getReference(Long id){
        // RECUPERE UNE REFERENCE CHARGEABLE A LA DEMANDE DE LA SESSION
        Movie movie = entityManager.getReference(Movie.class, id);
        LOGGER.trace("MOVIE NAME:  " + movie.getName());
        return movie;
    }
    public Movie getReference2(Long id){
        //DES CHOSES SE PASSENT
        //AH OUI, ESSAI DES ASSERT THROWS / LAZY EXCEPTION
        //ÇA SERT A RIEN SINON
        return entityManager.getReference(Movie.class, id);
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
