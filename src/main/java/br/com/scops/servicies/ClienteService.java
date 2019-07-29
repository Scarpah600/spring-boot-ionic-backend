package br.com.scops.servicies;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.scops.dao.ClienteDAO;
import br.com.scops.domain.Cliente;
import br.com.scops.dto.ClienteDTO;

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
    
	public Cliente alterando(Cliente obj) {
		Cliente newObj = buscar(obj.getId());
		updateDate(newObj, obj);
		return dao.save(newObj);
	}

	public void deletar(Integer id) {
		buscar(id);
		try {
			dao.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir um cliente que possui produtos");
		}
	}

	public List<Cliente> buscarTodos() {
		return dao.findAll();
	}

	public Page<Cliente> buscarPagina(Integer page, Integer linesPerpage, String orderBy, String direction) {

		PageRequest pageRequest = PageRequest.of(page, linesPerpage, Direction.valueOf(direction), 
				orderBy);
		return dao.findAll(pageRequest);
	}
	public Cliente fromDTO(ClienteDTO objtDtO) {
          return new Cliente(objtDtO.getId(), objtDtO.getNome(),
        		  objtDtO.getEmail(), null, null);
	}
	
	private void updateDate(Cliente newobj,Cliente obj) {
		newobj.setNome(obj.getNome());
		newobj.setEmail(obj.getEmail());
	}
}
