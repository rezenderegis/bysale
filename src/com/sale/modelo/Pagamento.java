package com.sale.modelo;

import java.util.Date;

public class Pagamento {

	private Double valor_pagamento;
	private Date data_pagamento_informada;
	private Long idpagamento;
	private int forma_pagamento_informada;
	private int situacao_pagamento;
	private String situacao_pagamento_informado;
	
	private Boolean pagamentos_pendentes;
	
	private Boolean pagamentos_efetuados;
	
	public int getSituacao_pagamento() {
		return situacao_pagamento;
	}

	public void setSituacao_pagamento(int situacao_pagamento) {
		this.situacao_pagamento = situacao_pagamento;
	}

	public Double getValor_pagamento() {
		return valor_pagamento;
	}

	public void setValor_pagamento(Double valor_pagamento) {
		this.valor_pagamento = valor_pagamento;
	}

	public Date getData_pagamento_informada() {
		return data_pagamento_informada;
	}

	public void setData_pagamento_informada(Date data_pagamento_informada) {
		this.data_pagamento_informada = data_pagamento_informada;
	}

	public Long getIdpagamento() {
		return idpagamento;
	}

	public void setIdpagamento(Long idpagamento) {
		this.idpagamento = idpagamento;
	}

	public int getForma_pagamento_informada() {
		return forma_pagamento_informada;
	}

	public void setForma_pagamento_informada(int forma_pagamento_informada) {
		this.forma_pagamento_informada = forma_pagamento_informada;
	}

	public String getSituacao_pagamento_informado() {
		return situacao_pagamento_informado;
	}

	public void setSituacao_pagamento_informado(
			String situacao_pagamento_informado) {
		this.situacao_pagamento_informado = situacao_pagamento_informado;
	}

	public Boolean getPagamentos_pendentes() {
		return pagamentos_pendentes;
	}

	public void setPagamentos_pendentes(Boolean pagamentos_pendentes) {
		this.pagamentos_pendentes = pagamentos_pendentes;
	}

	public Boolean getPagamentos_efetuados() {
		return pagamentos_efetuados;
	}

	public void setPagamentos_efetuados(Boolean pagamentos_efetuados) {
		this.pagamentos_efetuados = pagamentos_efetuados;
	}

}
