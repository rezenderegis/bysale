package com.sale.adapter;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.sale.cadastro.R;
import com.sale.cadastro.ListaPagamentos;
import com.sale.cadastro.ListaPessoas;
import com.sale.cadastro.ListaVendas;
import com.sale.cadastro.ProdutoActivity;
import com.sale.dao.SaleDAO;
import com.sale.modelo.Pessoa;
import com.sale.modelo.VendasDTO;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Toast;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class RelatorioPagamentoAdapter extends BaseAdapter {
	private List<VendasDTO> produto;
	private Activity activity;
	Double precoTotalVenda;
	Integer totalproduto;
	Context context;
	Double total_vendas_somadas;
	VendasDTO produtos;

	public RelatorioPagamentoAdapter(List<VendasDTO> vendasDTO, Activity activity,
			Context context, Double valorVendasSomadas) {

		this.produto = vendasDTO;
		this.activity = activity;
		this.context = context;
		this.total_vendas_somadas = valorVendasSomadas;
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return produto.size() + 1;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return produto.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return produto.get(position).getIdvendacliente();
	}

	@Override
	public View getView(int position, View converView, ViewGroup partent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = activity.getLayoutInflater();
		View linha = null;

		SaleDAO dao = new SaleDAO(context);
		VendasDTO dadosVenda = new VendasDTO();
		DecimalFormat precoFormatado = new DecimalFormat("####,###,###.00");

		if (position < (getCount() - 1)) {
			produtos = produto.get(position);
			linha = inflater.inflate(R.layout.relatorio_pagamentos_pendentes, null);
			TextView nome_cliente = (TextView) linha
					.findViewById(R.id.nome_cliente);
			nome_cliente.setText(produtos.getIdvendacliente()+"#"+produtos.getNome());

			TextView dt_pagamento_informada = (TextView) linha
					.findViewById(R.id.dt_pagamento_informada);

			SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");

			//String data = formatador.format(produtos.getDtvenda());

///data aqui
			
			Date data = (Date) produtos.getDtPagamentoInformado();
			String dataNova = null;
			
////
		
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy");
		
			dataNova = formatter.format(data);
			
			/// data aqui
			
			
			
			dt_pagamento_informada.setText(dataNova);

			String precoVendaFormatado = "0.0";

			if (produtos.getTotal() != 0.0) {
				precoVendaFormatado = precoFormatado
						.format(produtos.getTotal());
			}

			TextView preco_relatoriovenda = (TextView) linha
					.findViewById(R.id.preco_relatoriovenda);
			// String precoVenda = produtos.getPreco().toString();
			preco_relatoriovenda.setText("R$ "+precoVendaFormatado);

		} else {
			//

			String precoTotalFormatado = "0,00";

			if (total_vendas_somadas != null) {
				precoTotalFormatado = precoFormatado
						.format(total_vendas_somadas);
			}

			linha = inflater.inflate(R.layout.listagem_total_relatorio, null);
			TextView total_relatoriovenda = (TextView) linha
					.findViewById(R.id.total_relatoriovenda);
			total_relatoriovenda.setText("R$ " + precoTotalFormatado);

		}
		// linha = inflater.inflate(R.layout.botao_fechar_venda, null);
		return linha;

	}

}
