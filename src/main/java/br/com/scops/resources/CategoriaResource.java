package br.com.scops.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.scops.domain.Categoria;
import br.com.scops.dto.CategoriaDTO;
import br.com.scops.servicies.CategoriaService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {
	@Autowired
	private CategoriaService service;

	// Ele vai acessar o serviço
	// vai retorna procotocolo https
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) {
		Categoria obj = service.buscar(id);
		return ResponseEntity.ok().body(obj);

	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> inserindo(@Valid @RequestBody CategoriaDTO objtDto) {
		Categoria obj = service.fromDTO(objtDto);
		obj = service.inserindo(obj);
		URI url = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(url).build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> alterar(@Valid @RequestBody CategoriaDTO objDto, @PathVariable Integer id) {
		Categoria obj = service.fromDTO(objDto);
		obj.setId(id);
		obj = service.alterando(obj);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deletar(@PathVariable Integer id) {
		service.deletar(id);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<CategoriaDTO>> buscaTodos() {
		List<Categoria> lista = service.buscarTodos();
		List<CategoriaDTO> listaDto = lista.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listaDto);

	}
	
	@RequestMapping(value="/page",method = RequestMethod.GET)
	public ResponseEntity<Page<CategoriaDTO>> buscaPagina(
			@RequestParam(value="page",defaultValue = "0")Integer page, 
			@RequestParam(value="linesPerpage",defaultValue = "24")Integer linesPerpage,
			@RequestParam(value="orderBy",defaultValue = "0")String orderBy, 
			@RequestParam(value="direction",defaultValue = "0")String direction) {
		Page<Categoria> lista = service.buscarPagina(page, linesPerpage, orderBy, direction);
		Page<CategoriaDTO> listaDto = lista.map(obj -> new CategoriaDTO(obj));
		return ResponseEntity.ok().body(listaDto);

	}
	

}
