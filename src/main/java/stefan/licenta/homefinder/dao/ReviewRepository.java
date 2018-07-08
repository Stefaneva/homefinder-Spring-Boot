package stefan.licenta.homefinder.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stefan.licenta.homefinder.entity.Ad;
import stefan.licenta.homefinder.entity.Review;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByAd(Ad ad);
}
