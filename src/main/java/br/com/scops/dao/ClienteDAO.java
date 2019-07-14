package br.com.scops.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.scops.domain.Cliente;

@Repository
public interface ClienteDAO extends JpaRepository<Cliente, Integer> {
	

}
