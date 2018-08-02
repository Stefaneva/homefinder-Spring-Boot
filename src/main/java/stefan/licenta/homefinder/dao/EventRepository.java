package stefan.licenta.homefinder.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import stefan.licenta.homefinder.entity.Ad;
import stefan.licenta.homefinder.entity.Event;
import stefan.licenta.homefinder.entity.User;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    void deleteByUserAndAd(User user, Ad ad);
    Event findByUserAndAd(User user, Ad ad);
    @Query("select e.user from Event e where e.ad = ?1")
    List<User> findAllUsersAtAd(Ad ad);
    @Query("select e.ad.userId from Event e where e.ad = ?1")
    User findOwner(Ad ad);
    List<Event> findAllByAdUserId(User ad_userId);
}
