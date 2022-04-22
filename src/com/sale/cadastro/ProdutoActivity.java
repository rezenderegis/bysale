package com.sale.cadastro;

import java.util.List;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;

import com.sale.dao.SaleDAO;
import com.sale.modelo.CategoriaProduto;
import com.sale.modelo.Pessoa;
import com.sale.modelo.VendasDTO;

public class ProdutoActivity extends Activity {

	public String clienveVenda;
	public Long idVendaCliente;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle("Inclus�o de Produto na Venda");

		/*
		 * StackedBackgroundDrawable seta um valor para uma barra abaixo da
		 * ActionBar principal e recebe como par�metro o int.
		 */
		actionBar.setStackedBackgroundDrawable(new ColorDrawable(Color
				.parseColor("#FFFAFA")));

		Intent intent = getIntent();
		clienveVenda = (String) intent
				.getSerializableExtra("clienteSelecionadoFromListaPessoasAbrirVenda");
		idVendaCliente = (Long) intent.getSerializableExtra("idVendaCliente");
		
		// ProdutoFragment af = (ProdutoFragment)
		String tabProduto;
		SaleDAO dao = new SaleDAO(this);
		List<CategoriaProduto> categoriaProduto = dao
				.getListaCategoriaProduto();
		dao.close();

		/*
		 * Coloquei esse inflate para a custom view e cada entrada na adi��o das
		 * tabs abaixo, mas sobrep�es o texto e os �cones LinearLayout view =
		 * (LinearLayout) getLayoutInflater().inflate(R.layout.tab, null);
		 */

		for (CategoriaProduto categoriaProduto2 : categoriaProduto) {

			tabProduto = categoriaProduto2.getIdCategoria();

			String nomeTab = null;
			// if (tabProduto == 1) {nomeTab = "Bebida";} else if (tabProduto ==
			// 2) {nomeTab = "Comida";} else if (tabProduto == 3) {nomeTab =
			// "Petiscos";}
			// Criar um Bundle

			if (tabProduto.equals("1")) {
				Tab tab = actionBar.newTab()
						.setText(categoriaProduto2.getNomeCategoria())
						// Nome da Tab
						.setTabListener(
								new CustomTabListener<ProdutoFragment_padrao>(this,
										"android", ProdutoFragment_padrao.class,
										tabProduto, idVendaCliente, clienveVenda))
				// .setCustomView(R.layout.tab)
				// .setIcon(R.drawable.bebida_icon)

				;

				actionBar.addTab(tab);
			} else if (tabProduto.equals("2")) {
				Tab tab = actionBar.newTab()
				// .setCustomView(view)
						.setText(categoriaProduto2.getNomeCategoria())
						// Nome da Tab
						.setTabListener(
								new CustomTabListener<ProdutoFragment_padrao>(this,
										"apple", ProdutoFragment_padrao.class,
										tabProduto, idVendaCliente, clienveVenda))
				// .setIcon(R.drawable.comida_icon);
				;
				actionBar.addTab(tab);
			} else {

				Tab tab = actionBar.newTab()
				// .setCustomView(view)
						.setText(categoriaProduto2.getNomeCategoria())
						// Nome da Tab
						.setTabListener(
								new CustomTabListener<ProdutoFragment_padrao>(this,
										"apple", ProdutoFragment_padrao.class,
										tabProduto, idVendaCliente, clienveVenda))
				// .setIcon(R.drawable.comida_icon)
				;

				actionBar.addTab(tab);

			}

		}

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		int itemSelecionado = item.getItemId();

		if (itemSelecionado == android.R.id.home) {
			finish();
		}
		return super.onOptionsItemSelected(item);
	}

}
