package com.sale.relatorios;

import java.util.List;

import com.sale.cadastro.R;
import com.sale.adapter.ListaVendasAdapter;
import com.sale.adapter.Relatorio2Adapter;
import com.sale.cadastro.ListaVendas;
import com.sale.dao.SaleDAO;
import com.sale.modelo.Pessoa;
import com.sale.modelo.VendaCliente;
import com.sale.modelo.Vendas;
import com.sale.modelo.VendasDTO;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import android.widget.Toast;

public class Relatorio2 extends Activity {

	private ListView lista;
	public Vendas venda;
	public Long vendaSelecionadaFromListaVendasAbertas;
	public VendasDTO vendaCliente;
	private ListView produtoLista;
	private List<VendasDTO> vendasDTO;
	Double precoTotalVenda;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.listagem_alunos);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle("Relatório de Pedidos");
		lista = (ListView) findViewById(R.id.lista);
		View header = (View) this.getLayoutInflater().inflate(R.layout.relatorio_venda_cabecalho, null);
		lista.addHeaderView(header);
		registerForContextMenu(lista);

		
		/*
		lista.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int position, long id) {
				
				VendasDTO vendaSelecionada = (VendasDTO) adapter
						.getItemAtPosition(position);
				Intent irListaVendas = new Intent(Relatorio2.this, ListaVendas.class); // IR PARA A TELA DE
															// EDI«√O

				// PASSAR O PAR¬METRO DO ID DO CLIENTE PARA CONSULTAR

				irListaVendas.putExtra(
						"vendaSelecionadaFromListaVendasAbertas",
						vendaSelecionada.getIdvendacliente());// AlunoSelecionado
				irListaVendas.putExtra("clienteSelecionadoFromListaPessoasAbrirVenda", vendaSelecionada.getIdCliente());
				startActivity(irListaVendas);


			}
		});
*/
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

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub

		MenuItem deletar = menu.add("Deletar");

		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		carregarLista();
	}

	public void carregarLista() {

		Intent intent = getIntent();

		SaleDAO dao = new SaleDAO(this);

		String data_inicial = (String) intent
				.getSerializableExtra("data_inicial_buscar");
		String data_final = (String) intent
				.getSerializableExtra("data_final_buscar");
		String pedidos_em_orcamento = (String) intent
				.getSerializableExtra("pedidos_em_orcamento");
		
		String pedidos_emitidos = (String) intent
				.getSerializableExtra("pedidos_emitidos");
		vendasDTO = dao.getClientesVendasAbertas2(0, data_inicial, data_final,
				1, pedidos_em_orcamento, pedidos_emitidos);
		List<VendasDTO> totalVendas = dao.getClientesVendasAbertas2(0,
				data_inicial, data_final, 2, pedidos_em_orcamento, pedidos_emitidos);
		Double valorVendasSomadas = null;
		for (VendasDTO vendasDTO : totalVendas) {
			valorVendasSomadas = vendasDTO.getTotal();
		}
		dao.close();

		if (vendasDTO.equals("")) {
			Toast.makeText(Relatorio2.this, "Sem informações de busca", Toast.LENGTH_LONG).show();
		} else {
			Relatorio2Adapter adapter = new Relatorio2Adapter(vendasDTO, this,
					this, valorVendasSomadas);
	
			lista.setAdapter(adapter);
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();

	}

}
