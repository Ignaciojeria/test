package montyhall.example.montyhall.repository;

import montyhall.example.montyhall.domain.Door;
import montyhall.example.montyhall.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoorRepository extends JpaRepository<Door,Long> {
    List<Door> findAllByGame(Game game);
}
