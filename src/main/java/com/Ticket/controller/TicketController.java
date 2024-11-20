package com.Ticket.controller;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Ticket.model.PermissaoModel;
import com.Ticket.model.TicketModel;
import com.Ticket.model.UsuarioModel;
import com.Ticket.repository.PermissaoRepository;
import com.Ticket.repository.TicketRepository;
import com.Ticket.repository.UsuarioRepository;
import com.Ticket.services.UsuarioService;

@Controller
public class TicketController {

	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private TicketRepository ticketRepository;
	
	@Autowired
	private PermissaoRepository permissaoRepository   ;
	
	@Autowired
    private UsuarioService usuarioService; 
	
	
	@GetMapping({"/"})
	public String start() {
	return "Formulario";
	}
	
	 @GetMapping("/login")
	    public String LoginPage() {
	        return "login";  
	    }
	
	
	
	@PostMapping({"/Formulario/save"})
	public ModelAndView TicketSave(TicketModel ticket , RedirectAttributes redirectAttributes) {
	ModelAndView mv = new ModelAndView("redirect:/");
	ticket.setData_criacao(LocalDateTime.now());
	ticketRepository.save(ticket);
    redirectAttributes.addFlashAttribute("mensagem", true);

		return mv;
	}
	
	@GetMapping("/listarTickets")
	public String listarTickets(Model ticket) {
		
		
	    List<TicketModel> tickets = ticketRepository.findAll(Sort.by(Sort.Order.asc("situacao")));
		ticket.addAttribute("ticket",tickets);
	    
//		ticket.addAttribute("ticket", ticketRepository.findAll());
		return "/listar-tickets";
	}
	
		
	@GetMapping({"/cadastrar-usuario"})
	public String cadastrarUsuario(Model model ) {
		List<PermissaoModel> permissoes = permissaoRepository.findAll();
        model.addAttribute("permissoes", permissoes);
		
		
	return "cadastrar-usuario";
	}
	
	
	 @PostMapping("/usuario/salvar")
	    public String salvarUsuario(UsuarioModel usuario, RedirectAttributes redirectAttributes) {
	        return usuarioService.salvarUsuario(usuario, redirectAttributes);
	    }
	
	 @GetMapping("/listarUsuarios")
	 public String listarUsuarios(Model model) {
		 List<UsuarioModel> usuarios = usuarioRepository.findAllUsuariosWithPermissoes();
	        
	        // Log para verificar o que está sendo retornado
	        System.out.println("Chamando o controlador de listarUsuarios");
	        
	        // Verifique o tamanho da lista de usuários
	        if (usuarios.isEmpty()) {
	            System.out.println("Nenhum usuário encontrado.");
	        } else {
	            System.out.println("Número de usuários encontrados: " + usuarios.size());
	        }
	        
	        // Passa a lista de usuários para o modelo
	        model.addAttribute("usuarios", usuarios);

	        // Passa a lista de usuários para o modelo
	   

	        return "/listar-usuarios"; 
	    }
	
	@GetMapping({"/logar"})
	public String TelaDeLogin() {
	return "redirect:/listarTickets";
	}
	

	@GetMapping({"/teste"})
	public String teste() {
	return "teste";
	}
	
	
	 @GetMapping("visualizar-{id}")
	  public String busca(@PathVariable int id, Model model){
	    Optional<TicketModel> ticket =  ticketRepository.findById(id);
	    try{
	      model.addAttribute("ticket", ticket.get());
	    }
	    catch(Exception err){ return "redirect:/"; }

		return ("Formulario-visualizar");
	  }
	 
	 
	 @PostMapping("{id}/atualizar")
	  public String atualizar(@PathVariable int id,  TicketModel ticket){
	   
	    if(!ticketRepository.existsById(id)){
	      return "redirect:/";
	    }
	    ticket.setSituacao(true);
	    ticket.setData_fechamento(LocalDateTime.now());
	    ticketRepository.save(ticket);

	    return "redirect:/listarTickets";
	  }
	 
	 
	 

	
	
	
}
