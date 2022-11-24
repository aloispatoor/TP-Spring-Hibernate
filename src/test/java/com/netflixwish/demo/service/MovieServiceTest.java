package com.netflixwish.demo.service;

import com.netflixwish.demo.config.PersistenceConfigTest;
import com.netflixwish.demo.entity.Movie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {PersistenceConfigTest.class})
@SqlConfig(dataSource = "dataSourceH2", transactionManager = "transactionManager")
@Sql({"/datas/data-test.sql"})
public class MovieServiceTest {
    @Autowired
    private MovieService service;

    @Test
    public void updateDescription_nominalCase(){
        service.updateDescription(-2L, "C'est un film d'horreur quoi");
    }
}
