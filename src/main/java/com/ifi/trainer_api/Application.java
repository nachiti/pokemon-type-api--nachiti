package com.ifi.trainer_api;

import com.ifi.trainer_api.bo.Pokemon;
import com.ifi.trainer_api.bo.Trainer;
import com.ifi.trainer_api.repository.TrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Application {
    @Bean
    @Autowired
    public CommandLineRunner demo(TrainerRepository repository) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        return (args) -> {
            Trainer ash = new Trainer("Ash");
            Pokemon pikachu = new Pokemon(25, 18);
            List<Pokemon> pokemonListAsh=new ArrayList<>();
            pokemonListAsh.add(pikachu);

            ash.setTeam(pokemonListAsh);
            ash.setPassword(bCryptPasswordEncoder.encode("ash_password"));

            Trainer misty = new Trainer("Misty");
            Pokemon staryu = new Pokemon(120, 18);
            Pokemon starmie = new Pokemon(121, 21);
            List<Pokemon> pokemonList=new ArrayList<>();
            pokemonList.add(staryu);
            pokemonList.add(starmie);
            misty.setTeam(pokemonList);
            misty.setPassword(bCryptPasswordEncoder.encode("misty_password"));

            // save a couple of trainers
            repository.save(ash);
            repository.save(misty);
        };
    }

    public static void main(String... args){
        SpringApplication.run(Application.class, args);
    }
}
