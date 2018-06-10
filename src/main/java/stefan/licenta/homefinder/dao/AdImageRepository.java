package stefan.licenta.homefinder.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import stefan.licenta.homefinder.entity.Ad;
import stefan.licenta.homefinder.entity.AdImage;

import java.util.List;

@Repository
public interface AdImageRepository extends JpaRepository<AdImage, Long> {

    List<AdImage> findAllByAdId(Ad adId);

    AdImage findFirstByAdId(Ad adId);
}
