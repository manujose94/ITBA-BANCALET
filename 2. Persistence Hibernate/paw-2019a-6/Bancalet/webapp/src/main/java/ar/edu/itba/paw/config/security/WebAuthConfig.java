package ar.edu.itba.paw.config.security;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
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

	@Value("classpath:rememberme.key")
	private Resource rememberMeKey;

	@Autowired
	private PawUserDetailsService userDetailsService;

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http.userDetailsService(userDetailsService).sessionManagement().invalidSessionUrl("/index").and()
				.authorizeRequests().antMatchers("/").permitAll().antMatchers("/index").anonymous()
				.antMatchers("/items/*").permitAll().antMatchers("/contact").anonymous().antMatchers("/contactSucces")
				.anonymous().antMatchers("/users/login").anonymous().antMatchers("/items/**").permitAll()
				.antMatchers("/users/create").anonymous().antMatchers("/users/register").anonymous()
				.antMatchers("/admin/**").hasRole(User.ADMIN).antMatchers("/**").authenticated().and().formLogin()
				.usernameParameter("username").passwordParameter("password").defaultSuccessUrl("/", true)
				.loginPage("/users/login").and().rememberMe().rememberMeParameter("rememberme")
				.userDetailsService(userDetailsService).key(getRememberMeKey())
				.tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30)).and().logout().logoutUrl("/logout")
				.logoutSuccessUrl("/").and().exceptionHandling().accessDeniedPage("/error/403").and().csrf().disable();
	}

	@Override
	public void configure(final WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/webjars/**", "/resources/**", "/resources/css/**", "/resources/js/**",
				"/resources/img/**", "/resources/plugins/**", "/appicon.ico", "/error/403");
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

	private String getRememberMeKey() {
		final StringWriter stringWriter = new StringWriter();
		try (Reader reader = new InputStreamReader(rememberMeKey.getInputStream())) {
			final char[] buf = new char[2048];
			int length;
			while ((length = reader.read(buf)) != -1) {
				stringWriter.write(buf, 0, length);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return stringWriter.toString();
	}
}