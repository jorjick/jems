package by.jorjick.controller;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

	@RequestMapping("/login")
	public String login() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		if (authentication.isAuthenticated()) {
			for (GrantedAuthority authority : authorities) {
				switch (authority.getAuthority()) {
				case "admin":
					return "redirect:/admin";
				case "user":
					return "redirect:/user";
				default:
					return "/login";
				}
			}
		}
		return "login";

	}
}
