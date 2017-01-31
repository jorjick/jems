package by.jorjick.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import by.jorjick.custom.JemsAuthenticationFailureHandler;
import by.jorjick.custom.JemsAuthenticationsuccessHandler;
import by.jorjick.repository.UserRepository;
import by.jorjick.service.JemsUserDetailsService;

/**
 * Created by gora on 1/2/17.
 */
@Configuration
@EnableWebSecurity
@EnableRedisHttpSession
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    DataSource dataSource;

    @Autowired
    private UserRepository userRepository;

    @Bean
    AuthenticationSuccessHandler successHandler() {
        return new JemsAuthenticationsuccessHandler();
    }

    @Bean
    AuthenticationFailureHandler failureHandler() {
        return new JemsAuthenticationFailureHandler();
    }

    @Bean
    public SCryptPasswordEncoder scryptPasswordEncoder() {
    	SCryptPasswordEncoder passwordEncoder = new SCryptPasswordEncoder(2048, 8, 1, 32, 64);
    	return passwordEncoder;
    };

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceBean());
        auth.userDetailsService(userDetailsServiceBean()).passwordEncoder(scryptPasswordEncoder());
    }

    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return new JemsUserDetailsService(userRepository);
    }

   /* @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/static*//**");
    }*/


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .formLogin()
                .loginPage("/login")
                .successHandler(successHandler())
                .failureHandler(failureHandler())
                .usernameParameter("username")
                .passwordParameter("password")
                .and()
                .rememberMe()
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(24 * 60 * 60)
                .rememberMeParameter("remember-me")
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/login").permitAll()
                .antMatchers("/admin/**").hasAuthority("admin")
                .antMatchers("/user/**").hasAnyAuthority("user", "admin")
                .and()
                .logout()
                .logoutSuccessUrl("/login").clearAuthentication(true).invalidateHttpSession(true)
                .deleteCookies("SESSION")
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                .invalidSessionUrl("/login");
    }


    @Bean
    public PersistentTokenRepository persistentTokenRepository() {

        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }

    @Bean
    public RedisConnectionFactory connectionFactory() {
        return new JedisConnectionFactory();
    }
}
