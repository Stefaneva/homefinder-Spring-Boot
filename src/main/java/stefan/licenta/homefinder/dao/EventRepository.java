package stefan.licenta.homefinder.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stefan.licenta.homefinder.entity.Ad;
import stefan.licenta.homefinder.entity.Event;
import stefan.licenta.homefinder.entity.User;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    void deleteByUserAndAd(User user, Ad ad);
}
