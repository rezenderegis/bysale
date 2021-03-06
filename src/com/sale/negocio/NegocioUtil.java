package com.sale.negocio;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import android.content.Context;

import com.sale.dao.SaleDAO;
import com.sale.modelo.CategoriaProduto;
import com.sale.modelo.Pessoa;
import com.sale.modelo.ProdutoDTO;
import com.sale.modelo.VendaCliente;

public class NegocioUtil {

	
	
	public Double valorDescontoVenda (Context context, Long idVendaClienteDaListaVendas) {
		
		Double totalDesconto = 0.0;
		
		SaleDAO dao = new SaleDAO(context);
		List<VendaCliente> dadosVendaCliente = dao.getListaVendaCliente(
				idVendaClienteDaListaVendas, null);
		
		Integer situacao_venda = 0;
		for (VendaCliente vendaCliente : dadosVendaCliente) {
			if (vendaCliente.getDescontoVenda() != null) {
				totalDesconto = vendaCliente.getDescontoVenda();
			}
			situacao_venda = vendaCliente.getSituacaovenda();
		}

		
		return totalDesconto;
	}
	
	
	public Double calcularValorRestanteNaFormaPagamento (Double precoTotalVenda, Double totalDesconto, Double totalPagamento) {
	
		 Double calculoValorRestante;

		// totalDesconto;
		Double valorDesconto = precoTotalVenda * totalDesconto / 100;
		calculoValorRestante = precoTotalVenda - totalPagamento - valorDesconto;
		
		return calculoValorRestante;
		
	}


	public void salvarDadosPadrao(Context context) {
		// TODO Auto-generated method stub
		/*SaleDAO dao = new SaleDAO(context);
		UUID uuidCategoria = UUID.randomUUID();
		CategoriaProduto categoriaProduto = new CategoriaProduto(uuidCategoria.toString(),
				"Categoria-Demonstração");
		dao.salvaCategoria(categoriaProduto);

		ProdutoDTO produto = new ProdutoDTO();
		produto.setNomeProduto("Cx Cerveja - Demonstração");
		produto.setPrecoProduto(10.0);

		dao.salvaProduto(produto, uuidCategoria.toString());
		*/
		SaleDAO dao = new SaleDAO(context);
		dao.salvaProdutoECategoriaCadastroInicial();
		
		Pessoa pessoa = new Pessoa();
		UUID uuid = UUID.randomUUID();
		pessoa.setId(uuid.toString());
		pessoa.setNome("Supermercado Goiás - Demonstração");
		
		dao.salva(pessoa);
		dao.close();
		
	}

}
