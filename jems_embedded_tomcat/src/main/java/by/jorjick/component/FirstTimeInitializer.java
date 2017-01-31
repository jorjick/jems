package by.jorjick.component;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import by.jorjick.domain.Role;
import by.jorjick.domain.User;
import by.jorjick.repository.UserRepository;

/**
 * Created by gora on 1/6/17.
 */
@Component
public class FirstTimeInitializer {
    public static Logger log = LoggerFactory.getLogger(FirstTimeInitializer.class);
    @Autowired
    SCryptPasswordEncoder scryptPasswordEncoder;
    @Autowired
    UserRepository userRepository;



    @EventListener(ContextRefreshedEvent.class)
    public void contextRefreshedEvent() {
        /*User user = new User();
        user.setUsername("admin");
        user.setEmail("admin@jems.by");
        user.setPasswordHash(scryptPasswordEncoder.encode("admin"));
        Set<Role> roles = new HashSet<>();
        Role adminRole = new Role();
        adminRole.setRole("admin");
        roles.add(adminRole);
        user.setRoles(roles);
        userRepository.saveAndFlush(user);
        log.info("Initializer has executed!");*/
    }
}
