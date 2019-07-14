package br.com.scops.servicies;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.scops.dao.ClienteDAO;
import br.com.scops.domain.Cliente;

@Service
public class ClienteService {

	@Autowired
	private ClienteDAO dao;
	
	//Vai retorna um objeto pelo id 
    public Cliente buscar (Integer id) {
    	Optional<Cliente>obj = dao.findById(id);
    	return obj.orElseThrow(() -> new br.com.scops.servicies.ObjectNotFoundException(
    			"Objeto não Encontrado ! id" + id + ",Tipo: " + Cliente.class.getName()));
    }
		
}
