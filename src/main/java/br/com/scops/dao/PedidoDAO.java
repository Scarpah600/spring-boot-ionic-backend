package br.com.scops.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.scops.domain.Cliente;
import br.com.scops.domain.Pedido;

@Repository
public interface PedidoDAO extends JpaRepository<Pedido, Integer>{

	@Transactional(readOnly=true)
	Page<Pedido> findByCliente(Cliente cliente, Pageable pageRequest);
}
