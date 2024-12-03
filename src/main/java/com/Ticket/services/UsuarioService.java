package com.Ticket.services;




import java.io.IOException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Ticket.model.UsuarioModel;
import com.Ticket.model.UsuarioPermissao;
import com.Ticket.repository.UsuarioPermissaoRepository;
import com.Ticket.repository.UsuarioRepository;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class UsuarioService{

	@Autowired
	private AuthenticationManager authenticationManager;

	
	
		@Autowired
		private UsuarioRepository usuarioRepository;

		@Autowired
		private  UsuarioPermissaoRepository usuarioPermissaoRepository;

		
		
		  public String salvarUsuario(UsuarioModel usuario, RedirectAttributes redirectAttributes) {
			  
			  
			  BCryptPasswordEncoder b = new BCryptPasswordEncoder();  			
			
			  	SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
		        String dataFormatada = sdf.format(usuario.getData_nascimento());
		        System.out.println(dataFormatada);
		        String senhaCriptografada = b.encode(dataFormatada);
				System.out.println(senhaCriptografada);
				usuario.setSenha(senhaCriptografada);  
				usuario.setPrimeiro_login(true);
			  
		        if (!validarCPF(usuario.getCpf())) {
		            redirectAttributes.addFlashAttribute("mensagem", "CPF inválido");
		            
		            System.out.println( "CPF inválido");
		            
		            
		            return "redirect:/cadastrar-usuario"; 
		        } else if (usuarioRepository.existsByCpf(usuario.getCpf())) {
		            redirectAttributes.addFlashAttribute("mensagem", "CPF já existe no banco de dados");
		            
		            System.out.println( "CPF já existe no banco de dados");
		            
		            return "redirect:/cadastrar-usuario"; 
		        } else if (usuarioRepository.existsByEmail(usuario.getEmail())) {
		            redirectAttributes.addFlashAttribute("mensagem", "E-mail já existe no banco de dados");
		            
		            
		            System.out.println("E-mail já existe no banco de dados");
		            return "redirect:/cadastrar-usuario"; 
		        } else {
		        
		         
		            UsuarioModel usuarioSalvo = usuarioRepository.save(usuario);
		            System.out.println("salvou");
		            
		            Long usuarioIdSalvo = usuarioSalvo.getId_usuario();
				    long usuarioPermissaoSalva = usuarioSalvo.getPermissao();
				    UsuarioPermissao usuarioPermissao = new UsuarioPermissao();
				    usuarioPermissao.setUsuarioId(usuarioIdSalvo);
				    usuarioPermissao.setPermissaoId(usuarioPermissaoSalva);
				    System.out.println("salvou no permissao repositori com isso " + usuarioIdSalvo+ " "+ usuarioPermissaoSalva);
				    				    
				    usuarioPermissaoRepository.save(usuarioPermissao);
		            
		            
		            redirectAttributes.addFlashAttribute("mensagem", "Usuário salvo com sucesso");
		            return "redirect:/listarUsuarios"; 
		        }
		    }
		
		
		  
		  
		  
		  
		  public static boolean validarCPF(String cpf) {
			    cpf = cpf.replaceAll("[^0-9]", "");

			    if (cpf.length() != 11) {
			        return false;
			    }
			    if (cpf.matches("(\\d)\\1{10}")) {
			        return false;
			    }
			    int soma = 0;
			    for (int i = 0; i < 9; i++) {
			        soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
			    }
			    int primeiroDigito = 11 - (soma % 11);
			    if (primeiroDigito >= 10) {
			        primeiroDigito = 0;
			    }   
			    soma = 0;
			    for (int i = 0; i < 10; i++) {
			        soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
			    }
			    int segundoDigito = 11 - (soma % 11);
			    if (segundoDigito >= 10) {
			        segundoDigito = 0;
			    }
			    return cpf.charAt(9) == Character.forDigit(primeiroDigito, 10) &&
			           cpf.charAt(10) == Character.forDigit(segundoDigito, 10);
			}
			
		  
		  
		  
		  
		  
		  
		  
		  public void MudarSenha(String senha, String confirmaSenha, Authentication authentication, RedirectAttributes redirectAttributes, HttpServletRequest request, 
                  HttpServletResponse response) throws IOException,ServletException {

BCryptPasswordEncoder b = new BCryptPasswordEncoder();

if (!senha.equals(confirmaSenha)) {
  redirectAttributes.addFlashAttribute("mensagem", "As senhas não coincidem.");
  RequestDispatcher dispatcher = request.getRequestDispatcher("/redefinirSenha");
  dispatcher.forward(request, response);
  return;
}

if (senha.length() < 6) {
  redirectAttributes.addFlashAttribute("mensagem", "A senha precisa ter pelo menos 6 caracteres.");
  RequestDispatcher dispatcher = request.getRequestDispatcher("/redefinirSenha");
  dispatcher.forward(request, response);
  return;
}

String senhaCriptografada = b.encode(senha);
UsuarioModel usuario =  usuarioRepository.findByCpf(authentication.getName());

usuario.setSenha(senhaCriptografada);
usuario.setPrimeiro_login(false);
usuarioRepository.save(usuario);

// Criar um novo token de autenticação com a senha em texto plano
UsernamePasswordAuthenticationToken newAuthToken = new UsernamePasswordAuthenticationToken(
  usuario.getUsername(),
  senha,  // Passa a senha em texto plano
  authentication.getAuthorities()
);

Authentication newAuthentication = authenticationManager.authenticate(newAuthToken);
SecurityContextHolder.getContext().setAuthentication(newAuthentication);

// Adicionar mensagem de sucesso
if (authentication.getAuthorities().stream()
      .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("administrador"))) {
  redirectAttributes.addFlashAttribute("mensagem", "Senha redefinida com sucesso.");
  response.sendRedirect("/listarUsuarios");
} else if (authentication.getAuthorities().stream()
      .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("usuario"))) {
  redirectAttributes.addFlashAttribute("mensagem", "Senha redefinida com sucesso.");
  response.sendRedirect("/listarTickets");
}
}

		  
		  
		  
		
		 
		  
		  
		  
		  }
		  
		
// metodo mais simples para salvar mas no casos ele somente salva nao redireciona ///
//		   public void salvarUsuario(UsuarioModel usuario, RedirectAttributes redirectAttributes ) {
//			BCryptPasswordEncoder b = new BCryptPasswordEncoder();  
//			System.out.println(usuario.getSenha());
//			String senhaCriptografada = b.encode(usuario.getSenha());
//			System.out.println(senhaCriptografada);
//			usuario.setSenha(senhaCriptografada);
//		    UsuarioModel usuarioSalvo = usuarioRepository.save(usuario);
//		    Long usuarioIdSalvo = usuarioSalvo.getId_usuario();
//		    long usuarioPermissaoSalva = usuarioSalvo.getPermissao();
//		    
//		    UsuarioPermissao usuarioPermissao = new UsuarioPermissao();
//		    usuarioPermissao.setUsuarioId(usuarioIdSalvo);
//		    usuarioPermissao.setPermissaoId(usuarioPermissaoSalva);
//		    usuarioPermissaoRepository.save(usuarioPermissao);
//
//
//		   }
	

