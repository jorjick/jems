package by.jorjick.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import by.jorjick.domain.User;

/**
 * Created by gora on 1/2/17.
 */
public interface UserRepository extends JpaRepository<User, String> {
    public User findByUsername (String userName);
    
    
    @Query("SELECT COUNT(SELECT u FROM User u WHERE u.getUserame() = :userName) > 0")
    public boolean existsByUsername(@Param("userName") String userName);
}
