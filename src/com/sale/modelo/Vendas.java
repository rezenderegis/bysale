package com.sale.modelo;

import java.util.Date;

public class Vendas {

	private Long id;

	private String idProduto;

	private String idCliente;

	private Long idVendaCliente;

	private Long controle;

	private Long totalProduto;

	private Date dtvenda;

	private Long situacao;

	
	public String getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return idProduto.toString();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(String idProduto) {
		this.idProduto = idProduto;
	}

	public Long getIdVendaCliente() {
		return idVendaCliente;
	}

	public void setIdVendaCliente(Long idVendaCliente) {
		this.idVendaCliente = idVendaCliente;
	}

	public Long getControle() {
		return controle;
	}

	public void setControle(Long controle) {
		this.controle = controle;
	}

	public Long getTotalProduto() {
		return totalProduto;
	}

	public void setTotalProduto(Long totalProduto) {
		this.totalProduto = totalProduto;
	}

	public Date getDtvenda() {
		return dtvenda;
	}

	public void setDtvenda(Date dtvenda) {
		this.dtvenda = dtvenda;
	}

	public Long getSituacao() {
		return situacao;
	}

	public void setSituacao(Long situacao) {
		this.situacao = situacao;
	}

}
