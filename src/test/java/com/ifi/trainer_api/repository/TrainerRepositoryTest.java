package com.ifi.trainer_api.repository;

import com.ifi.trainer_api.bo.Pokemon;
import com.ifi.trainer_api.bo.Trainer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
@DataJpaTest
public class TrainerRepositoryTest {

    @Autowired
    private TrainerRepository repository;

    @Test
    void trainerRepository_shouldExtendsCrudRepository() throws NoSuchMethodException {
        assertTrue(CrudRepository.class.isAssignableFrom(TrainerRepository.class));
    }

    @Test
    void trainerRepositoryShouldBeInstanciedBySpring(){
        assertNotNull(repository);
    }

    @Test
    void testSave(){
        Trainer ash = new Trainer("Ash");

        repository.save(ash);

        Trainer saved = repository.findById(ash.getName()).orElse(null);

        assertEquals("Ash", saved.getName());
    }

    @Test
    void testSaveWithPokemons(){
        Trainer misty = new Trainer("Misty");
        Pokemon staryu = new Pokemon(120, 18);
        Pokemon starmie = new Pokemon(121, 21);
        List<Pokemon> list=new ArrayList<>();
        list.add(staryu);
        list.add(starmie);
        misty.setTeam(list);

        repository.save(misty);

        Trainer saved = repository.findById(misty.getName()).orElse(null);

        assertEquals("Misty", saved.getName());
        assertEquals(2, saved.getTeam().size());
    }

}