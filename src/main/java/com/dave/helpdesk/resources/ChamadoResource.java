package com.dave.helpdesk.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import com.dave.helpdesk.domain.dtos.ChamadoDTO;
import com.dave.helpdesk.domain.enums.Status;
import com.dave.helpdesk.services.ChamadoService;

@RestController
@RequestMapping(value = "/chamados")
public class ChamadoResource {

	@Autowired
	private ChamadoService service;

	@GetMapping(value = "/{id}")
	public ResponseEntity<ChamadoDTO> findById(@PathVariable Integer id) {
		Chamado obj = service.findById(id);
		return ResponseEntity.ok().body(new ChamadoDTO(obj));
	}

	@GetMapping
	public ResponseEntity<List<ChamadoDTO>> findAll() {
		List<Chamado> list = service.findAll();
		List<ChamadoDTO> listDTO = list.stream().map(obj -> new ChamadoDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}
	
	//chamados?filter&titulo=titulo&status=0,1,2
	@GetMapping(params = "filter")
	public ResponseEntity<List<ChamadoDTO>> findByFilter(@RequestParam(defaultValue = "") String titulo, @RequestParam(defaultValue = "0,1,2") String status ) {
		
		System.out.println("titulo:"+titulo+ " status:"+ status);
		
		//trara null como parametro
		status = status.contains("null") ? "0,1,2" : status;

		boolean isDescricao = false;
		int statusArray[];
		
	    ArrayList<Integer> valores = new ArrayList<Integer>();
		for(Status x : Status.values()) {
			if(status.equalsIgnoreCase(x.getDescricao())) {
				valores.add(x.getCodigo());
				isDescricao = true;
			}
		}
		
		if (isDescricao) {			
			status = valores.toString();
			statusArray = Arrays.stream(status.substring(1, status.length()-1).split(",")).map(String::trim).mapToInt(Integer::parseInt).toArray();		
		} else {			
			//convert string para array de int
			statusArray = Arrays.stream(status.split(",")).map(String::trim).mapToInt(Integer::parseInt).toArray();			
		}		
		
		List<Chamado> list = service.findAllByFilter(titulo, statusArray);
		List<ChamadoDTO> listDTO = list.stream().map(obj -> new ChamadoDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}
	
	
	@PostMapping
	public ResponseEntity<ChamadoDTO> create(@Valid @RequestBody ChamadoDTO obj) {
		Chamado newObj = service.create(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	//chamados?with_response_body
	@PostMapping(params = "with_response_body")
	public ResponseEntity<ChamadoDTO> createWithResponseDto(@Valid @RequestBody ChamadoDTO objDTO) {
		Chamado newObj = service.create(objDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
		return ResponseEntity.created(uri).body(new ChamadoDTO(newObj));
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<ChamadoDTO> update(@PathVariable Integer id, @Valid @RequestBody ChamadoDTO objDTO) {
		Chamado newObj = service.update(id, objDTO);
		return ResponseEntity.ok().body(new ChamadoDTO(newObj));
	}
}
