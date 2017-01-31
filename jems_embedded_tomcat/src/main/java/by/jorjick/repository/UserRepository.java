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

    @Query(value = "SELECT COUNT(u)>0 FROM users u WHERE u.username = :userName", nativeQuery = true)
    public boolean existsByUsername(@Param("userName") String userName);
}
