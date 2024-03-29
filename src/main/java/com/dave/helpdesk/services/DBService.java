package com.dave.helpdesk.services;

import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.dave.helpdesk.domain.Chamado;
import com.dave.helpdesk.domain.Cliente;
import com.dave.helpdesk.domain.Tecnico;
import com.dave.helpdesk.domain.enums.Perfil;
import com.dave.helpdesk.domain.enums.Prioridade;
import com.dave.helpdesk.domain.enums.Status;
import com.dave.helpdesk.repositories.ChamadoRepository;
import com.dave.helpdesk.repositories.PessoaRepository;

@Service
public class DBService {

	@Autowired
	private ChamadoRepository chamadoRepository;
	@Autowired
	private PessoaRepository pessoaRepository;
	@Autowired
	private BCryptPasswordEncoder encoder;

	public void instanciaDB() {

		Tecnico tec0 = new Tecnico(null, "Dave Fernandes", "79138309050", "dave.fernandes.dev@gmail.com", encoder.encode("123456"));
		tec0.addPerfil(Perfil.ADMIN);

		Tecnico tec01 = new Tecnico(null, "Admin", "22781508047", "admin@mail.com", encoder.encode("123"));
		tec01.addPerfil(Perfil.ADMIN);		
		
		Tecnico tec1 = new Tecnico(null, "Valdir Cezar", "55048215095", "valdir@mail.com", encoder.encode("123"));
		tec1.addPerfil(Perfil.ADMIN);
		Tecnico tec2 = new Tecnico(null, "Richard Stallman", "90334707056", "stallman@mail.com", encoder.encode("123"));
		Tecnico tec3 = new Tecnico(null, "Claude Elwood Shannon", "27106847054", "shannon@mail.com", encoder.encode("123"));
		Tecnico tec4 = new Tecnico(null, "Tim Berners-Lee", "16272012039", "lee@mail.com", encoder.encode("123"));
		Tecnico tec5 = new Tecnico(null, "Linus Torvalds", "77855617027", "linus@mail.com", encoder.encode("123"));

		Cliente cli1 = new Cliente(null, "Albert Einstein", "11166189074", "einstein@mail.com", encoder.encode("123"));
		Cliente cli2 = new Cliente(null, "Marie Curie", "32242914006", "curie@mail.com", encoder.encode("123"));
		Cliente cli3 = new Cliente(null, "Charles Darwin", "79204383062", "darwin@mail.com", encoder.encode("123"));
		Cliente cli4 = new Cliente(null, "Stephen Hawking", "17740968030", "hawking@mail.com", encoder.encode("123"));
		Cliente cli5 = new Cliente(null, "Max Planck", "08139930083", "planck@mail.com", encoder.encode("123"));
 
		Cliente cli6 = new Cliente(null, "Cliente 6", "33506478044", "cli6@mail.com", encoder.encode("123"));
		Cliente cli7 = new Cliente(null, "Cliente 7", "81818966026", "cli7@mail.com", encoder.encode("123"));
		Cliente cli8 = new Cliente(null, "Cliente 8", "81676722041", "cli8@mail.com", encoder.encode("123"));
		Cliente cli9 = new Cliente(null, "Cliente 9", "48674650031", "cli9@mail.com", encoder.encode("123"));
		Cliente cli10 = new Cliente(null, "Cliente 10", "05967418040", "cli10@mail.com", encoder.encode("123"));		
		
		Chamado c1 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Consertar Notebook", "notebook não liga", tec1, cli1);
		Chamado c2 = new Chamado(null, Prioridade.ALTA, Status.ABERTO, "Consertar Monitor", "tela azul", tec1, cli2);
		Chamado c3 = new Chamado(null, Prioridade.BAIXA, Status.ENCERRADO, "Monitor com Defeito", "Teste chamado 3", tec2, cli3, LocalDate.now());
		Chamado c4 = new Chamado(null, Prioridade.ALTA, Status.ABERTO, "CPU não liga", "não sei o motivo", tec3, cli3);
		Chamado c5 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "mal contato pen drive", "Teste chamado 5", tec2, cli1);
		Chamado c6 = new Chamado(null, Prioridade.BAIXA, Status.ENCERRADO, "celular trincado", "Teste chamado 6", tec1, cli5, LocalDate.now());
		Chamado c7= new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 1", "Teste chamado 1", tec1, cli1);
		Chamado c8= new Chamado(null, Prioridade.ALTA, Status.ABERTO, "Chamado 2", "Teste chamado 2", tec1, cli2);
		Chamado c9= new Chamado(null, Prioridade.BAIXA, Status.ENCERRADO, "Chamado 3", "Teste chamado 3", tec2, cli3, LocalDate.now());
		Chamado c10= new Chamado(null, Prioridade.ALTA, Status.ABERTO, "Chamado 4", "Teste chamado 4", tec3, cli3);
		Chamado c11= new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 5", "Teste chamado 5", tec2, cli1);
		Chamado c12= new Chamado(null, Prioridade.BAIXA, Status.ENCERRADO, "Chamado 7", "Teste chamado 6", tec1, cli5, LocalDate.now());


		pessoaRepository.saveAll(Arrays.asList(tec0, tec01, tec1, tec2, tec3, tec4, tec5, cli1, cli2, cli3, cli4, cli5, cli6, cli7, cli8, cli9, cli10));
		chamadoRepository.saveAll(Arrays.asList(c1, c2, c3, c4, c5, c6,c7, c8, c9, c10, c11, c12));
	}
}
