package br.com.scops.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.scops.domain.Produto;

@Repository
public interface ProdutoDAO extends JpaRepository<Produto, Integer>{

}
