package montyhall.example.montyhall.service;

import montyhall.example.montyhall.domain.Choise;
import montyhall.example.montyhall.domain.Door;
import montyhall.example.montyhall.domain.Game;
import montyhall.example.montyhall.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    public List<Game> findAll() {
        return gameRepository.findAll();
    }

    public Game createNewGame(Game game) {
        game.setOpportunities(2);
        return gameRepository.save(game);
    }


    public Game chooseDoor(Door door) {
        Game game = gameRepository.findById(door.getGame().getId()).get();
        opportunitiesGuardClause(game);

        if (game.getOpportunities() == 2)
            game.setChoise(Choise.FIRST_CHOISE);
        else if (game.getOpportunities() == 1 && door.getId().longValue() == game.getDoor().getId().longValue())
            game.setChoise(Choise.KEEP_CHOISE);
        else if (game.getOpportunities() == 1 && door.getGame().getId().longValue() == game.getId().longValue() && door.getGame().getId().longValue() != game.getDoor().getId().longValue())
            game.setChoise(Choise.CHANGE_CHOISE);
        else
            throw new RuntimeException("invalid option!");

        int opportunities = game.getOpportunities() - 1;
        game.setOpportunities(opportunities);

        game.setDoor(door);

        return gameRepository.save(game);
    }

    private static void opportunitiesGuardClause(Game game) {
        if (game.getOpportunities() == 0) {
            throw new RuntimeException("you do not have more opportunities");
        }
    }

    public static void chooseDoorAfterOpportunityWasGivenGuardClause(Game game) {
        if (game.getChoise() == null)
            throw new RuntimeException("¿what is you first choise?");
        if (game.getChoise().name().equals(Choise.FIRST_CHOISE.name()))
            throw new RuntimeException("select a first choise");
    }

}
