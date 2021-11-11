package com.dave.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dave.helpdesk.domain.Chamado;

public interface ChamadoRepository extends JpaRepository<Chamado, Integer> {

}
