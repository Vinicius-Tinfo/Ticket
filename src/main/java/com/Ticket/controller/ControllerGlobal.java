package com.Ticket.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.Ticket.model.UsuarioModel;
import com.Ticket.repository.UsuarioRepository;

@ControllerAdvice
public class ControllerGlobal {
	
	
	@Autowired
UsuarioRepository usuarioRepository;
	
	
	
	   @ModelAttribute("userLogado")
	    public Map<String, String> addModel() {
	        Map<String, String> userInfo = new HashMap<>();
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        
	        if (authentication != null && authentication.isAuthenticated()) {
	            String cpfLogado = authentication.getName();
	            
	            UsuarioModel usuario = usuarioRepository.findByCpf(cpfLogado); 
	            
	            if (usuario != null) {
	                userInfo.put("nome", usuario.getNome()); 
	                userInfo.put("cpf", usuario.getCpf()); 
	                userInfo.put("permissao", ""+ usuario.getPermissao());
	            }
	        }
	        
	        return userInfo; 
	    }
	}
