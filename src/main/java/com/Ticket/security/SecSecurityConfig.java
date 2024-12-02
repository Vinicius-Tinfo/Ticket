package com.Ticket.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.Ticket.model.UsuarioModel;
import com.Ticket.repository.UsuarioRepository;



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
	            .requestMatchers("/logar","/Formulario","/listarTickets").hasAnyAuthority("administrador","usuario")
	            .requestMatchers("/assets/**").permitAll()
	            .requestMatchers("/","/Formulario/save").permitAll()
	
	            .anyRequest().authenticated()
	           )
	            .formLogin(formLogin -> formLogin	 
	            		
	            		
	            		.successHandler((request, response, authentication) -> {

	            		    // Obtém o usuário autenticado
	            		    UsuarioModel usuario =  usuarioRepository.findByCpf(authentication.getName());

	            		    // Verificando se é o primeiro login
	            		    if (usuario.isPrimeiro_login()) {  // Modificado para acessar a variável 'primeiro_login'
	            		        response.sendRedirect("/teste");  // Redireciona para a página de mudança de senha
	            		    } else if (authentication.getAuthorities().stream()
	            		            .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("administrador"))) {

	            		        // Redireciona para a página de listagem de usuários caso seja um administrador
	            		        response.sendRedirect("/listarUsuarios");

	            		    } else if (authentication.getAuthorities().stream()
	            		            .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("usuario"))) {

	            		        // Redireciona para a página de listagem de tickets caso seja um usuário comum
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