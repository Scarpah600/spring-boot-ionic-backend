package br.com.scops.servicies;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.scops.dao.CategoriaDAO;
import br.com.scops.domain.Categoria;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaDAO dao;
	
	//Vai retorna um objeto pelo id 
    public Categoria buscar (Integer id) {
    	Optional<Categoria>obj = dao.findById(id);
    	return obj.orElseThrow(() -> new br.com.scops.servicies.ObjectNotFoundException(
    			"Objeto não Encontrado ! id" + id + ",Tipo: " + Categoria.class.getName()));
    }
		
}
