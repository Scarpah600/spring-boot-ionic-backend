package br.com.scops;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.scops.dao.CategoriaDAO;
import br.com.scops.dao.CidadeDAO;
import br.com.scops.dao.EstadoDAO;
import br.com.scops.dao.ProdutoDAO;
import br.com.scops.domain.Categoria;
import br.com.scops.domain.Cidade;
import br.com.scops.domain.Estado;
import br.com.scops.domain.Produto;

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
	}

	
	
}
