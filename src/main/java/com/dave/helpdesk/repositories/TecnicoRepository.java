package com.dave.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dave.helpdesk.domain.Tecnico;

public interface TecnicoRepository extends JpaRepository<Tecnico, Integer> {

}
