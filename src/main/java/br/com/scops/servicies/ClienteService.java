package br.com.scops.servicies;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.scops.dao.ClienteDAO;
import br.com.scops.dao.EnderecoDAO;
import br.com.scops.domain.Cidade;
import br.com.scops.domain.Cliente;
import br.com.scops.domain.Endereco;
import br.com.scops.domain.enums.TipoCliente;
import br.com.scops.dto.ClienteDTO;
import br.com.scops.dto.ClienteNewDTO;

@Service
public class ClienteService {

	@Autowired
	private ClienteDAO dao;
	
	@Autowired
	private EnderecoDAO daoendereco;
	
	// Vai retorna um objeto pelo id
	public Cliente find(Integer id) {
		Optional<Cliente> obj = dao.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}

	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = dao.save(obj);
		daoendereco.saveAll(obj.getEnderecos());
		return obj;
	}
	public Cliente alterando(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return dao.save(newObj);
	}

	public void deletar(Integer id) {
		find(id);
		try {
			dao.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há pedidos relacionandos");
		}
	}

	public List<Cliente> buscarTodos() {
		return dao.findAll();
	}

	public Page<Cliente> buscarPagina(Integer page, Integer linesPerpage, String orderBy, String direction) {

		PageRequest pageRequest = PageRequest.of(page, linesPerpage, Direction.valueOf(direction), orderBy);
		return dao.findAll(pageRequest);
	}

	public Cliente fromDTO(ClienteDTO objtDtO) {
		return new Cliente(objtDtO.getId(), objtDtO.getNome(), objtDtO.getEmail(), null, null);
	}

	public Cliente fromDTO(ClienteNewDTO objDto) {
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()));
		Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());
		if (objDto.getTelefone2()!=null) {
			cli.getTelefones().add(objDto.getTelefone2());
		}
		if (objDto.getTelefone3()!=null) {
			cli.getTelefones().add(objDto.getTelefone3());
		}
		return cli;
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
}
