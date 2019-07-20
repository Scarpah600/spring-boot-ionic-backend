package br.com.scops.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.scops.domain.ItemPedido;

@Repository
public interface ItemPedidoDAO extends JpaRepository<ItemPedido, Integer> {
	

}
