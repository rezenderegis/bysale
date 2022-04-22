package com.sale.cadastro.util;

import java.util.List;

import org.json.JSONStringer;

import android.app.Activity;

import com.sale.dao.SaleDAO;
import com.sale.login.Usuario;
import com.sale.modelo.CategoriaProduto;
import com.sale.modelo.Pessoa;
import com.sale.modelo.Produto;
import com.sale.modelo.VendaCliente;
import com.sale.modelo.Vendas;

public class PessoaConveter {

	public String toJson(List<Pessoa> pessoas, Activity context) {
		// TODO Auto-generated method stub

		JSONStringer js = new JSONStringer(); // ESSA CLASSE AJUDA A CRIAR
												// STRINGS DINAMICAMENTE
		// A JSON STRINGER AJUDA ORGANIZAR O CNONJUNTO DE CHAVES E VALOR

		// {chave : valor} FORMATO DE JSON ARRAY DE ALUNOS { "list": [{ "aluno":
		// [{"nome": "mara","nota": 9},{"nome": "maria", "nota": 7}] }]}
		// O C�DIGO ABAIXO GERAR� CADA ALUNO NO FORMATO JSON
		SaleDAO dao = new SaleDAO(context);

		try {

			// js.object().key("list").array();
			js.object().key("pessoa").array(); // colocar tudo em um arrar

			for (Pessoa aluno : pessoas) {
				js.object();
				js.key("id").value(aluno.getId());
				js.key("nome").value(aluno.getNome());
				js.key("telefone").value(aluno.getTelefone());
				js.key("site").value(aluno.getSite());
				js.key("endereco").value(aluno.getEndereco());
				js.key("email").value(aluno.getEmail());
				js.key("cpf_cnpj").value(aluno.getCpf_cnpj());
				js.key("observacao").value(aluno.getObservacao());

				// USUARIO
				List<Usuario> usuario = dao.getDadosUsuario();
				for (Usuario usuario2 : usuario) {
					js.key("idempresa").value(usuario2.getIdempresa());
					js.key("idusuario").value(usuario2.getIdusuario());
				}

				js.endObject();
			}

			js.endArray().endObject();
			// js.endArray().endObject();

			return js.toString();
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException();
		}

	}

	public String toJsonProduto(List<Produto> produtos, Activity context) {
		// TODO Auto-generated method stub

		JSONStringer js = new JSONStringer();
		SaleDAO dao = new SaleDAO(context);

		try {

			js.object().key("produto").array();

			for (Produto prod : produtos) {

				js.object();
				js.key("id").value(prod.getId());
				js.key("nomeproduto").value(prod.getNomeProduto());
				js.key("preco").value(prod.getPreco());
				js.key("categoria").value(prod.getCategoria());

				List<Usuario> usuario = dao.getDadosUsuario();
				for (Usuario usuario2 : usuario) {
					js.key("idempresa").value(usuario2.getIdempresa());
					js.key("idusuario").value(usuario2.getIdusuario());
				}

				js.endObject();

			}
			js.endArray().endObject();
			return js.toString();
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException();
		}

	}

	public String toJsonVendaCliente(List<VendaCliente> vendaClientes,
			Activity context) {
		// TODO Auto-generated method stub

		JSONStringer js = new JSONStringer();
		SaleDAO dao = new SaleDAO(context);

		try {

			// js.object().key("list").array();

			js.object().key("vendacliente").array();

			for (VendaCliente venda : vendaClientes) {

				js.object();

				js.key("idvendacliente").value(venda.getIdvendacliente());
				js.key("idcliente").value(venda.getIdcliente());
				js.key("situacaovenda").value(venda.getSituacaovenda());
				js.key("dtvenda").value(venda.getDtvenda());
				js.key("totalvenda").value(venda.getTotalVenda());

				List<Usuario> usuario = dao.getDadosUsuario();
				for (Usuario usuario2 : usuario) {
					js.key("idempresa").value(usuario2.getIdempresa());
					js.key("idusuario").value(usuario2.getIdusuario());
				}

				js.endObject();

			}
			js.endArray().endObject();
			return js.toString();

		} catch (Exception e) {

			throw new RuntimeException();
		}
	}

