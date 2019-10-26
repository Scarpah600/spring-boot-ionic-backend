package br.com.scops.servicies;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	
	@Autowired
	private ClienteService clienteDao;
	
	@Autowired
	private EmailService emailService;
	
	public Pedido find(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}
	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(clienteDao.find(obj.getCliente().getId()));
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
			ip.setProduto(produtoService.find(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(obj);
		}
		itemPedidoRepository.saveAll(obj.getItens());
	     emailService.sendOrderConfirmationEmail(obj);
		return obj;
	}
}

