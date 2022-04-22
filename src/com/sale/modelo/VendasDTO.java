package com.sale.modelo;

import java.util.Date;

public class VendasDTO {

	private Long id;

	private String nomeProduto;

	private Double preco;

	private Double total;

	private Double totalProduto;

	private Long idvenda;

	private Long idvendacliente;

	private Date dtvenda;

	private Long situacaoVenda;

	private String idCliente;
	
	//Coloquei esses atributos para atender ao relat�rio de vendas pendentes. O ideal
	// � que tivesse feito um DTO espef�co
	
	private Double totalPagamentos;
	
	private Date dtPagamentoInformado;
	
	private String situacao_cadastro;
	
	
	
	public String getSituacao_cadastro() {
		return situacao_cadastro;
	}

	public void setSituacao_cadastro(String situacao_cadastro) {
		this.situacao_cadastro = situacao_cadastro;
	}



	public String getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	private String nome;

	private String site;

	private String endereco;

	private String telefone;

	// private Double nota;

	private String foto;

	private String email;

	public Long getSituacaoVenda() {
		return situacaoVenda;
	}

	public void setSituacaoVenda(Long situacaoVenda) {
		this.situacaoVenda = situacaoVenda;
	}

	public Date getDtvenda() {
		return dtvenda;
	}

	public void setDtvenda(Date dtvenda) {
		this.dtvenda = dtvenda;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return nomeProduto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Double getTotalProduto() {
		return totalProduto;
	}

	public void setTotalProduto(Double totalProduto) {
		this.totalProduto = totalProduto;
	}

	public Long getIdvenda() {
		return idvenda;
	}

	public void setIdvenda(Long idvenda) {
		this.idvenda = idvenda;
	}

	public Long getIdvendacliente() {
		return idvendacliente;
	}

	public void setIdvendacliente(Long idvendacliente) {
		this.idvendacliente = idvendacliente;
	}

	public Double getTotalPagamentos() {
		return totalPagamentos;
	}

	public void setTotalPagamentos(Double totalPagamentos) {
		this.totalPagamentos = totalPagamentos;
	}

	public Date getDtPagamentoInformado() {
		return dtPagamentoInformado;
	}

	public void setDtPagamentoInformado(Date dtPagamentoInformado) {
		this.dtPagamentoInformado = dtPagamentoInformado;
	}

}
