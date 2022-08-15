package com.dave.helpdesk.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.dave.helpdesk.domain.Pessoa;
import com.dave.helpdesk.domain.Tecnico;
import com.dave.helpdesk.domain.dtos.TecnicoDTO;
import com.dave.helpdesk.domain.enums.Perfil;
import com.dave.helpdesk.repositories.PessoaRepository;
import com.dave.helpdesk.repositories.TecnicoRepository;
import com.dave.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.dave.helpdesk.services.exceptions.ObjectnotFoundException;

@Service
public class TecnicoService {

	@Autowired
	private TecnicoRepository repository;
	@Autowired
	private PessoaRepository pessoaRepository;
	@Autowired
	private BCryptPasswordEncoder encoder;

	public Tecnico findById(Integer id) {
		Optional<Tecnico> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectnotFoundException("Objeto nao encontrado! Id: " + id));
	}
	
	public Tecnico findByNome(String nome) {
		Optional<Tecnico> obj = repository.findByNome(nome);
		return obj.orElseThrow(() -> new ObjectnotFoundException("Objeto nao encontrado! Nome: " + nome));
	}

	public List<Tecnico> findAll() {
		return repository.findAll();
	}

	public Tecnico create(TecnicoDTO objDTO) {
		objDTO.setId(null);
		objDTO.setSenha(encoder.encode(objDTO.getSenha()));
		validaPorCpfEEmail(objDTO);
		Tecnico newObj = new Tecnico(objDTO);
		return repository.save(newObj);
	}
 
	public Tecnico update(Integer id, @Valid TecnicoDTO objDTO) {
		objDTO.setId(id);
		Tecnico oldObj = findById(id);
		
		if(!objDTO.getSenha().equals(oldObj.getSenha())) 
			objDTO.setSenha(encoder.encode(objDTO.getSenha()));
		
		validaPorCpfEEmail(objDTO);
		oldObj = new Tecnico(objDTO);
		return repository.save(oldObj);
	}
	
	public Tecnico patchPerfil(Integer id, @Valid Map<String, String> map) {
		Set<String> perfis = Set.of(map.get("perfis").split(","));
		Tecnico oldObj = findById(id);
		
		oldObj.clearPerfis();
		System.out.println("veja ANTES:"+oldObj.getId()+ " "+oldObj.getPerfis());
		for (String string : perfis) {
			oldObj.addPerfil(Perfil.toEnum(string));			
		} 	
		System.out.println("veja DEPOIS:"+oldObj.getId()+ " "+oldObj.getPerfis());

		return repository.save(oldObj);

	}

	public void delete(Integer id) {
		Tecnico obj = findById(id);

		if (obj.getChamados().size() > 0) {
			throw new DataIntegrityViolationException("Técnico possui ordens de serviço e nao pode ser deletado!");
		}

		repository.deleteById(id);
	}

	private void validaPorCpfEEmail(TecnicoDTO objDTO) {
		Optional<Pessoa> obj = pessoaRepository.findByCpf(objDTO.getCpf());
		if (obj.isPresent() && obj.get().getId() != objDTO.getId()) {
			throw new DataIntegrityViolationException("CPF ja cadastrado no sistema!");
		}

		obj = pessoaRepository.findByEmail(objDTO.getEmail());
		if (obj.isPresent() && obj.get().getId() != objDTO.getId()) {
			throw new DataIntegrityViolationException("E-mail ja cadastrado no sistema!");
		}
	}


}
