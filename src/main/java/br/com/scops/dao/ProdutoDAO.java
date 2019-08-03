package br.com.scops.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.scops.domain.Categoria;
import br.com.scops.domain.Produto;

@Repository
public interface ProdutoDAO extends JpaRepository<Produto, Integer>{

	Page<Produto> search(String nome, List<Categoria>categorias ,Pageable pageRequest);
}
