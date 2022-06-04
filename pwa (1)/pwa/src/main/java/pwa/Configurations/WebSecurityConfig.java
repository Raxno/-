package pwa.Configurations;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDetailsService profileDetailsService;

    @Bean
    public BCryptPasswordEncoder bCrypt() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }

    @Bean
    public PersistentTokenRepository tokenRepository() {
        JdbcTokenRepositoryImpl tr = new JdbcTokenRepositoryImpl();
        tr.setDataSource(dataSource);
        return tr;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login/**", "/profile/registration/**", "/recovery", "/change_password",
                        "/friend_list", "/registration_notification", "/confirm", "/activate/**", "/user/{nickname}",
                        "/change_password/*", "/search", "/recovery/*", "/css/**", "/images/**", "/js/**").permitAll()
                .anyRequest().authenticated()
                .and().exceptionHandling().accessDeniedPage("/access-denied")
                .and().formLogin().loginPage("/login").permitAll()
                .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login").invalidateHttpSession(true)
                .and().rememberMe().rememberMeParameter("remember-me").tokenRepository(tokenRepository()).and().cors().disable();
    }

    @Autowired
    public void configureGlobale(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(profileDetailsService).passwordEncoder(bCrypt());
    }

}
