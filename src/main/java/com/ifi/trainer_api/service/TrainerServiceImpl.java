package com.ifi.trainer_api.service;

import com.ifi.trainer_api.bo.Trainer;
import com.ifi.trainer_api.repository.TrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class TrainerServiceImpl implements TrainerService{
    private String pokemonTrainerServiceUrl;
    TrainerRepository trainerRepository;

    @Autowired
    public TrainerServiceImpl(TrainerRepository trainerRepo) {
        trainerRepository=trainerRepo;
    }

    public Iterable<Trainer> getAllTrainers() {

        return trainerRepository.findAll();

    }

    public Trainer getTrainer(String name) {
        try {
            return trainerRepository.findById(name).get();
        }
        catch (Exception e){
            return null;
        }

    }

    public Trainer createTrainer(Trainer trainer) {
        return trainerRepository.save(trainer);
    }

    public void deleteTrainer(String name){
        trainerRepository.deleteById(name);
    }

    @Override
    public void updateTrainer(Trainer trainer) {
        trainerRepository.save(trainer);
    }
}
