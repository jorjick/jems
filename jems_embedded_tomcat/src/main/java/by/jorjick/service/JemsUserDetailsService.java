package by.jorjick.service;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import by.jorjick.domain.Role;
import by.jorjick.domain.User;
import by.jorjick.repository.UserRepository;

/**
 * Created by gora on 1/1/17.
 */
@Service
public class JemsUserDetailsService implements UserDetailsService {
    private static Logger log = LoggerFactory.getLogger(UserDetailsService.class);
    private UserRepository userRepository;


    public JemsUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            log.debug("User not fount for provided username: " + username);
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPasswordHash(),
                user.isEnabled(), true, true, true,
                getAuthorities(user));
    }

    private Set<GrantedAuthority> getAuthorities(User user) {
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        for (Role role : user.getRoles()) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getRole());
            authorities.add(grantedAuthority);
        }
        log.debug("user authorities are " + authorities.toString());
        return authorities;
    }
}
