package montyhall.example.montyhall.service;

import montyhall.example.montyhall.domain.Choise;
import montyhall.example.montyhall.domain.Door;
import montyhall.example.montyhall.domain.Game;
import montyhall.example.montyhall.domain.Gift;
import montyhall.example.montyhall.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private DoorService doorService;

    public List<String> getScore() {

        List<Game> gameList = gameRepository.findAllByDoorIsNotNullAndOpportunitiesEquals(0);

        List<String> opciones = new ArrayList<>();

        long mantienesOpcionYPierdes = gameList.stream().filter(item -> item.getChoise() == Choise.KEEP_CHOISE && item.getDoor().getGift() == Gift.GOAT).count();

        long mantienesOpcionYGanas = gameList.stream().filter(item -> item.getChoise() == Choise.KEEP_CHOISE && item.getDoor().getGift() == Gift.CAR).count();

        long cambiasOpcionYPierdes = gameList.stream().filter(item -> item.getChoise() == Choise.CHANGE_CHOISE && item.getDoor().getGift() == Gift.GOAT).count();

        long cambiasOpcionYGanas = gameList.stream().filter(item -> item.getChoise() == Choise.CHANGE_CHOISE && item.getDoor().getGift() == Gift.CAR).count();

        opciones.add("veces que mantienes opcion inicial y te ganas una cabra :".concat(mantienesOpcionYPierdes + ""));
        opciones.add("veces que mantienes opcion inicial y te ganas un auto :".concat(mantienesOpcionYGanas + ""));
        opciones.add("veces que cambias  la opcion inicial y te ganas una cabra :".concat(cambiasOpcionYPierdes + ""));
        opciones.add("veces que cambias  la opcion inicial y te ganas un auto :".concat(cambiasOpcionYGanas + ""));

        return opciones;
    }

    public List<Map<String, Object>> findAllAndSetGameDoors(int pageRequest) {


        Pageable pageable = PageRequest.of(pageRequest, 10);

        Page<Game> page = gameRepository.findAllByDoorIsNotNullAndOpportunitiesEquals(0, pageable);

        List<Map<String, Object>> outputList = new ArrayList<>();

        page.forEach(item -> {
            Map<String, Object> output = new HashMap<>();
            output.put("GAME", item);
            output.put("GAME_DOORS", doorService.findAllByGame(item));
            outputList.add(output);
        });

        return outputList;
    }


    public Game createNewGame(Game game) {
        game.setOpportunities(2);
        return gameRepository.save(game);
    }


    public Game chooseDoor(Door door) {
        Game game = gameRepository.findById(door.getGame().getId()).get();
        opportunitiesGuardClause(game);

        if (game.getOpportunities() == 2) {
            game.setChoise(Choise.FIRST_CHOISE);
        } else if (game.getOpportunities() == 1 && door.getId().longValue() == game.getDoor().getId().longValue()) {
            game.setChoise(Choise.KEEP_CHOISE);
        } else if (game.getOpportunities() == 1 && door.getGame().getId().longValue() == game.getId().longValue() && door.getGame().getId().longValue() != game.getDoor().getId().longValue()) {
            game.setChoise(Choise.CHANGE_CHOISE);
        } else {
            throw new RuntimeException("invalid option!");
        }

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

    public int countByDoorIsNotNullAndOpportunitiesEquals() {
        int count = this.gameRepository.countByDoorIsNotNullAndOpportunitiesEquals(0);
        return (int) Math.round(((count / 10) + 0.5));
    }

}
