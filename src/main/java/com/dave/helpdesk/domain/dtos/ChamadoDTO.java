package com.dave.helpdesk.domain.dtos;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import com.dave.helpdesk.domain.Chamado;
import com.dave.helpdesk.domain.enums.Prioridade;
import com.dave.helpdesk.domain.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;

public class ChamadoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataAbertura = LocalDate.now();
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataFechamento;
	@NotNull(message = "O campo PRIORIDADE é requerido")
	private Integer prioridade;	
	private String prioridadeDescricao;	
	
	@NotNull(message = "O campo STATUS é requerido")
	private Integer status;	
    private String statusDescricao;
	
	@NotNull(message = "O campo TITULO é requerido")
	private String titulo;
	@NotNull(message = "O campo OBSERVAÇÕES é requerido")
	private String observacoes;
	//@NotNull(message = "O campo TECNICO é requerido")
	private Integer tecnico;
	//@NotNull(message = "O campo CLIENTE é requerido")
	private Integer cliente;
	private String nomeTecnico;
	private String nomeCliente;
	


	public ChamadoDTO() {
		super();
	}

	public ChamadoDTO(Chamado obj) {
		this.id = obj.getId();
		this.dataAbertura = obj.getDataAbertura();
		this.dataFechamento = obj.getDataFechamento();
		this.prioridade = obj.getPrioridade().getCodigo();
		this.prioridadeDescricao = obj.getPrioridade().getDescricao();
		this.status = obj.getStatus().getCodigo();
		//this.statusDescricao = obj.getStatus().getDescricao();
		this.titulo = obj.getTitulo();
		this.observacoes = obj.getObservacoes();
		this.tecnico = obj.getTecnico() != null ? obj.getTecnico().getId() : null ;
		this.cliente = obj.getCliente() != null ? obj.getCliente().getId() : null ;
		this.nomeCliente = obj.getCliente().getNome();
		this.nomeTecnico = obj.getTecnico().getNome();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDate getDataAbertura() {
		return dataAbertura;
	}

	public void setDataAbertura(LocalDate dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public LocalDate getDataFechamento() {
		return dataFechamento;
	}

	public void setDataFechamento(LocalDate dataFechamento) {
		this.dataFechamento = dataFechamento;
	}

	public void setPrioridade(Integer prioridade) {
		this.prioridade = prioridade;
	}

	public Integer getPrioridade() {
		return prioridade;
	}

	public String getPrioridadeDescricao() {
		return Prioridade.toEnum(prioridade).getDescricao();
	}	
	
	public void setPrioridadeDescricao(String prioridadeDescricao) {
		this.prioridade = Prioridade.toEnum(prioridadeDescricao).getCodigo();
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Integer getStatus() {
		return status;
	}

	public String getStatusDescricao() {
		return Status.toEnum(status).getDescricao();
	}
	
	public void setStatusDescricao(String statusDescricao) {
		this.status = Status.toEnum(statusDescricao).getCodigo();
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public Integer getTecnico() {
		return tecnico;
	}

	public void setTecnico(Integer tecnico) {
		this.tecnico = tecnico;
	}

	public Integer getCliente() {
		return cliente;
	}

	public void setCliente(Integer cliente) {
		this.cliente = cliente;
	}

	public String getNomeTecnico() {
		return nomeTecnico;
	}

	public void setNomeTecnico(String nomeTecnico) {
		this.nomeTecnico = nomeTecnico;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

}
