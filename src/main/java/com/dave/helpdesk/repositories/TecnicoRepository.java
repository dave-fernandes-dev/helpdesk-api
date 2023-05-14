package com.dave.helpdesk.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dave.helpdesk.domain.Tecnico;

public interface TecnicoRepository extends JpaRepository<Tecnico, Integer> {
	
	Optional<Tecnico> findByNome(String nome);
	
	Optional<Tecnico> findByCpf(String cpf);

}
