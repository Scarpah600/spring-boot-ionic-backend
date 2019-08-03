package br.com.scops.servicies;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.scops.dao.CategoriaDAO;
import br.com.scops.dao.ProdutoDAO;
import br.com.scops.domain.Categoria;
import br.com.scops.domain.Produto;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoDAO dao;
	@Autowired
	private CategoriaDAO categoriadao;
	//Vai retorna um objeto pelo id 
    public Produto buscar (Integer id) {
    	Optional<Produto>obj = dao.findById(id);
    	return obj.orElseThrow(() -> new br.com.scops.servicies.ObjectNotFoundException(
    			"Objeto não Encontrado ! id" + id + ",Tipo: " + Produto.class.getName()));
    }
    public Page<Produto> search(String nome, List<Integer> ids,Integer page, Integer linesPerpage, String orderBy, String direction) {

		PageRequest pageRequest = PageRequest.of(page, linesPerpage, Direction.valueOf(direction), 
				orderBy);
    	//Busca paginada
		List<Categoria> categorias = categoriadao.findAllById(ids);
		return dao.search(nome,categorias,pageRequest);
    	
    }
}
