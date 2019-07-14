package br.com.scops.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.scops.domain.Estado;

@Repository
public interface EstadoDAO extends JpaRepository<Estado, Integer> {
	

}
