package by.jorjick.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import by.jorjick.domain.Role;

/**
 * Created by gora on 1/2/17.
 */
public interface RoleRepository extends JpaRepository<Role, String> {
	public Role findByRole (String role);
	
	public Set<Role> findSetByRole (String role);
}
