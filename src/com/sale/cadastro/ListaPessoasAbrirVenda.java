package com.sale.cadastro;

import java.util.List;

import com.sale.cadastro.R;
import com.sale.adapter.ListaPessoasAdapter;
import com.sale.adapter.ListaPessoasCadastradasAdapter;
import com.sale.cadastro.ListaVendas;
import com.sale.cadastro.task.BaixarDadosCategoria;
import com.sale.cadastro.task.BaixarDadosServidor;
import com.sale.cadastro.task.BaixarDadosTask;
import com.sale.cadastro.task.BaixarDadosUsuarioServidor;
import com.sale.cadastro.task.EnviaCadastrosServidorTask;
import com.sale.cadastro.task.EnviaClientesTask;
import com.sale.cadastro.task.EnviaVendaTask;
import com.sale.cadastro.util.ConnectionDetector;
import com.sale.cadastro.util.Tools;
import com.sale.dao.SaleDAO;
import com.sale.login.Login;
import com.sale.login.VerificaLogin;
import com.sale.modelo.ControleSincronizacao;
import com.sale.modelo.Pessoa;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

public class ListaPessoasAbrirVenda extends Activity {

	private String tipo_vendas_enviar = null; 

	private ListView lista;
	public Pessoa pessoa;
	SaleDAO dao = new SaleDAO(this);

	Long tipoContaEmpresaUsuario = null;
	Long tipoEmpresa = null;
	String usuario_master = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.listagem_alunos);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);

		getActionBar().setTitle("Venda: Selecione o Cliente");
		lista = (ListView) findViewById(R.id.lista);

		// AVISAR AO AONDROID QUE PRECISA PASSAR UM MENU COM OP��ES
		registerForContextMenu(lista);

		// INCLUIR COMPORTAMENTO PARA QUANDO CLICAR EM ALGUM �TEM DA LISTA
		lista.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int posicao, long id) {
				// TODO Auto-generated method stub

				pessoa = (Pessoa) adapter.getItemAtPosition(posicao);

				//
				Intent irListaVendas = new Intent(ListaPessoasAbrirVenda.this,
						ListaVendas.class); // IR PARA A TELA DE EDI��O

				// PASSAR O PAR�METRO DO ID DO CLIENTE PARA CONSULTAR

				dao.salvaVendaCliente(pessoa.getId());

				String idvendaclienteInsere = dao
						.trazUltimaVendaCliente(pessoa);

				irListaVendas.putExtra(
						"vendaSelecionadaFromListaVendasAbertas",
						Long.parseLong(idvendaclienteInsere));// AlunoSelecionado
				
				irListaVendas.putExtra("clienteSelecionadoFromListaPessoasAbrirVenda", pessoa.getId());
				
				irListaVendas.putExtra("origem", "LISTA_PESSOAS");

				// � um

				// � um
				// apelido
				startActivity(irListaVendas);

			}

		});

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

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub

		super.onResume();

		carregaLista();

		Log.i("CICLO DE VIDA", "onResume");
	}

	private void carregaLista() {
		// TODO Auto-generated method stub
		// Mostrar o pessoa na tela
		SaleDAO dao = new SaleDAO(this);

		List<Pessoa> pessoas = dao.getLista();
		dao.close(); // TODA VEZ QUE INSTANCIARMOS O DAO ELE DEVE SER FECHADO

		ListaPessoasCadastradasAdapter adapter = new ListaPessoasCadastradasAdapter(
				pessoas, this, ListaPessoasAbrirVenda.this);
		// VINCULAR ADAPTER A LISTAGEM
		lista.setAdapter(adapter);
	}

}
