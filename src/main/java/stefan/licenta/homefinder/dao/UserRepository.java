package stefan.licenta.homefinder.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import stefan.licenta.homefinder.entity.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmail(String email);
    User findByEmail(String email);

    @Query("select u.email from User u")
    List<String> getAllUsersEmail();

    @Query("select u from User u where u.type not like 'ADMIN'")
    List<User> getAllUsers();
}
