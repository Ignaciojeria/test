package montyhall.example.montyhall.repository;

import montyhall.example.montyhall.domain.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    List<Game> findAllByDoorIsNotNullAndOpportunitiesEquals(int opportunities);

    Page<Game> findAllByDoorIsNotNullAndOpportunitiesEquals(int opportunities, Pageable pageable);

    int countByDoorIsNotNullAndOpportunitiesEquals(int opportunities);

}
