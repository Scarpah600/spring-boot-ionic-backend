package br.com.scops.servicies;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.scops.dao.CategoriaDAO;
import br.com.scops.domain.Categoria;
import br.com.scops.dto.CategoriaDTO;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaDAO dao;

	// Vai retorna um objeto pelo id
	public Categoria buscar(Integer id) {
		Optional<Categoria> obj = dao.findById(id);
		return obj.orElseThrow(() -> new br.com.scops.servicies.ObjectNotFoundException(
				"Objeto não Encontrado ! id" + id + ",Tipo: " + Categoria.class.getName()));
	}

	public Categoria inserindo(Categoria obj) {
		obj.setId(null);
		return dao.save(obj);
	}

	public Categoria alterando(Categoria obj) {
		Categoria newObj = buscar(obj.getId());
		updateDate(newObj, obj);
		return dao.save(newObj);
	}

	public void deletar(Integer id) {
		buscar(id);
		try {
			dao.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel deletar uma categoria que possui produtos");
		}
	}

	public List<Categoria> buscarTodos() {
		return dao.findAll();
	}

	public Page<Categoria> buscarPagina(Integer page, Integer linesPerpage, String orderBy, String direction) {

		PageRequest pageRequest = PageRequest.of(page, linesPerpage, Direction.valueOf(direction), 
				orderBy);
		return dao.findAll(pageRequest);
	}
	public Categoria fromDTO(CategoriaDTO objtDtO) {
		return new Categoria(objtDtO.getId(), objtDtO.getNome());
	}
	private void updateDate(Categoria newobj,Categoria obj) {
		newobj.setNome(obj.getNome());
	}
}
