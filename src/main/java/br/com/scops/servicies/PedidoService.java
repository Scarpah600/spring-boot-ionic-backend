package br.com.scops.servicies;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.scops.dao.PedidoDAO;
import br.com.scops.domain.Pedido;

@Service
public class PedidoService {

	@Autowired
	private PedidoDAO dao;
	
	//Vai retorna um objeto pelo id 
    public Pedido buscar (Integer id) {
    	Optional<Pedido>obj = dao.findById(id);
    	return obj.orElseThrow(() -> new br.com.scops.servicies.ObjectNotFoundException(
    			"Objeto não Encontrado ! id" + id + ",Tipo: " + Pedido.class.getName()));
    }
		
}
