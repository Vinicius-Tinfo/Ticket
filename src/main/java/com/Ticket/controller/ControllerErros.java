package com.Ticket.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class ControllerErros {

	  @GetMapping("/erro-500")
	    public String trigger500Error() {
	        throw new RuntimeException("Simulando erro 500");
	    }

	    // Simulando um erro genérico: A URL '/erro-generico' vai gerar um erro genérico
	    @GetMapping("/erro-generico")
	    public String triggerGenericError() throws Exception {
	        throw new Exception("Simulando erro genérico inesperado");
	    }
	    
	    @GetMapping("/erro-400")
	    public String trigger400Error() {
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Parâmetro inválido");
	    }
	    
	
	
	
}
