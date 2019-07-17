package ar.edu.itba.paw.config.security;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import ar.edu.itba.paw.models.User;

@Configuration
@EnableWebSecurity
@ComponentScan("ar.edu.itba.paw.config")
public class WebAuthConfig extends WebSecurityConfigurerAdapter {

	private static final String KEY = "QH8wJ+cEvrlVJFebOQcGNEDan0N4KzjkNxM6ODOXxGc=";

	@Autowired
	private PawUserDetailsService userDetailsService;

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http.userDetailsService(userDetailsService).sessionManagement().invalidSessionUrl("/index").and()
				.authorizeRequests().antMatchers("/").permitAll().antMatchers("/index").anonymous().antMatchers("/list")
				.anonymous().antMatchers("/contact").anonymous().antMatchers("/contactSucces").anonymous()
				.antMatchers("/users/login").anonymous().antMatchers("/list/**").anonymous()
				.antMatchers("/users/create").anonymous().antMatchers("/users/register").anonymous()
				.antMatchers("/admin/**").hasRole(User.ADMIN).antMatchers("/**").authenticated().and().formLogin()
				.usernameParameter("username").passwordParameter("password").defaultSuccessUrl("/", false)
				.loginPage("/users/login").and().rememberMe().rememberMeParameter("rememberme")
				.userDetailsService(userDetailsService).key(KEY).tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30))
				.and().logout().logoutUrl("/logout").logoutSuccessUrl("/").and().exceptionHandling()
				.accessDeniedPage("/error/403").and().csrf().disable();
	}

	@Override
	public void configure(final WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/webjars/**","/resources/**", "/resources/css/**", "/resources/js/**", "/resources/img/**",
				"/resources/plugins/**", "/appicon.ico", "/error/403");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider());
	}

	@Bean
	public DaoAuthenticationProvider authProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}
}