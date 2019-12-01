package br.com.scops.servicies;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.scops.dao.CategoriaDAO;
import br.com.scops.dao.CidadeDAO;
import br.com.scops.dao.ClienteDAO;
import br.com.scops.dao.EnderecoDAO;
import br.com.scops.dao.EstadoDAO;
import br.com.scops.dao.ItemPedidoDAO;
import br.com.scops.dao.PagamentoDAO;
import br.com.scops.dao.PedidoDAO;
import br.com.scops.dao.ProdutoDAO;
import br.com.scops.domain.Categoria;
import br.com.scops.domain.Cidade;
import br.com.scops.domain.Cliente;
import br.com.scops.domain.Endereco;
import br.com.scops.domain.Estado;
import br.com.scops.domain.ItemPedido;
import br.com.scops.domain.Pagamento;
import br.com.scops.domain.PagamentoComCartao;
import br.com.scops.domain.Pedido;
import br.com.scops.domain.Produto;
import br.com.scops.domain.enums.EstadoPagamento;
import br.com.scops.domain.enums.Perfil;
import br.com.scops.domain.enums.TipoCliente;

@Service
public class DBService {
	@Autowired
	private CategoriaDAO dao;
	@Autowired
	private EstadoDAO estadodao;
	@Autowired
	private CidadeDAO cidadedao;
	@Autowired
	private ProdutoDAO pdao;
	@Autowired
	private EnderecoDAO enderecodao;
	@Autowired
	private ClienteDAO clientedao;
	@Autowired
	private PedidoDAO pedidodao;
	@Autowired
	private PagamentoDAO pagamentodao;
	@Autowired
	private ItemPedidoDAO itempedidodao;
	@Autowired
	private BCryptPasswordEncoder pe;
	
	public void instantiateTestDatabase() throws ParseException {
		Categoria cat1 = new Categoria(null, "Informatica");
		Categoria cat2 = new Categoria(null, "Escritorio");
		Categoria cat3 = new Categoria(null, "Cama mesa banho");
		Categoria cat4 = new Categoria(null, "Domèstico");
		Categoria cat5 = new Categoria(null, "Esportes");
		Categoria cat6 = new Categoria(null, "Alimentos");
		Categoria cat7 = new Categoria(null, "Calsados");
		Categoria cat8 = new Categoria(null, "Equipamentos Eletronicos");
		Categoria cat9 = new Categoria(null, "Equipamento de Jardinagem");
		Categoria cat10 = new Categoria(null, "Materiais de Construção");
		

		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		Produto p4 = new Produto(null, "Bola de Futebol", 100.00);
		Produto p5 = new Produto(null, "TV 40 Polegadas", 2300.00);
		Produto p6 = new Produto(null, "Martelo", 30.00);
		Produto p7 = new Produto(null, "Pá para Jardinagem", 2000.00);
		Produto p8 = new Produto(null, "Armario", 1800.00);
		Produto p9 = new Produto(null, "Bolacha", 1.45);
		Produto p10 = new Produto(null, "Toalha", 75.00);
		Produto p11 = new Produto(null, "Tapete", 150.00);
		Produto p12 = new Produto(null, "Biscoito", 3.90);

		Estado est1 = new Estado(null, "Parana");
		Estado est2 = new Estado(null, "Sao paulo");
		Estado est3 = new Estado(null, "Minas Gerais");

		Cidade c1 = new Cidade(null, "Curitiba", est1);
		Cidade c2 = new Cidade(null, "Bom Sucesso", est2);
		Cidade c3 = new Cidade(null, "Uberlandia", est3);
		Cidade c4 = new Cidade(null, "Campo Largo", est1);
		Cidade c5 = new Cidade(null, "Itararé", est2);

		Cliente cli1 = new Cliente(null, "Gustavo Scarpin", "gustavo.scarpin.2020@gmail.com", "068.238.119-51",
				TipoCliente.PESSOAFISICA,pe.encode("123"));
		Cliente cli2 = new Cliente(null, "Leonan Mattos", "leonan.mattos@gmail.com", "048.138.519-11",
				TipoCliente.PESSOAFISICA,pe.encode("123"));
		
		
		cli1.addPerfil(Perfil.ADMIN);

		
		
		cli1.getTelefones().addAll(Arrays.asList("97108812", "3399-3873"));
		cli2.getTelefones().addAll(Arrays.asList("97108812", "3399-3873"));

		Endereco e1 = new Endereco(null, "Rua Teodoro Agustyn", "634", "Rua", "Vila Itaqui", "83604-360", cli1, c4);

		Endereco e2 = new Endereco(null, "Rua Fernandes de Barros ", "634", "Rua", "Rua XV", "83604-360", cli2, c1);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1));
		cli2.getEnderecos().addAll(Arrays.asList(e2));

		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		cat3.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat4.getProdutos().addAll(Arrays.asList(p8, p10, p11));
		cat5.getProdutos().addAll(Arrays.asList(p4));
		cat7.getProdutos().addAll(Arrays.asList(p9, p12));
		cat8.getProdutos().addAll(Arrays.asList(p5));
		cat9.getProdutos().addAll(Arrays.asList(p7));
		cat10.getProdutos().addAll(Arrays.asList(p6));

		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		p4.getCategorias().addAll(Arrays.asList(cat5));
		p5.getCategorias().addAll(Arrays.asList(cat8));
		p6.getCategorias().addAll(Arrays.asList(cat10));
		p7.getCategorias().addAll(Arrays.asList(cat9));
		p8.getCategorias().addAll(Arrays.asList(cat4));
		p9.getCategorias().addAll(Arrays.asList(cat7));
		p10.getCategorias().addAll(Arrays.asList(cat4));
		p11.getCategorias().addAll(Arrays.asList(cat4));
		p12.getCategorias().addAll(Arrays.asList(cat7,cat10));

		est1.getCidades().addAll(Arrays.asList(c1, c4));
		est2.getCidades().addAll(Arrays.asList(c2, c5));
		est3.getCidades().addAll(Arrays.asList(c3));

		dao.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7, cat8, cat9, cat10));
		pdao.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12));
		estadodao.saveAll(Arrays.asList(est1, est2, est3));
		cidadedao.saveAll(Arrays.asList(c1, c2, c3, c4, c5));
		clientedao.saveAll(Arrays.asList(cli1, cli2));
		enderecodao.saveAll(Arrays.asList(e1, e2));

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);

		Pedido ped2 = new Pedido(null, sdf.parse("30/09/2017 15:00"), cli2, e2);

		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);

		Pagamento pagto2 = new PagamentoComCartao(null, EstadoPagamento.PENDENTE, ped2, 1);
		ped2.setPagamento(pagto2);

		cli1.getPedidos().addAll(Arrays.asList(ped1));

		cli2.getPedidos().addAll(Arrays.asList(ped2));

		pedidodao.saveAll(Arrays.asList(ped1, ped2));
		pagamentodao.saveAll(Arrays.asList(pagto1, pagto2));

		ItemPedido ep1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ep2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);

		ped1.getItens().addAll(Arrays.asList(ep1, ep2));

		p1.getItens().addAll(Arrays.asList(ep1));
		p3.getItens().addAll(Arrays.asList(ep2));

		itempedidodao.saveAll(Arrays.asList(ep1, ep2));
	}
}
