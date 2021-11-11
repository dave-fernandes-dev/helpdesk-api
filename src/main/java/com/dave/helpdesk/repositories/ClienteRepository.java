package com.dave.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dave.helpdesk.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

}
