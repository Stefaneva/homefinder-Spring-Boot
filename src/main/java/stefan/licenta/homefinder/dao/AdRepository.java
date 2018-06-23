package stefan.licenta.homefinder.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stefan.licenta.homefinder.entity.Ad;
import stefan.licenta.homefinder.entity.User;

import java.util.List;

@Repository
public interface AdRepository extends JpaRepository<Ad, Long> {
    List<Ad> findAllByOrderByDateDesc();
    List<Ad> findAllByUserIdOrderByDateDesc(User userId);
}
