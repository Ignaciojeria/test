package montyhall.example.montyhall.resource;

import montyhall.example.montyhall.domain.Door;
import montyhall.example.montyhall.domain.Game;
import montyhall.example.montyhall.resource.request.ChooseDoorRequest;
import montyhall.example.montyhall.service.DoorService;
import montyhall.example.montyhall.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ClientResource {

    @Autowired
    private DoorService doorService;

    @Autowired
    private GameService gameService;

    @PostMapping("/play")
    public List<Door> createNewGame() {
        return doorService.createGameDoors(gameService.createNewGame(new Game()));
    }

    @PostMapping("/choose")
    public Game chooseDoor(@RequestBody ChooseDoorRequest chooseDoorRequest){
        return gameService.chooseDoor(doorService.findById(chooseDoorRequest.id));
    }




}
