package by.jorjick.custom;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import by.jorjick.helpers.AuthResult;

/**
 * Created by gora on 1/3/17.
 */
public class JemsAuthenticationsuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        AuthResult authResult = new AuthResult();
        authResult.setSuccess(true);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            switch (authority.getAuthority()) {
                case "admin":
                    authResult.setRedirectUrl("admin");
                    break;
                case "user":
                    authResult.setRedirectUrl("user");
                    break;
                default:
                    authResult.setRedirectUrl("guest");
            }
        }
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().print(objectMapper.writeValueAsString(authResult));
        response.getWriter().flush();
    }
}
