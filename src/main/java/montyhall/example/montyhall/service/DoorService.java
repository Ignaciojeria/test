package montyhall.example.montyhall.service;

import montyhall.example.montyhall.domain.Choise;
import montyhall.example.montyhall.domain.Door;
import montyhall.example.montyhall.domain.Game;
import montyhall.example.montyhall.domain.Gift;
import montyhall.example.montyhall.repository.DoorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DoorService {

    @Autowired
    private DoorRepository doorRepository;

    public Map<String, Object> createGameDoors(Game game) {

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

        doorList = doorRepository.saveAll(doorList);

        Map<String, Object> output = new HashMap<>();

        output.put("gameId", game.getId());

        doorList.forEach(item -> {
            item.setGift(null);
        });

        output.put("doors", doorList);

        return output;
    }

    public Door findById(Long id) {
        return doorRepository.findById(id).get();
    }

    public List<Object> findAllByGameAndShowGoatOfOneOfTheDoors(Game game) {

        if (!game.getChoise().name().equals(Choise.FIRST_CHOISE.name()))
            throw new RuntimeException("¿what is you first choise?");

        List<Door> doorList = doorRepository.findAllByGame(game);


        doorList = doorList.stream().filter(item -> item.getId() != item.getGame().getDoor().getId()).collect(Collectors.toList());

        Map<String, Object> output = new HashMap<>();

        Map<String, Long> selected = new HashMap<>();
        selected.put("id", game.getDoor().getId());
        output.put("selected", selected);

        for (int i = 0; i < doorList.size(); i++) {
            if (doorList.get(i).getGift().name().equals(Gift.GOAT.name())) {
                Map<String, Object> oppenedDoor = new HashMap<>();
                oppenedDoor.put("id", doorList.get(i).getId());
                oppenedDoor.put("gift", doorList.get(i).getGift());
                output.put("opened", oppenedDoor);
                doorList.remove(i);
                break;
            }
        }

        doorList.forEach(item -> {
            item.setGift(null);
        });

        output.put("other_choise", doorList.get(0));


        return output.values().stream().collect(Collectors.toList());
    }


    public List<Object> findAllByGameAndShowResulset(Game game) {
        List<Map<String, Object>> doorsMap = new ArrayList<>();

        doorRepository.findAllByGame(game).forEach(
                item -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", item.getId());
                    map.put("gift", item.getGift());
                    doorsMap.add(map);
                }
        );

        Map<String, Object> output = new HashMap<>();

        output.put("game", game);
        output.put("doors", doorsMap);

        return output.values().stream().collect(Collectors.toList());
    }

    List<Door> findAllByGame(Game game) {
        return doorRepository.findAllByGame(game);
    }


}
