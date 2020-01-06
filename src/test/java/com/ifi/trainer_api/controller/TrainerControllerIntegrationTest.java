package com.ifi.trainer_api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifi.trainer_api.bo.Pokemon;
import com.ifi.trainer_api.bo.Trainer;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TrainerControllerIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TrainerController controller;

    @Value("${spring.security.user.name}")
    private String username;

    @Value("${spring.security.user.password}")
    private String password;

    @Test
    void trainerController_shouldBeInstanciated(){
        assertNotNull(controller);
    }



    @Test
    void getTrainer_withNameAsh_shouldReturnAsh() {
        Trainer ash = this.restTemplate.withBasicAuth(username, password).getForObject("http://localhost:" + port + "/trainers/Ash", Trainer.class);
        assertNotNull(ash);
        assertEquals("Ash", ash.getName());
        assertEquals(1, ash.getTeam().size());

        assertEquals(25, ash.getTeam().get(0).getPokemonType());
        assertEquals(18, ash.getTeam().get(0).getLevel());
    }

    @Test
    void getAllTrainers_shouldReturnAshAndMisty() {
        Trainer[] trainers = this.restTemplate.withBasicAuth(username, password).getForObject("http://localhost:" + port + "/trainers/", Trainer[].class);
        assertNotNull(trainers);
        assertEquals(2, trainers.length);

        assertEquals("Ash", trainers[0].getName());
        assertEquals("Misty", trainers[1].getName());
  }

    @Test
    void deleteTrainer_shouldDeleteAshAndMisty() {
        Trainer[] trainers = this.restTemplate.withBasicAuth(username, password).getForObject("http://localhost:" + port + "/trainers/", Trainer[].class);
        assertNotNull(trainers);
        assertEquals(2, trainers.length);

        this.restTemplate.withBasicAuth(username, password).delete("http://localhost:" + port + "/trainers/Ash");
        trainers = this.restTemplate.withBasicAuth(username, password).getForObject("http://localhost:" + port + "/trainers/", Trainer[].class);
        assertEquals("Misty", trainers[0].getName());
        assertEquals(1, trainers.length);


        //remettre ash dans la base de données
        Trainer ash = new Trainer("Ash");

        Pokemon pikachu = new Pokemon(25, 18);
        List<Pokemon> listPokemonOfAsh=new ArrayList<>();
        listPokemonOfAsh.add(pikachu);
        ash.setTeam(listPokemonOfAsh);
        this.restTemplate.withBasicAuth(username, password).postForObject("http://localhost:" + port + "/trainers/",ash,Trainer.class);

    }

    @Test
    void AddTrainer_ShouldChangeSizeOfGetAllTrainers() {
        Trainer[] trainers = this.restTemplate.withBasicAuth(username, password).getForObject("http://localhost:" + port + "/trainers/", Trainer[].class);
        assertNotNull(trainers);

        Trainer newTrainer = new Trainer("New");
        Pokemon pikachu = new Pokemon(11, 18);
        List<Pokemon> listPokemonOfAsh = new ArrayList<>();
        listPokemonOfAsh.add(pikachu);
        newTrainer.setTeam(listPokemonOfAsh);

        this.restTemplate.withBasicAuth(username, password).postForObject("http://localhost:" + port + "/trainers/", newTrainer, Trainer.class);

        Trainer newTrainerGet = this.restTemplate.withBasicAuth(username, password).getForObject("http://localhost:" + port + "/trainers/New", Trainer.class);
        assertNotNull(newTrainerGet);

        //enlever New de la base de données
        this.restTemplate.withBasicAuth(username, password).delete("http://localhost:" + port + "/trainers/New");
    }
}
