package montyhall.example.montyhall.resource;

import montyhall.example.montyhall.domain.Door;
import montyhall.example.montyhall.domain.Game;
import montyhall.example.montyhall.resource.request.ChooseDoorRequest;
import montyhall.example.montyhall.service.DoorService;
import montyhall.example.montyhall.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
public class ClientResource {

    @Autowired
    private DoorService doorService;

    @Autowired
    private GameService gameService;

    @PostMapping("/play")
    public Map<String, Object> createNewGame() {
        return doorService.createGameDoors(gameService.createNewGame(new Game()));
    }

    @PostMapping("/firstChoise")
    public Map<String, Object> chooseDoorAndGiveOpportunity(@RequestBody ChooseDoorRequest chooseDoorRequest) {
        Game game = gameService.chooseDoor(doorService.findById(chooseDoorRequest.id));
        Map<String, Object> output = doorService.findAllByGameAndShowGoatOfOneOfTheDoors(game);
        return output;
    }

    @PostMapping("/finalChoise")
    public Object chooseDoorAfterOpportunityWasGiven(@RequestBody ChooseDoorRequest chooseDoorRequest) {
        Game game = gameService.chooseDoor(doorService.findById(chooseDoorRequest.id));
        GameService.chooseDoorAfterOpportunityWasGivenGuardClause(game);
        return doorService.findAllByGameAndShowResulset(game);
    }


}
