package by.jorjick.controller;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	@RequestMapping("/")
	public String index() {
		Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();
		for (GrantedAuthority authority : authorities) {
			switch (authority.getAuthority()) {
			case "admin":
				return "admin";
			case "user":
				return "user";
			default:
				return "login";
			}
		}
		return "login";
	}
}
