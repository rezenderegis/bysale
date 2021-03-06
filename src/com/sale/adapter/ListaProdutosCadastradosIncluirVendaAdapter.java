package com.sale.adapter;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import com.sale.cadastro.R;
import com.sale.cadastro.ListaPagamentos;
import com.sale.cadastro.ListaPessoas;
import com.sale.cadastro.ListaVendas;
import com.sale.cadastro.ProdutoActivity;
import com.sale.dao.SaleDAO;
import com.sale.modelo.Pessoa;
import com.sale.modelo.Produto;
import com.sale.modelo.ProdutoDTO;
import com.sale.modelo.VendasDTO;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Toast;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class ListaProdutosCadastradosIncluirVendaAdapter extends ArrayAdapter {
	private List<Produto> produto;
	private Activity activity;
	Double precoTotalVenda;
	Integer totalproduto;
	Context context;
	Double total_vendas_somadas;
	Produto produtos;
    static final int LAYOUT = R.layout.lista_produtos_com_preco_adapter;

	public ListaProdutosCadastradosIncluirVendaAdapter(List<Produto> produto, Activity activity,
			Context context) {
        super(context, LAYOUT, produto);

		this.produto = produto;
		this.activity = activity;
		this.context = context;
		// TODO Auto-generated constructor stub
	}

	

	@Override
	public View getView(int position, View converView, ViewGroup partent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = activity.getLayoutInflater();
		View linha = null;

		DecimalFormat precoFormatado = new DecimalFormat("####,###,###.00");

			produtos = produto.get(position);
			linha = inflater.inflate(R.layout.lista_produtos_com_preco_adapter, null);
			TextView nome_produto = (TextView) linha
					.findViewById(R.id.nomeproduto_adapter);
			nome_produto.setText(produtos.getNomeProduto());


		
			String precoProdutoFormatado = "0.0";

			if (produtos.getPreco()!= 0.0) {
				precoProdutoFormatado = precoFormatado
						.format(produtos.getPreco());
			}

			TextView precoProduto = (TextView) linha
					.findViewById(R.id.preco_produto_adapter);
			// String precoVenda = produtos.getPreco().toString();
			precoProduto.setText("R$ "+precoProdutoFormatado);

		
		// linha = inflater.inflate(R.layout.botao_fechar_venda, null);
		return linha;

	}

}
