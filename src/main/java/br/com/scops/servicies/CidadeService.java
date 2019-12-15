package br.com.scops.servicies;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.scops.dao.CidadeDAO;
import br.com.scops.domain.Cidade;

@Service
public class CidadeService {

	@Autowired
	private CidadeDAO repo;

	public List<Cidade> findByEstado(Integer estadoId) {
		return repo.findCidades(estadoId);
	}
}
