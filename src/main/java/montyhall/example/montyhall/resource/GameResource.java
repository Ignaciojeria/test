package montyhall.example.montyhall.resource;

import montyhall.example.montyhall.domain.Game;
import montyhall.example.montyhall.repository.GameRepository;
import montyhall.example.montyhall.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
public class GameResource {

    @Autowired
    private GameService gameService;

    @GetMapping("game/findAll")
    public List<Game> findAll() {
        return gameService.findAll();
    }


}
