package montyhall.example.montyhall.resource;

import montyhall.example.montyhall.domain.Game;
import montyhall.example.montyhall.repository.GameRepository;
import montyhall.example.montyhall.service.DoorService;
import montyhall.example.montyhall.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
public class GameResource {

    @Autowired
    private GameService gameService;

    @GetMapping("game/getScore")
    public List<String> getScore() {
        return gameService.getScore();
    }


    @GetMapping("game/findAll/{pageRequest}")
    public List<Map<String, Object>> findAll(@PathVariable int pageRequest) {
        return gameService.findAllAndSetGameDoors(pageRequest);
    }

    @GetMapping("game/countPages")
    public int countGamePages() {
        return gameService.countByDoorIsNotNullAndOpportunitiesEquals();
    }


}
