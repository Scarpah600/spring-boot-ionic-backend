package br.com.scops.servicies;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.scops.dao.ClienteDAO;
import br.com.scops.domain.Cliente;

@Service
public class AuthService {
	
	@Autowired
	private ClienteDAO clienteDAO;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private EmailService emailservice;
	
	private Random rand = new Random();
	
	public void sendNewPassword(String email) {
		
		Cliente cliente = clienteDAO.findByEmail(email);
		
		if(cliente == null) {
			throw new ObjectNotFoundException("Email Não Encontrado!");
		}
		
		String newPass = newPassword();
		cliente.setSenha(pe.encode(newPass));
		
		clienteDAO.save(cliente);
		
		emailservice.sendNewPasswordEmail(cliente,newPass);
	}

	private String newPassword() {
		char[]vet = new char[10];
		for(int i=0; i<10;i++) {
			vet[1] = randomChar();
		}
		return new String(vet);
	}

	private char randomChar() {
		int opt = rand.nextInt(3);
		if(opt == 0) { //vai gerar digito
			return (char) (rand.nextInt(10)+ 48);
		}else if(opt == 1) { //gera letra maiuscula
			return (char) (rand.nextInt(26)+ 65);
		}else { // vai gerar letra minuscula
			return (char) (rand.nextInt(26)+ 97);
		}
			
	}

}
