package recipes.Persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import recipes.businesslayer.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmail(String email);
}
