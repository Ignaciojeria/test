package montyhall.example.montyhall.repository;

import montyhall.example.montyhall.domain.Door;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoorRepository extends JpaRepository<Door,Long> {
}
