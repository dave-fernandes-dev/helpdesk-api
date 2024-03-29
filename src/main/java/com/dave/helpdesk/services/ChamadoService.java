package com.dave.helpdesk.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dave.helpdesk.domain.Chamado;
import com.dave.helpdesk.domain.Cliente;
import com.dave.helpdesk.domain.Tecnico;
import com.dave.helpdesk.domain.dtos.ChamadoDTO;
import com.dave.helpdesk.domain.enums.Prioridade;
import com.dave.helpdesk.domain.enums.Status;
import com.dave.helpdesk.repositories.ChamadoRepository;
import com.dave.helpdesk.services.exceptions.ObjectnotFoundException;

@Service
public class ChamadoService {

	@Autowired
	private ChamadoRepository repository;
	@Autowired
	private TecnicoService tecnicoService;
	@Autowired
	private ClienteService clienteService;

	public Chamado findById(Integer id) {
		Optional<Chamado> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectnotFoundException("Objeto nao encontrado! ID: " + id));
	}

	public List<Chamado> findAll() {
		return repository.findAll();
	}
	
	public List<Chamado> findAllByFilter(String titulo, int[] status) {
		return repository.findByFilter(titulo, status);
	}

	public Chamado create(ChamadoDTO obj) {
		return repository.save(newChamado(obj));
	}

	public Chamado update(Integer id, @Valid ChamadoDTO objDTO) {
		objDTO.setId(id);
		Chamado oldObj = findById(id);
		oldObj = newChamado(objDTO);
		return repository.save(oldObj);
	}
	
	public void delete(Integer id) {
		//Chamado obj = findById(id);
		//checaIntegridadeReferencial(obj); 
		repository.deleteById(id);
	}

	@SuppressWarnings("unlikely-arg-type")
	private Chamado newChamado(ChamadoDTO obj) {
		
		Tecnico tecnico = null;
		Cliente cliente = null;

		if (obj.getTecnico() != null) {
			tecnico = tecnicoService.findById(obj.getTecnico());
		}

		// se nomeTecnico vier do FlutterFlow...
		String nomeTecnico = obj.getNomeTecnico();
		if (nomeTecnico !=null && !nomeTecnico.isBlank()) {
			tecnico = tecnicoService.findByNome(nomeTecnico);
		}

		if (obj.getCliente() != null) {
			cliente = clienteService.findById(obj.getCliente());
		}

		// se nomeCliente vier do FlutterFlow...
		String nomeCliente = obj.getNomeCliente();
		if (nomeCliente!=null && !nomeCliente.isBlank()) {
			cliente = clienteService.findByNome(nomeCliente);
		}

		Chamado chamado = new Chamado();
		if (obj.getId() != null) {
			chamado.setId(obj.getId());
		}

		if (obj.getStatus().equals(Status.ENCERRADO)) {
			chamado.setDataFechamento(LocalDate.now());
		}

		chamado.setTecnico(tecnico);
		chamado.setCliente(cliente);
		chamado.setPrioridade(Prioridade.toEnum(obj.getPrioridade()));
		chamado.setStatus(Status.toEnum(obj.getStatus()));
		chamado.setTitulo(obj.getTitulo());
		chamado.setObservacoes(obj.getObservacoes());
		return chamado;
	}

}
