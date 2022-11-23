package com.netflixwish.demo.repository;

import com.netflixwish.demo.config.PersistenceConfigTest;
import com.netflixwish.demo.entity.Movie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {PersistenceConfigTest.class})
@SqlConfig(dataSource = "dataSourceH2", transactionManager = "transactionManager")
@Sql({"/datas/data-test.sql"}) // Charge le fichier qui insère les données en BDD
public class MovieRepositoryTest {

    @Autowired
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
        movies.forEach(System.out::println);
    }

    @Test
    public void edit_nominalCase(){
        Movie existingMovie = new Movie();

        existingMovie.setId(-1L);
        existingMovie.setName("Silent Hill");

        Movie updatedMovie = repository.updateMovie(existingMovie);

        System.out.println(updatedMovie.getName());
    }

    @Test
    public void delete_nominalCase(){
        Long id = -3L;
        repository.delete(id);
        System.out.println("Remove ok");
    }
}
