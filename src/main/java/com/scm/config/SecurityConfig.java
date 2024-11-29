package com.scm.config;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

//import org.springframework.context.annotation.Bean;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.scm.services.implementation.SecurityCustomUserDetailService;



@Configuration
public class SecurityConfig 
{
	@Autowired
	private SecurityCustomUserDetailService customUserDetailService;

	@Autowired
	private OAuthAuthenticationSuccessHandler handler;

	// configuration of authentication provider for spring security
    @Bean
    public DaoAuthenticationProvider authenticationProvider()
	{
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		
		//user detail service ka object
		daoAuthenticationProvider.setUserDetailsService(customUserDetailService);
		
		// password encodar ka object
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		
		return daoAuthenticationProvider;
	}
	
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception
    {
    	// configuration for authorizing the page means apan fakt te pages directly access karu sakto je ethun permited jhale ahet............
    	// urls configure kiya hai ki kon se public rahenge aur private
    	
    	httpSecurity.authorizeHttpRequests(authorize -> {
    		
    		// here we are only giving the access to this pages......so it's not recommended 
    	//	authorize.requestMatchers("/home","/signup","/login").permitAll();
    	//       or
    		// this is for protecting the url .....so here we have to first login or signup.....so no one can access directly
    		authorize.requestMatchers("/user/**").authenticated();
    		// here giving the access or permission to any request other than the user like login page can view anyone so we give the permission
    		authorize.anyRequest().permitAll();
    	});
    	
    	// form default login
    	// agar hame kuch bhi change karna hua to hum yaha ayenge : form login related
    	
    	//httpSecurity.formLogin(Customizer.withDefaults()); 		// this command set default values for or form
    	
    	httpSecurity.formLogin( formLogin -> {
    		// custumizing the login form ............by using this then the spring default login page will not display instead of that our own login page will be displayed...
    		
    		formLogin.loginPage("/login");
    		// the login form will submit to authenticate
    		formLogin.loginProcessingUrl("/authenticate");
    		
    		// if user successfully login then user forward to dashboard
    		formLogin.successForwardUrl("/user/dashboard");
    		
    		formLogin.failureForwardUrl("/login?error=true");
    		
    		formLogin.usernameParameter("email");
    		formLogin.passwordParameter("password");
    		
    		
    		// this comment is only for some extra point ...we don't need now we will use it in future 
    		
    	/*	formLogin.failureHandler(new AuthenticationFailureHandler() {
				
				@Override
				public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
						AuthenticationException exception) throws IOException, ServletException {
					
				}
			});
    		
    		formLogin.successHandler(new AuthenticationSuccessHandler() {
			
				@Override
				public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
						Authentication authentication) throws IOException, ServletException {
					
				}
			});    */
    		
    	});
    	
    	// disable the default logout option provided by the spring security
    	httpSecurity.csrf(AbstractHttpConfigurer :: disable);
    	
    	// custom logout url
    	httpSecurity.logout(logoutForm -> {
    		logoutForm.logoutUrl("/do-logout");
    		logoutForm.logoutSuccessUrl("/login?logout=true");
    	});
    	
    	
    	
    	// oauth2 configurations
    	
    	httpSecurity.oauth2Login(oauth ->{
    		oauth.loginPage("/login");
    		
    		oauth.successHandler(handler);
    	});
    	
    	
    	return httpSecurity.build();
    }
    
    
	@Bean
	public PasswordEncoder passwordEncoder () 
	{
		return new BCryptPasswordEncoder();
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// =============================================================================================
	
	
	// user create and login using java code with in memory service
	
//	@Bean
//	public UserDetailsService userDetailsService()
//	{
//		UserDetails user1 = User.withDefaultPasswordEncoder().username("admin123").password("admin123").roles("ADMIN", "USER").build();
//		
//		UserDetails user2 = User.withUsername("user123").password("password").build();
//
//		var inMemoryUserDetailsManager = new InMemoryUserDetailsManager(user1,user2);
//		
//		return inMemoryUserDetailsManager;
//	}
	

}
