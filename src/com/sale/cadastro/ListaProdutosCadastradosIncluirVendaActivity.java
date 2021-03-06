package com.sale.cadastro;

import java.util.List;

import com.sale.adapter.ListaProdutosCadastradosAdapter;
import com.sale.adapter.ListaProdutosCadastradosIncluirVendaAdapter;
import com.sale.cadastro.R;
import com.sale.dao.SaleDAO;
import com.sale.modelo.Produto;
import com.sale.modelo.ProdutoDTO;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListaProdutosCadastradosIncluirVendaActivity extends Activity {

	public Produto produto;
	ListView lista;
	int layout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.listagem_alunos);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		
		
		
		// getActionBar().setIcon(R.drawable.ic_back);
		/*
		 * SaleDAO dao = new SaleDAO(this);
		 * 
		 * List<Produto> produtos = dao.getListaProduto();
		 * 
		 * dao.close();
		 */

		layout = android.R.layout.simple_list_item_1;

		// ArrayAdapter<Produto> adapter = new ArrayAdapter<Produto>(this,
		// layout,produtos);
		View header = (View) this.getLayoutInflater().inflate(R.layout.listagem_produtos_cadastrados_cabecalho, null);
		getActionBar().setTitle("Produtos Cadastrados");
		lista = (ListView) findViewById(R.id.lista);
		// lista.setAdapter(adapter);
		registerForContextMenu(lista);
		lista.addHeaderView(header);

		lista.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				produto = (Produto) adapter.getItemAtPosition(position);
				lista.showContextMenuForChild(view);
			}
		});

	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		// super.onCreateContextMenu(menu, v, menuInfo);

		MenuItem editarProduto = menu.add("Editar Produto");

		editarProduto.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				// TODO Auto-generated method stub

				Intent editarProdutoCadastrado = new Intent(
						ListaProdutosCadastradosIncluirVendaActivity.this,
						FormularioProdutoActivity.class);
				editarProdutoCadastrado.putExtra("produtoParaEditar", produto);
				//editarProdutoCadastrado.putExtra("editarProdutoCadastrado", produto)
			//	editarProduto.putExtra("produtoParaEditar", produto);
				startActivity(editarProdutoCadastrado);

				return false;
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub

		MenuInflater menuCategoria = getMenuInflater();
		menuCategoria.inflate(R.menu.menu_novo, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		int itemClicado = item.getItemId();

		if (itemClicado == android.R.id.home) {
			finish();
		}

		switch (itemClicado) {

		case R.id.novo:
			Intent cadastrarProduto = new Intent(this,
					FormularioProdutoActivity.class);
			startActivity(cadastrarProduto);

			break;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		carregarLista();
	}

	public void carregarLista() {

		SaleDAO dao = new SaleDAO(this);
		List<Produto> produto = dao.getListaProduto();

		dao.close();

		ListaProdutosCadastradosIncluirVendaAdapter adapter = new ListaProdutosCadastradosIncluirVendaAdapter(produto, this, this);
		lista.setAdapter(adapter);
	}

}
