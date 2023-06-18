package tn.proosoftcloud.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import tn.proosoftcloud.security.jwt.AuthEntryPointJwt;
import tn.proosoftcloud.security.jwt.AuthTokenFilter;
import tn.proosoftcloud.service.UserDetailsServiceImpl;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity( prePostEnabled = true)
/**
 *
 * @author Tarchoun Abir
 *
 */
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	UserDetailsServiceImpl userDetailsService;
	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;
	@Autowired
	DataSource dataSource;
	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}
	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	private static final String[] AUTH_WHITELIST = {
			"/v2/api-docs",
			"/configuration/ui",
			"/swagger-resources/**",
			"/swagger*/**",
			"/configuration/security",
			"/swagger-ui.html",
			"/webjars/**"
	};

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic().disable().csrf().disable()
				.headers()
				.frameOptions()
				.deny()
				.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.authorizeRequests()
				.antMatchers(HttpMethod.OPTIONS).permitAll()
				.antMatchers("/api/auth/**").permitAll()
				.antMatchers("/api/**").permitAll()
				.antMatchers("/api/test/**").permitAll()
				 .antMatchers("/**/**").permitAll()
				// dont authenticate this particular request
				.antMatchers("/api/private/**").authenticated()
				.antMatchers(AUTH_WHITELIST).permitAll()
				.anyRequest().authenticated().and()
				.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
				//remember me configuration
				.rememberMe().tokenRepository(persistentTokenRepository()).tokenValiditySeconds(86400)
				.key("rem-me-key")
				.rememberMeParameter("remember-me-param")
				.rememberMeCookieName("my-remember-me");
		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	}
	@Bean
	public PersistentTokenRepository persistentTokenRepository(){
		JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
		db.setDataSource(dataSource);
		return db;
	}
}