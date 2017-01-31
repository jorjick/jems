package by.jorjick.service;

import by.jorjick.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by gora on 1/11/17.
 */
public interface IUserService
{
    Page<User> findAllPageable(Pageable pageable);

    Iterable<User> save(Iterable<User> users);
    
    User saveAndFlush(User user);
    
    boolean isExist(User user);
}
