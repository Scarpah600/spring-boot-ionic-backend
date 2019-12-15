package br.com.scops.servicies;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.scops.dao.EstadoDAO;
import br.com.scops.domain.Estado;

@Service
public class EstadoService {

	@Autowired
	private EstadoDAO repo;
	
	public List<Estado> findAll() {
		return repo.findAllByOrderByNome();
	}
}
