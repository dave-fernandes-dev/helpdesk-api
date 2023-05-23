package com.dave.helpdesk.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.dave.helpdesk.domain.Chamado;
import com.dave.helpdesk.domain.Cliente;
import com.dave.helpdesk.domain.Tecnico;
import com.dave.helpdesk.domain.dtos.ChamadoDTO;
import com.dave.helpdesk.domain.dtos.ClienteDTO;
import com.dave.helpdesk.domain.dtos.TecnicoDTO;
import com.dave.helpdesk.services.ClienteService;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {

	@Autowired
	private ClienteService service;

	@GetMapping(value = "/{id}")
	public ResponseEntity<ClienteDTO> findById(@PathVariable Integer id) {
		Cliente obj = service.findById(id);
		return ResponseEntity.ok().body(new ClienteDTO(obj));
	}

	@GetMapping
	public ResponseEntity<List<ClienteDTO>> findAll() {
		List<Cliente> list = service.findAll();
		List<ClienteDTO> listDTO = list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}
	
	//clientes/{cpf}?cpf
	@GetMapping(value = "/{cpf}", params = "cpf")
	public ResponseEntity<ClienteDTO> findByCpf(@PathVariable String cpf) {
		Cliente obj = service.findByCpf(cpf);
		return ResponseEntity.ok().body(new ClienteDTO(obj));
	}	
	
	//clientes?filter&search=search
	@GetMapping(params = "filter")
	public ResponseEntity<List<ClienteDTO>> findByFilter(@RequestParam(defaultValue = "") String search) {
		
		//System.out.println("titulo:"+titulo+ " status:"+ status);
	
		List<Cliente> list = service.findAllByFilter(search);
		List<ClienteDTO> listDTO = list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}

	@PostMapping
	public ResponseEntity<ClienteDTO> create(@Valid @RequestBody ClienteDTO objDTO) {
		Cliente newObj = service.create(objDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	//clientes?with_response_body
	@PostMapping(params = "with_response_body")
	//@ResponseStatus(HttpStatus.CREATED) 
	public ResponseEntity<ClienteDTO> createWithResponseDto(@Valid @RequestBody ClienteDTO objDTO) {
		Cliente newObj = service.create(objDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
		//return ResponseEntity(new ClienteDTO(newObj),uri.bui, HttpStatus.CREATED);
		return ResponseEntity.created(uri).body(new ClienteDTO(newObj));
		//return ResponseEntity.created().body(new ClienteDTO(obj));
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<ClienteDTO> update(@PathVariable Integer id, @Valid @RequestBody ClienteDTO objDTO) {
		Cliente obj = service.update(id, objDTO);
		return ResponseEntity.ok().body(new ClienteDTO(obj));
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<ClienteDTO> delete(@PathVariable Integer id) {
		service.delete(id); 
		return ResponseEntity.noContent().build();
	}
	
	//clientes/{id}?cpf
	@DeleteMapping(value = "/{id}", params = "cpf")
	public ResponseEntity<ClienteDTO> deleteByCpf(@PathVariable String id) {
		service.deleteByCpf(id); 
		return ResponseEntity.noContent().build();
	}
	
	//clientes/?cpf={cpf}
	@DeleteMapping()
	public ResponseEntity<ClienteDTO> deleteByCpf2(@RequestParam(value = "cpf") String cpf) {
		service.deleteByCpf(cpf); 
		return ResponseEntity.noContent().build();
	}

}
