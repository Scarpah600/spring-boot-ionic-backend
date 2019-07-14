package br.com.scops.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.scops.domain.Categoria;

@Repository
public interface CategoriaDAO extends JpaRepository<Categoria, Integer> {
	

}
