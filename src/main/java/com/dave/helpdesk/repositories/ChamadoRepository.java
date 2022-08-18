package com.dave.helpdesk.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dave.helpdesk.domain.Chamado;

public interface ChamadoRepository extends JpaRepository<Chamado, Integer> {
	
	
	@Query(value = "SELECT * FROM CHAMADO C WHERE ( C.TITULO ILIKE CONCAT('%', :titulo,'%') OR C.OBSERVACOES ILIKE CONCAT('%', :titulo,'%') ) AND C.STATUS IN (:status) ORDER BY C.ID DESC", nativeQuery = true)
	List<Chamado> findByFilter(String titulo, int[] status);

}
