package ar.edu.itba.paw.config.security;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;

import javax.ws.rs.HttpMethod;

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
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import ar.edu.itba.paw.models.User;

@Configuration
@EnableWebSecurity
@ComponentScan("ar.edu.itba.paw.config")
public class WebAuthConfig extends WebSecurityConfigurerAdapter {

	@Value("classpath:rememberme.key")
	private Resource rememberMeKey;

	@Autowired
	private PawUserDetailsService userDetailsService;

	@Autowired
	private AuthenticationEntryPoint authenticationEntryPoint;

	@Autowired
	private AccessDeniedHandler accessDeniedHandler;

	@Autowired
	private StatelessLoginSuccessHandler statelessLoginSuccessHandler;

	@Autowired
	private AuthenticationFailureHandler statelessLoginFailureHandler;

	@Autowired
	private StatelessAuthenticationFilter statelessAuthenticationFilter;

	@Autowired
	private CorsFilter corsFilter;

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http.userDetailsService(userDetailsService).sessionManagement().and()
				.addFilterBefore(corsFilter, ChannelProcessingFilter.class).csrf().disable().exceptionHandling()
				.authenticationEntryPoint(authenticationEntryPoint).accessDeniedHandler(accessDeniedHandler).and()
				.authorizeRequests().antMatchers(HttpMethod.GET, "/rest/error/403").permitAll()
				.antMatchers(HttpMethod.GET, "/rest/login").permitAll()
				.antMatchers(HttpMethod.GET, "/rest/users/login/**").authenticated()
				.antMatchers(HttpMethod.POST, "/rest/contact").permitAll()
				.antMatchers(HttpMethod.POST, "/rest/register").permitAll()
				.antMatchers(HttpMethod.PUT, "/rest/confirmregister/**").permitAll()

				.antMatchers("/rest/items/contactosItem/**").hasAnyRole(User.ADMIN, User.USER)
				.antMatchers("/rest/items/contactosMyItem/**").hasAnyRole(User.ADMIN, User.USER)
				.antMatchers("/rest/items/baja/**").hasAnyRole(User.ADMIN, User.USER).antMatchers("/rest/items/additem")
				.hasAnyRole(User.ADMIN, User.USER).antMatchers("/rest/items/update/**")
				.hasAnyRole(User.ADMIN, User.USER).antMatchers("/rest/items/delete/**")
				.hasAnyRole(User.ADMIN, User.USER).antMatchers("/rest/items/delitemimages/**")
				.hasAnyRole(User.ADMIN, User.USER).antMatchers("/rest/items/myitems").hasAnyRole(User.ADMIN, User.USER)
				.antMatchers("/rest/items/sendto/**").hasAnyRole(User.ADMIN, User.USER)
				.antMatchers("/rest/items/rate/**").hasAnyRole(User.ADMIN, User.USER).antMatchers("/rest/items")
				.permitAll().antMatchers("/rest/items/**").permitAll()

				.antMatchers("/rest/admin").hasRole(User.ADMIN).antMatchers("/rest/admin/**").hasRole(User.ADMIN)

				.antMatchers("/rest/users").hasAnyRole(User.ADMIN, User.USER).antMatchers("/rest/users/**")
				.hasAnyRole(User.ADMIN, User.USER).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().formLogin().usernameParameter("username")
				.passwordParameter("password").loginProcessingUrl("/rest/login")
				.successHandler(statelessLoginSuccessHandler).failureHandler(statelessLoginFailureHandler).and()
				.logout().logoutUrl("/logout").and()
				.addFilterBefore(statelessAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	public void configure(final WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/resources/**", "/resources/**", "/resources/img/**",
				"/resources/**", "/favicon.ico");
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

	@Bean
	public String tokenSigningKey() {
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