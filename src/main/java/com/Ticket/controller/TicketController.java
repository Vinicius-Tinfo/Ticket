package com.Ticket.controller;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Ticket.model.PermissaoModel;
import com.Ticket.model.TicketModel;
import com.Ticket.model.UsuarioModel;
import com.Ticket.repository.PermissaoRepository;
import com.Ticket.repository.TicketRepository;
import com.Ticket.repository.UsuarioPermissaoRepository;
import com.Ticket.repository.UsuarioRepository;
import com.Ticket.services.UsuarioService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class TicketController {

	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	
	@Autowired
	private UsuarioPermissaoRepository usuarioPermissaoRepository;
	
	@Autowired
	private TicketRepository ticketRepository;
	
	@Autowired
	private PermissaoRepository permissaoRepository   ;
	
	@Autowired
    private UsuarioService usuarioService; 
	
	@PostMapping({"/primeiroLogin"})
	public String PrimeiroLogin() {
		return "redefinirSenha";
	}
	
	@GetMapping({"/teste"})
	public String teste() {
	return "teste";
	}
	
	
	
	
	  @PostMapping("/apagarUsuario")
	    public String apagarUsuario(@RequestParam Long funcionarioId,
	                                 @RequestParam String senha,
	                                 RedirectAttributes redirectAttributes,
	                                 Authentication authentication) {

		  UsuarioModel adm =  usuarioRepository.findByCpf(authentication.getName());
	       

			  BCryptPasswordEncoder b = new BCryptPasswordEncoder();  			

	        System.out.println(senha);
	        if (!b.matches(senha, adm.getPassword())) {
	            redirectAttributes.addFlashAttribute("mensagem", "A senha do administrador está incorreta.");
	            return "redirect:/listarUsuarios"; // Página de erro (senha incorreta)
	        }else {
	        	Optional<UsuarioModel> usuarioOptional = usuarioRepository.findById(funcionarioId);
	            
	            if (usuarioOptional.isPresent()) {
	                UsuarioModel usuarioToUpdate = usuarioOptional.get();  

	        	long id = usuarioToUpdate.getId_usuario();
	        	
	        	usuarioPermissaoRepository.deleteById(id);
	        	usuarioRepository.deleteById(id);
		            
				redirectAttributes.addFlashAttribute("mensagem", "O Usuario foi excluido");
		            return "redirect:/listarUsuarios";
	        }

	        return "redirect:/listarUsuarios"; // Página de resultado (sucesso ou erro)
	    }
	}
	
	
	
	
	
	  @PostMapping("/resertarSenha")
	    public String resertarSenha(@RequestParam Long funcionarioId,
	                                 @RequestParam String senha,
	                                 RedirectAttributes redirectAttributes,
	                                 Authentication authentication) {

		  UsuarioModel adm =  usuarioRepository.findByCpf(authentication.getName());
	       

			  BCryptPasswordEncoder b = new BCryptPasswordEncoder();  			

	        System.out.println(senha);
	        if (!b.matches(senha, adm.getPassword())) {
	            redirectAttributes.addFlashAttribute("mensagem", "A senha do administrador está incorreta.");
	            return "redirect:/listarUsuarios"; // Página de erro (senha incorreta)
	        }else {
	        	Optional<UsuarioModel> usuarioOptional = usuarioRepository.findById(funcionarioId);
	            
	            if (usuarioOptional.isPresent()) {
	                UsuarioModel usuarioToUpdate = usuarioOptional.get();  

	        	SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
		        String dataFormatada = sdf.format(usuarioToUpdate.getData_nascimento());
		        System.out.println(dataFormatada);
		        String senhaCriptografada = b.encode(dataFormatada);
				System.out.println(senhaCriptografada);
				usuarioToUpdate.setSenha(senhaCriptografada);  
				usuarioToUpdate.setPrimeiro_login(true);
				usuarioRepository.save(usuarioToUpdate);
		            
				redirectAttributes.addFlashAttribute("mensagem", "A senha do usuario foi redefinida com sucesso.");
		            return "redirect:/listarUsuarios";
	        }

	        return "redirect:/listarUsuarios"; // Página de resultado (sucesso ou erro)
	    }
	}
	
	
	
	
	
	
	@PostMapping("/redefinirSenha")
	public String resetPassword(@RequestParam String senha, 
            @RequestParam String confirmaSenha, 
            Authentication authentication,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request, 
            HttpServletResponse response) throws ServletException, IOException {

usuarioService.MudarSenha(senha, confirmaSenha, authentication, redirectAttributes, request, response);

return null;
}
	
	
	
	
	
	
	
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
	
	/// mudar para metodo post futuramente com cofirmação//
	
	 @PostMapping("/deleteTicket-{id}")
		public String Deletar(@PathVariable("id") int id ) {
		ticketRepository.deleteById(id);
		return"redirect:/listarTickets";
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
