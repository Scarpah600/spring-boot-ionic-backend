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

import br.com.scops.domain.Cliente;
import br.com.scops.dto.ClienteDTO;
import br.com.scops.dto.ClienteNewDTO;
import br.com.scops.servicies.ClienteService;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {
	@Autowired
	private ClienteService service;

	// Ele vai acessar o serviço
	// vai retorna procotocolo https
	@RequestMapping(value="/{id}",method = RequestMethod.GET)
	public ResponseEntity<Cliente> find(@PathVariable Integer id) {
		Cliente obj = service.find(id);
		return ResponseEntity.ok().body(obj);
		
	}

	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO objDto) {
		Cliente obj = service.fromDTO(objDto);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> alterar(@Valid @RequestBody ClienteDTO objDto, @PathVariable Integer id) {
		Cliente obj = service.fromDTO(objDto);
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
	public ResponseEntity<List<ClienteDTO>> buscaTodos() {
		List<Cliente> lista = service.buscarTodos();
		List<ClienteDTO> listaDto = lista.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listaDto);

	}
	
	@RequestMapping(value="/page",method = RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> buscaPagina(
			@RequestParam(value="page",defaultValue = "0")Integer page, 
			@RequestParam(value="linesPerpage",defaultValue = "24")Integer linesPerpage,
			@RequestParam(value="orderBy",defaultValue = "0")String orderBy, 
			@RequestParam(value="direction",defaultValue = "0")String direction) {
		Page<Cliente> lista = service.buscarPagina(page, linesPerpage, orderBy, direction);
		Page<ClienteDTO> listaDto = lista.map(obj -> new ClienteDTO(obj));
		return ResponseEntity.ok().body(listaDto);

	}
}
