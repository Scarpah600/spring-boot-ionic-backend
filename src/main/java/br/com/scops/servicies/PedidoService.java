package br.com.scops.servicies;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.scops.dao.ItemPedidoDAO;
import br.com.scops.dao.PagamentoDAO;
import br.com.scops.dao.PedidoDAO;
import br.com.scops.domain.ItemPedido;
import br.com.scops.domain.PagamentoComBoleto;
import br.com.scops.domain.Pedido;
import br.com.scops.domain.enums.EstadoPagamento;
import br.com.scops.resources.BoletoService;

@Service
public class PedidoService {
	@Autowired
	private PedidoDAO repo;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoDAO pagamentoRepository;
	
	@Autowired
	private ItemPedidoDAO itemPedidoRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	public Pedido buscar (Integer id) {
    	Optional<Pedido>obj = repo.findById(id);
    	return obj.orElseThrow(() -> new br.com.scops.servicies.ObjectNotFoundException(
    			"Objeto não Encontrado ! id" + id + ",Tipo: " + Pedido.class.getName()));
    }
	
	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		obj = repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		for (ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setPreço(produtoService.buscar(ip.getProduto().getId()).getPreco());
			ip.setPedido(obj);
		}
		itemPedidoRepository.saveAll(obj.getItens());
		return obj;
	}
}
