package com.dave.helpdesk.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dave.helpdesk.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

	Optional<Cliente> findByCpf(String cpf);
	Optional<Cliente> findByNome(String nome);
	
	//List<Cliente> findClientesByNome(String nome);
	@Query(value = "SELECT * FROM PESSOA P WHERE P.DTYPE='Cliente' AND ( P.NOME ILIKE CONCAT('%', :search,'%') OR P.EMAIL ILIKE CONCAT('%', :search,'%') OR P.CPF ILIKE CONCAT('%', :search,'%') )  ORDER BY P.ID DESC", nativeQuery = true)
	//@Query(value = "SELECT * FROM CLIENTE C WHERE ( C.NOME ILIKE CONCAT('%', :search,'%') OR C.EMAIL ILIKE CONCAT('%', :search,'%') OR C.CPF ILIKE CONCAT('%', :search,'%') )  ORDER BY C.ID DESC", nativeQuery = true, )
	List<Cliente> findByFilter(String search);
}
