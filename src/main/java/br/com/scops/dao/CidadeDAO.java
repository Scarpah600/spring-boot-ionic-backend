package br.com.scops.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.scops.domain.Cidade;

@Repository
public interface CidadeDAO extends JpaRepository<Cidade, Integer> {
	

}