	public String toJsonVenda(List<Vendas> venda, Activity context) {
		// TODO Auto-generated method stub

		JSONStringer js = new JSONStringer();

		SaleDAO dao = new SaleDAO(context);

		try {

			js.object().key("vendas").array();

			for (Vendas vendas : venda) {
				js.object();

				js.key("id").value(vendas.getId());
				js.key("idcliente").value(vendas.getIdCliente());
				js.key("idproduto").value(vendas.getIdProduto());
				js.key("controle").value(vendas.getControle());
				js.key("idvendacliente").value(vendas.getIdVendaCliente());
				js.key("totalproduto").value(vendas.getTotalProduto());
				js.key("dtvenda").value(vendas.getDtvenda());
				js.key("situacao").value(vendas.getSituacao());
				List<Usuario> usuario = dao.getDadosUsuario();
				for (Usuario usuario2 : usuario) {
					js.key("idempresa").value(usuario2.getIdempresa());
					js.key("idusuario").value(usuario2.getIdusuario());
				}

				List<VendaCliente> vendaCliente = dao.getListaVendaCliente(
						vendas.getIdVendaCliente(), null);
				for (VendaCliente vendaClienteData : vendaCliente) {
					js.key("dtvendaCliente").value(
							vendaClienteData.getDtvenda());

				}

				// CONTROLE
				// controle

				// INCLUIR DATA
				js.endObject();
			}

			js.endArray().endObject();
			return js.toString();
		} catch (Exception e) {
			throw new RuntimeException();
		}

	}

	public String toJsonUsuario(List<Usuario> usuarioLogin, String tipo) {
		// TODO Auto-generated method stub

		JSONStringer js = new JSONStringer();

		try {

			js.object().key("usuario").array();

			for (Usuario usuarios : usuarioLogin) {
				js.object();
				js.key("idusuario").value(usuarios.getIdusuario());

				// Inclui essa condi��o para ser utilizada no cadastro de
				// usu�rio
				if (usuarios.getNome() != null) {
					js.key("nome_usuario").value(usuarios.getNome());
				}

				js.key("idempresa").value(usuarios.getIdempresa());
				js.key("tiposincronizacao").value(tipo);
				js.key("email").value(usuarios.getEmail());
				js.key("password").value(usuarios.getPassword());
				js.endObject();
			}

			js.endArray().endObject();
			return js.toString();
		} catch (Exception e) {
			throw new RuntimeException();
		}

	}

	public String toJsonCategoriaProduto(
			List<CategoriaProduto> categoriaProduto, Activity context) {
		// TODO Auto-generated method stub

		JSONStringer js = new JSONStringer(); // ESSA CLASSE AJUDA A CRIAR
												// STRINGS DINAMICAMENTE
		// A JSON STRINGER AJUDA ORGANIZAR O CNONJUNTO DE CHAVES E VALOR

		// {chave : valor} FORMATO DE JSON ARRAY DE ALUNOS { "list": [{ "aluno":
		// [{"nome": "mara","nota": 9},{"nome": "maria", "nota": 7}] }]}
		// O C�DIGO ABAIXO GERAR� CADA ALUNO NO FORMATO JSON
		SaleDAO dao = new SaleDAO(context);

		try {

			// js.object().key("list").array();
			js.object().key("categoria").array(); // colocar tudo em um arrar

			for (CategoriaProduto categoria : categoriaProduto) {
				js.object();
				js.key("id").value(categoria.getIdCategoria());
				js.key("nome").value(categoria.getNomeCategoria());

				// USUARIO

				List<Usuario> usuario = dao.getDadosUsuario();
				for (Usuario usuario2 : usuario) {
					js.key("idempresa").value(usuario2.getIdempresa());
					js.key("idusuario").value(usuario2.getIdusuario());
				}

				js.endObject();
			}

			js.endArray().endObject();
			// js.endArray().endObject();

			return js.toString();
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException();
		}

	}

}
