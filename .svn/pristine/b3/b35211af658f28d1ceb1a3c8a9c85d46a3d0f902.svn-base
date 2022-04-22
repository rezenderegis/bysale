package com.sale.cadastro;

import java.util.List;

import com.sale.cadastro.R;
import com.sale.dao.SaleDAO;
import com.sale.modelo.CategoriaProduto;
import com.sale.modelo.CategoriaProdutoTeste;
import com.sale.modelo.Pessoa;
import com.sale.modelo.Produto;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListaCategoriasProdutos extends Activity {
	int layout;
	ListView lista;
	// private ListView lista;
	public CategoriaProduto categoriaProdutoEditar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle("Categorias Cadastradas");
		setContentView(R.layout.listagem_categoria_produto);
		
		//Adicionando cabeçalho
		View header = (View) this.getLayoutInflater().inflate(R.layout.listagem_categoria_produto_cabecalho, null);
		
		SaleDAO dao = new SaleDAO(this);
		List<CategoriaProduto> categoriaProduto = dao
				.getListaProdutoCategoria();

		dao.close();
		layout = android.R.layout.simple_list_item_1;
		// ArrayAdapter<Pessoa> adapter = new ArrayAdapter<Pessoa>(this,
		// layout,pessoas);
		// ArrayAdapter<CategoriaProduto> adapter = new
		// ArrayAdapter<CategoriaProduto>(this, layout,categoriaProduto);

		lista = (ListView) findViewById(R.id.lista_categoria_produto);

		// lista.setAdapter(adapter);
		registerForContextMenu(lista);
		lista.addHeaderView(header);

		lista.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				categoriaProdutoEditar = (CategoriaProduto) adapter
						.getItemAtPosition(position);
				lista.showContextMenuForChild(view);
			}
		});
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub

		MenuItem editarCategoria = menu.add("Editar Categoria");

		editarCategoria
				.setOnMenuItemClickListener(new OnMenuItemClickListener() {

					@Override
					public boolean onMenuItemClick(MenuItem item) {
						// TODO Auto-generated method stub

						Intent editarProduto = new Intent(
								ListaCategoriasProdutos.this,
								FormularioCategoriaActivity.class);
						editarProduto.putExtra("categoriaParaEditar",
								categoriaProdutoEditar);

						startActivity(editarProduto);

						return false;
					}
				});

		super.onCreateContextMenu(menu, v, menuInfo);
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
			Intent cadastrar_categoria = new Intent(this,
					FormularioCategoriaActivity.class);
			startActivity(cadastrar_categoria);
			break;

		}

		return super.onOptionsItemSelected(item);
	}

	public void carregarLista() {

		SaleDAO dao = new SaleDAO(this);
		List<CategoriaProduto> categoriaProduto = dao
				.getListaProdutoCategoria();

		dao.close();
		ArrayAdapter<CategoriaProduto> adapter = new ArrayAdapter<CategoriaProduto>(
				this, layout, categoriaProduto);

		lista.setAdapter(adapter);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// Log.i("CICLO DE VIDA", "onResume ListarCategoria");

		carregarLista();
	}

}
