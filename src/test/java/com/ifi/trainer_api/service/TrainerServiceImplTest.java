package com.ifi.trainer_api.service;

import com.ifi.trainer_api.bo.Trainer;
import com.ifi.trainer_api.repository.TrainerRepository;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TrainerServiceImplTest {

        @Test
        void getAllTrainers_shouldCallTheRepository() {
            TrainerRepository trainerRepo = mock(TrainerRepository.class);
            TrainerServiceImpl trainerService = new TrainerServiceImpl(trainerRepo);

            trainerService.getAllTrainers();

            verify(trainerRepo).findAll();
        }

        @Test
        void getTrainer_shouldCallTheRepository() {
            TrainerRepository trainerRepo = mock(TrainerRepository.class);
            TrainerServiceImpl trainerService = new TrainerServiceImpl(trainerRepo);

            trainerService.getTrainer("Ash");

            verify(trainerRepo).findById("Ash");
        }

        @Test
        void createTrainer_shouldCallTheRepository() {
            TrainerRepository trainerRepo = mock(TrainerRepository.class);
            TrainerServiceImpl trainerService = new TrainerServiceImpl(trainerRepo);

            Trainer ash = new Trainer();
            trainerService.createTrainer(ash);

            verify(trainerRepo).save(ash);
        }



}