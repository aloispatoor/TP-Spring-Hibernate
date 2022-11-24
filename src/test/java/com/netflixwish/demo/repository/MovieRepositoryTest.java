package com.netflixwish.demo.repository;

import com.netflixwish.demo.config.PersistenceConfigTest;
import com.netflixwish.demo.entity.Movie;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {PersistenceConfigTest.class})
@SqlConfig(dataSource = "dataSourceH2", transactionManager = "transactionManager")
@Sql({"/datas/data-test.sql"}) // Charge le fichier qui insère les données en BDD
public class MovieRepositoryTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MovieRepository.class);
    @Autowired // Injection de dépendance
    private MovieRepository repository;

    @Test
    public void save_nominalCase(){
        Movie movie = new Movie();
        movie.setName("Training Day");
        repository.persist(movie);
        System.out.println("Test1 end");
    }

    @Test
    public void find_nominalCase(){
        Movie movie = repository.find(-2L);
        assertThat(movie.getName()).as("Pas le bon film").isEqualTo("Cannibal Holocaust");
    }

    @Test
    public void find_allCases(){
        List<Movie> movies = repository.findAll();
        assertThat(movies).as("Not all movies was found").hasSize(3);
        movies.forEach(System.out::println);
    }

    @Test
    public void edit_nominalCase(){
        Movie existingMovie = new Movie();

        existingMovie.setId(-1L);
        existingMovie.setName("Silent Hill");

        Movie updatedMovie = repository.updateMovie(existingMovie);
        assertThat(updatedMovie.getName()).as("Movie not found").isEqualTo("Silent Hill");
        System.out.println(updatedMovie.getName());
    }

    @Test
    public void delete_nominalCase(){
        Long id = -3L;
        repository.delete(id);
//        VERIF S'IL A BIEN UN FILM EN MOINS DANS LA BDD
        List<Movie> movies = repository.findAll();
        assertThat(movies).as("The film wasn't deleted").hasSize(2);
        System.out.println("Remove ok");
    }

    @Test
    public void getReference_nominalCase(){
        Movie movie = repository.getReference(-2L);
        assertThat(movie.getId()).as("Reference didn't load correctly").isEqualTo(-2);
    }

    @Test
    public void getReference2_nominalCase(){
        assertThrows(LazyInitializationException.class, () -> {
            Movie movie = repository.getReference2(-2L);
            LOGGER.trace("MOVIE NAME:  " + movie.getName());
        }, "Didn't get the right exception");
    }
}
