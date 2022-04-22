package com.sale.cadastro;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.sale.cadastro.R;
import com.sale.adapter.ListaVendasAdapter;
import com.sale.dao.SaleDAO;
import com.sale.modelo.Pessoa;
import com.sale.modelo.VendaCliente;
import com.sale.modelo.Vendas;
import com.sale.modelo.VendasDTO;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ListaVendas extends Activity {

   
	private ListView lista;
	public Vendas venda;
	public Long vendaSelecionadaFromListaVendasAbertas;
	public VendasDTO vendaCliente;
	private ListView produtoLista;
	private List<VendasDTO> vendasDTO;
	Double precoTotalVenda;
	private String clienteSelecionadoFromListaPessoasAbrirVenda;
	public Long situacao_venda;
	String origem_tela_anterior;
	String origem;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		

		
		setContentView(R.layout.listagem_alunos);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle("Produtos da Venda");
		lista = (ListView) findViewById(R.id.lista);
		registerForContextMenu(lista);
	   
		
	
		/*
		ActivityManager mngr = (ActivityManager) getSystemService( ACTIVITY_SERVICE );

		List<ActivityManager.RunningTaskInfo> taskList = mngr.getRunningTasks(10);

		if(taskList.get(0).numActivities == 1 &&
		   taskList.get(0).topActivity.getClassName().equals(this.getClass().getName())) {
		    Log.i("Acitivity anterior", this.getClass().getName());
		}
		*/
		
		
		View header = (View) this.getLayoutInflater().inflate(R.layout.listagem_vendas_cabecalho, null);
		
		lista.addHeaderView(header);
		
		
		
		lista.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				vendaCliente = (VendasDTO) adapter.getItemAtPosition(position);
				lista.showContextMenuForChild(view);
			}
		});

	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		MenuItem deletar = menu.add("Deletar");

		deletar.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				// TODO Auto-generated method stub

				SaleDAO dao = new SaleDAO(ListaVendas.this);

				dao.deletarProdutoVenda(vendaCliente.getIdvenda());

				// CONTROLE DE SINCRONIZA��O: S� IR� SINCRONIZAR SE ESSE VALOR
				// ESTIVER PREENCHIDO.
				dao.salvaControleSincronizacaoCliente(vendaCliente
						.getIdvendacliente().toString());
				// /
				dao.close();
				// Restartar a activity atual
				Intent intent = getIntent();
				finish();
				startActivity(intent);

				return false;
			}
		});
	
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	/*
	 * @Override public boolean onOptionsItemSelected(MenuItem item) { // TODO
	 * Auto-generated method stub
	 * 
	 * int itemClicado = item.getItemId(); if (itemClicado == android.R.id.home)
	 * { finish(); }
	 * 
	 * 
	 * return super.onOptionsItemSelected(item); }
	 */

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		carregarLista();
	}

	public void carregarLista() {

		Intent intent = getIntent();
		vendaSelecionadaFromListaVendasAbertas = (Long) intent
				.getSerializableExtra("vendaSelecionadaFromListaVendasAbertas");

		SaleDAO dao = new SaleDAO(this);
		vendasDTO = dao.getListaVendas(vendaSelecionadaFromListaVendasAbertas
				.toString());

		VendasDTO dadosVenda = dao
				.getDadosVenda(vendaSelecionadaFromListaVendasAbertas);
		
		
	

		
		dao.close();
		
		precoTotalVenda = dadosVenda.getTotal();

		Long idVendaCliente = vendaSelecionadaFromListaVendasAbertas;
		
		if (intent.getSerializableExtra("clienteSelecionadoFromListaPessoasAbrirVenda").equals(null)) {
			//Essa variável somente é utilizada no momento de abrir uma venda
			clienteSelecionadoFromListaPessoasAbrirVenda = "null";	
		} else {
			clienteSelecionadoFromListaPessoasAbrirVenda = (String) intent.getSerializableExtra("clienteSelecionadoFromListaPessoasAbrirVenda").toString();

		}
		
		ListaVendasAdapter adapter = new ListaVendasAdapter(vendasDTO, this,
				precoTotalVenda, this, idVendaCliente, precoTotalVenda,
				vendaSelecionadaFromListaVendasAbertas, clienteSelecionadoFromListaPessoasAbrirVenda);
		
		lista.setAdapter(adapter);

	}

	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

	
		String nova = origem_tela_anterior;
	
		
		
			int itemClicado = item.getItemId();
			if (itemClicado == android.R.id.home) {
				finish();
			} 
		
		

		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		
		

		
		
		super.onBackPressed();

	}
	
	
	

	


	
	
}
