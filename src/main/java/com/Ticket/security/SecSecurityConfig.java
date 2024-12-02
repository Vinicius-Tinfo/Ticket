package com.Ticket.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.SecurityExpressionOperations;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebSecurityExpressionRoot;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;



@Configuration
@EnableWebSecurity
public class SecSecurityConfig {
	
	@Autowired	
	private UserDetailServiceImpl userDetailServiceImpl;
	
	@Bean 
	public PasswordEncoder passwordEncoder() { 
	    return new BCryptPasswordEncoder(); 
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	 
	    http.authorizeHttpRequests(
	            auth -> auth.requestMatchers("/signin", "/signup","/login").permitAll()
	            .requestMatchers("/teste").hasAnyAuthority("administrador")		 
	            .requestMatchers("/cadastrar-usuario","/listarUsuarios").hasAnyAuthority("administrador")		         
	            .requestMatchers("/logar","/Formulario","/listarTickets").hasAnyAuthority("administrador","usuario")
	            .requestMatchers("/assets/**").permitAll()
	            .requestMatchers("/","/Formulario/save").permitAll()
	
	            .anyRequest().authenticated()
	           )
	            .formLogin(formLogin -> formLogin	 
	            		.successHandler((request, response, authentication) -> {
	                        
	            			if (authentication.getAuthorities().stream()
	                                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("administrador"))) {
	                            
	            				response.sendRedirect("/listarUsuarios");
	                        } else if (authentication.getAuthorities().stream()
	                                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("usuario"))) {
	                            
	                        	response.sendRedirect("/listarTickets");
	                        }
	                    })
	            		
	                  //  .defaultSuccessUrl("/listarTickets", true)
	                    .loginPage("/login")
	                    .failureUrl("/login?error=true")
	                    .permitAll()
	            )	 
	    // .rememberMe(rememberMe -> rememberMe.key("AbcdEfghIjkl..."))
        // .logout(logout -> logout.logoutUrl("/signout").permitAll());

         .logout()
    	 .logoutRequestMatcher(
    	new AntPathRequestMatcher("/logout", "GET"))
    	 .logoutSuccessUrl("/?logout=true");
	    
	    return http.build();
	}
	

	
	
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)
	  throws Exception {
		//Serve de exemplo para gerar um senha criptografada
		BCryptPasswordEncoder b = new BCryptPasswordEncoder();
		System.out.println(b.encode("1234"));
		//Cripitografa a senha para salvar no banco de dados
		auth.userDetailsService(userDetailServiceImpl).passwordEncoder(new BCryptPasswordEncoder());
	 
	    
	  
	}

}