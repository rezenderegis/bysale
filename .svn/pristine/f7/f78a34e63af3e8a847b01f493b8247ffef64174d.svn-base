package com.sale.cadastro;

import java.util.List;

import com.sale.cadastro.R;
import com.sale.adapter.ListaPagamentosAdapter;
import com.sale.adapter.ListaPessoasAdapter;
import com.sale.adapter.ListaVendasAdapter;
import com.sale.dao.SaleDAO;
import com.sale.modelo.Pagamento;
import com.sale.modelo.Pessoa;
import com.sale.modelo.Vendas;
import com.sale.modelo.VendasDTO;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.ListView;

public class ListaPagamentos extends Activity {

	private ListView lista;
	public Vendas venda;
	public Pessoa alunoParaSerAlterado;
	public VendasDTO vendaCliente;
	private ListView produtoLista;
	private Long idVendaCliente;
	private VendasDTO novoCliente;
	private Pagamento pagamento;
	List<Pagamento> pagamentos;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.listagem_alunos);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle("Plano de Pagamento");
		lista = (ListView) findViewById(R.id.lista);
		
		View header = (View) this.getLayoutInflater().inflate(R.layout.listagem_pagamentos_cabecalho, null);
		lista.addHeaderView(header);
		
		/*
		 
		  	if (pagamento != null) {
			View header = (View) this.getLayoutInflater().inflate(R.layout.listagem_pagamentos_cabecalho, null);
			lista.addHeaderView(header);
		} else {
			View header = (View) this.getLayoutInflater().inflate(R.layout.listagem_pagamentos_cabecalho_com_pgt_vazio, null);
			lista.addHeaderView(header);
		}
		  
		 * 
		 **
		 */
		
		
		
		
		
		registerForContextMenu(lista);
		
			
	
		lista.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				pagamento = (Pagamento) adapter.getItemAtPosition(position);
				lista.showContextMenuForChild(view);

			}
		});
		
	}

	/*
	 * @Override public void onCreateContextMenu(ContextMenu menu, View v,
	 * ContextMenuInfo menuInfo) { // TODO Auto-generated method stub
	 * 
	 * MenuItem editarCategoria = menu.add("Editar Categoria");
	 * 
	 * editarCategoria .setOnMenuItemClickListener(new OnMenuItemClickListener()
	 * {
	 * 
	 * @Override public boolean onMenuItemClick(MenuItem item) { // TODO
	 * Auto-generated method stub
	 * 
	 * Intent editarProduto = new Intent( ListaCategoriasProdutos.this,
	 * FormularioCategoriaActivity.class);
	 * editarProduto.putExtra("categoriaParaEditar", categoriaProdutoEditar);
	 * 
	 * startActivity(editarProduto);
	 * 
	 * return false; } });
	 * 
	 * super.onCreateContextMenu(menu, v, menuInfo); }
	 */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		if (pagamento != null) {
						MenuItem deletarPagamento = menu.add("Excluir Pagamento");
						deletarPagamento
								.setOnMenuItemClickListener(new OnMenuItemClickListener() {
				
									@Override
									public boolean onMenuItemClick(MenuItem item) {
										// TODO Auto-generated method stub
										
										SaleDAO dao = new SaleDAO(ListaPagamentos.this);
										dao.deletarFormaPagamento(pagamento.getIdpagamento());
										Intent intent = getIntent();
										finish();
										startActivity(intent);
										
										return false;
									}
									
								});
						
						
						MenuItem gravar_pagamento_efetuado = menu.add("Pagamento Efetuado");
						gravar_pagamento_efetuado
								.setOnMenuItemClickListener(new OnMenuItemClickListener() {
				
									@Override
									public boolean onMenuItemClick(MenuItem item) {
										// TODO Auto-generated method stub
										
										SaleDAO dao = new SaleDAO(ListaPagamentos.this);
										dao.informar_pagamento_efetuado(pagamento.getIdpagamento());
										Intent intent = getIntent();
										finish();
										startActivity(intent);
										
										return false;
									}
									
								});
						
			}
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		carregaLista();
	}

	private void carregaLista() {
		// TODO Auto-generated method stub
		// Mostrar o pessoa na tela
		SaleDAO dao = new SaleDAO(this);
		Double precoTotalVenda;
		Intent intent = getIntent();
		idVendaCliente = (Long) intent.getSerializableExtra("idVendaCliente");
		precoTotalVenda = (Double) intent
				.getSerializableExtra("precoTotalVenda");

		novoCliente = (VendasDTO) intent.getSerializableExtra("clienteVenda");

		pagamentos = dao.getPagamento(idVendaCliente);

		Double totalPagamento = dao.getTotalPagamento(idVendaCliente);

		// clienteVenda
		dao.close();

	
		
		ListaPagamentosAdapter adapter = new ListaPagamentosAdapter(pagamentos,
				this, ListaPagamentos.this, totalPagamento, precoTotalVenda,
				idVendaCliente);
		// VINCULAR ADAPTER A LISTAGEM
		lista.setAdapter(adapter);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		int itemClicado = item.getItemId();

		if (itemClicado == android.R.id.home) {
			finish();
		}

		return super.onOptionsItemSelected(item);
	}

	/*
	 * @Override public void onBackPressed() { // TODO Auto-generated method
	 * stub super.onBackPressed();
	 * 
	 * // Em 04 de julho de 2015: implementei o onBackPressed por que quando //
	 * apertava o voltar na tela de lista de pagamentos ele sempre voltava //
	 * para a mesma tela. // aogora, se pressionar o voltar nessa tela, ele deve
	 * voltar para a // tela de lista de vendas. Intent irTelaListaVendas = new
	 * Intent(ListaPagamentos.this, ListaVendas.class);
	 * 
	 * /* Est‡ travando por que o atributo novoCliente est‡ vindo nulo
	 * 
	 * *
	 * 
	 * irTelaListaVendas.putExtra("alunoParaSerAlterado", novoCliente);//
	 * AlunoSelecionado // é um // apelido startActivity(irTelaListaVendas); }
	 */

}
