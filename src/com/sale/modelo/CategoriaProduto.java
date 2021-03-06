package com.sale.modelo;

import java.io.Serializable;

public class CategoriaProduto implements Serializable {

	private String idCategoria;

	private String nomeCategoria;

	public CategoriaProduto(String idCategoria, String string) {
		// TODO Auto-generated constructor stub

		this.idCategoria = idCategoria;
		this.nomeCategoria = string;

	}



	public String getIdCategoria() {
		return idCategoria;
	}



	public void setIdCategoria(String idCategoria) {
		this.idCategoria = idCategoria;
	}



	public String getNomeCategoria() {
		return nomeCategoria;
	}

	public void setNomeCategoria(String nomeCategoria) {
		this.nomeCategoria = nomeCategoria;
	}

	@Override
	public String toString() {
		return nomeCategoria;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idCategoria == null) ? 0 : idCategoria.hashCode());
		return result;
	}

	/*
	 * Data: 02-11-2014 Fui na casa do Andr� e ele implementou para conseguirmos
	 * identificar o objetivo a ser selecionado no combo de categoria.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CategoriaProduto other = (CategoriaProduto) obj;
		if (idCategoria == null) {
			if (other.idCategoria != null)
				return false;
		} else if (!idCategoria.equals(other.idCategoria))
			return false;
		return true;
	}

}
