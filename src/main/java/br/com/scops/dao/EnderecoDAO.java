package br.com.scops.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.scops.domain.Endereco;

@Repository
public interface EnderecoDAO extends JpaRepository<Endereco, Integer> {
	

}
