package montyhall.example.montyhall.service;

import montyhall.example.montyhall.domain.Choise;
import montyhall.example.montyhall.domain.Door;
import montyhall.example.montyhall.domain.Game;
import montyhall.example.montyhall.domain.Gift;
import montyhall.example.montyhall.repository.DoorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class DoorService {

    @Autowired
    private DoorRepository doorRepository;

    public List<Door> createGameDoors(Game game) {

        List<Door> doorList = new ArrayList<>();

        Door goatDoorA = new Door();
        goatDoorA.setGame(game);
        goatDoorA.setGift(Gift.GOAT);

        Door goatDoorB = new Door();
        goatDoorB.setGame(game);
        goatDoorB.setGift(Gift.GOAT);

        Door carDoor = new Door();
        carDoor.setGame(game);
        carDoor.setGift(Gift.CAR);

        doorList.add(goatDoorA);
        doorList.add(goatDoorB);
        doorList.add(carDoor);

        Collections.shuffle(doorList);

        return doorRepository.saveAll(doorList);
    }

    public Door findById(Long id) {
        return doorRepository.findById(id).get();
    }


}
