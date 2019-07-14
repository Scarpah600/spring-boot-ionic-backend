package br.com.scops;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.scops.dao.CategoriaDAO;
import br.com.scops.dao.CidadeDAO;
import br.com.scops.dao.ClienteDAO;
import br.com.scops.dao.EnderecoDAO;
import br.com.scops.dao.EstadoDAO;
import br.com.scops.dao.ProdutoDAO;
import br.com.scops.domain.Categoria;
import br.com.scops.domain.Cidade;
import br.com.scops.domain.Cliente;
import br.com.scops.domain.Endereco;
import br.com.scops.domain.Estado;
import br.com.scops.domain.Produto;
import br.com.scops.domain.enums.TipoCliente;

@SpringBootApplication
public class VendasApplication implements CommandLineRunner{

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
	
	
	
	public static void main(String[] args) {
		SpringApplication.run(VendasApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//Operacao de instanciação automatico
		Categoria cat1 = new Categoria(null,"Informatica");
		Categoria cat2 = new Categoria(null,"Escritorio");
		
		Produto p1 = new Produto(null,"Computador", 2000.00);
		Produto p2 = new Produto(null,"Impressora", 800.00);
		Produto p3 = new Produto(null,"Mouse", 80.00);
		
		Estado est1 = new Estado(null,"Parana");
		Estado est2 = new Estado(null,"Sao paulo");
		Estado est3 = new Estado(null,"Minas Gerais");
		
		Cidade c1 = new Cidade(null,"Curitiba",est1);
		Cidade c2 = new Cidade(null,"Bom Sucesso",est2);
		Cidade c3 = new Cidade(null,"Uberlandia",est3);
		Cidade c4 = new Cidade(null,"Campo Largo",est1);
		Cidade c5 = new Cidade(null,"Itararé",est2);
		
		
		Cliente cli1 = new Cliente(null,"Gustavo Scarpin", "gustavo.scarpin.2020@gmail.com", "068.238.119-51",TipoCliente.PESSOAFISICA);
		
		cli1.getTelefones().addAll(Arrays.asList("97108812","3399-3873"));
		
		Endereco e1 = new Endereco(null, "Rua Teodoro Agustyn", "634", "Rua", "Vila Itaqui", "83604-360", cli1,c4);
		
		
		cli1.getEnderecos().addAll(Arrays.asList(e1));
		
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		est1.getCidades().addAll(Arrays.asList(c1,c4));
		est2.getCidades().addAll(Arrays.asList(c2,c5));
		est3.getCidades().addAll(Arrays.asList(c3));
		
		dao.saveAll(Arrays.asList(cat1,cat2));
	    pdao.saveAll(Arrays.asList(p1,p2,p3));
	    estadodao.saveAll(Arrays.asList(est1,est2,est3));
	    cidadedao.saveAll(Arrays.asList(c1,c2,c3,c4,c5));
	    clientedao.saveAll(Arrays.asList(cli1));
	    enderecodao.saveAll(Arrays.asList(e1));
	}

	
	
}
