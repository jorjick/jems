package by.jorjick.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.jorjick.domain.User;
import by.jorjick.repository.RoleRepository;
import by.jorjick.repository.UserRepository;

/**
 * Created by gora on 1/11/17.
 */
@Service
public class UserService implements IUserService {
	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	private RoleRepository roleRepository;

	@Autowired
	public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.roleRepository = roleRepository;
	}

	@Transactional
	@Override
	public Page<User> findAllPageable(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	public boolean isExist(User user) {
		return userRepository.existsByUsername(user.getUsername());
	}

	@Transactional
	@Override
	public Iterable<User> save(Iterable<User> users) {
		return userRepository.save(users);
	}

	@Transactional
	public User saveAndFlush(User user) {
		user.setPasswordHash(passwordEncoder.encode(user.getPassword()));
		user.setEnabled(true);
		user.setRoles(roleRepository.findSetByRole("user"));
		return userRepository.saveAndFlush(user);
	}
}
