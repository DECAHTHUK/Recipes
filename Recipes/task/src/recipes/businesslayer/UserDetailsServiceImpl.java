package recipes.businesslayer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import recipes.Persistence.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepo;

    @Autowired
    public UserDetailsServiceImpl(UserRepository recipeRepository) {
        this.userRepo = recipeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findUserByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("Not found: " + email);
        }

        return new UserDetailsImpl(user);
    }

    public User findUserByEmail(String email) {
        return userRepo.findUserByEmail(email);
    }

    public void updateUserByEmail(String email, long id) {
        User user = userRepo.findUserByEmail(email);
        user.addId(id);
        userRepo.save(user);
    }
}
