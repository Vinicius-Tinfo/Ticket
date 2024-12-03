package com.Ticket.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.Ticket.model.UsuarioModel;
import com.Ticket.repository.UsuarioRepository;

import jakarta.servlet.RequestDispatcher;



@Configuration
@EnableWebSecurity
public class SecSecurityConfig {
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	
	
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
	            .requestMatchers("/logar","/Formulario","/listarTickets","/primeiroLogin").hasAnyAuthority("administrador","usuario")
	            .requestMatchers("/assets/**").permitAll()
	            .requestMatchers("/","/Formulario/save").permitAll()
	
	            .anyRequest().authenticated()
	           )
	            .formLogin(formLogin -> formLogin	 
	            		
	            		
	            		.successHandler((request, response, authentication) -> {

	            		  
	            		    UsuarioModel usuario =  usuarioRepository.findByCpf(authentication.getName());

	            		    
	            		    if (usuario.isPrimeiro_login()) {  
	            		        //response.sendRedirect("/primeiroLogin"); 
	            		        
	            		        RequestDispatcher dispatcher = request.getRequestDispatcher("/primeiroLogin");         		       	            		       
	            		        dispatcher.forward(request, response);             		        
	            		        
	            		    } else if (authentication.getAuthorities().stream()
	            		            .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("administrador"))) {

	            		        response.sendRedirect("/listarUsuarios");

	            		    } else if (authentication.getAuthorities().stream()
	            		            .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("usuario"))) {

	            		        response.sendRedirect("/listarTickets");
	            		    }
	            		})
	            		
	            		
	            		
//	            		.successHandler((request, response, authentication) -> {
//	                        
//	            			if (authentication.getAuthorities().stream()
//	                                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("administrador"))) {
//	                            
//	            				response.sendRedirect("/listarUsuarios");
//	                        } else if (authentication.getAuthorities().stream()
//	                                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("usuario"))) {
//	                            
//	                        	response.sendRedirect("/listarTickets");
//	                        }
//	                    })
//	            		
	                 
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
	

	
	
	 @Bean
	 public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
	         AuthenticationManagerBuilder authenticationManagerBuilder =
	             http.getSharedObject(AuthenticationManagerBuilder.class);
	         
	         authenticationManagerBuilder
	         .userDetailsService(userDetailServiceImpl)  // Configura o UserDetailsService
	         .passwordEncoder(passwordEncoder());      // Configura o PasswordEncoder

	         
	  return authenticationManagerBuilder.build();}
	
	
	
	
	
	
	
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