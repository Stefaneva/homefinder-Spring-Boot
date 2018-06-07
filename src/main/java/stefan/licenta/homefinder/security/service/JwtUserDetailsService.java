package stefan.licenta.homefinder.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import stefan.licenta.homefinder.dao.UserRepository;
import stefan.licenta.homefinder.entity.User;
import stefan.licenta.homefinder.security.JwtUserFactory;

@Service
public class JwtUserDetailsService  implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(mail);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with email '%s'.", mail));
        } else {
            return JwtUserFactory.create(user);
        }
    }
}

