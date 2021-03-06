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

public class ListaPessoas extends Activity {

	private String tipo_vendas_enviar = null; // Registra se as vendas a serem

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
		getActionBar().setTitle("Clientes Cadastrados");;
		getActionBar().setDisplayHomeAsUpEnabled(true);

		lista = (ListView) findViewById(R.id.lista);

		// AVISAR AO AONDROID QUE PRECISA PASSAR UM MENU COM OP??????ES
		registerForContextMenu(lista);

		// INCLUIR COMPORTAMENTO PARA QUANDO CLICAR EM ALGUM ???TEM DA LISTA
		lista.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int posicao, long id) {
				// TODO Auto-generated method stub

				pessoa = (Pessoa) adapter.getItemAtPosition(posicao);
				lista.showContextMenuForChild(view);
				

			}

		});

		// Log.i("CICLO DE VIDA", "onCreate");
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub

		tipoContaEmpresaUsuario = dao.tipoContaEmpresaUsuario();
		tipoEmpresa = dao.tipoEmpresa();

		usuario_master = dao.informacoesUsuario();

		//if (usuario_master.equals("S")) { 
			
			MenuItem editarCadastro = menu.add(getString(R.string.lista_pessoas_editar_cadastro));

			editarCadastro
					.setOnMenuItemClickListener(new OnMenuItemClickListener() {

						@Override
						public boolean onMenuItemClick(MenuItem item) {
							// TODO Auto-generated method stub

							Intent irParaFormulario = new Intent(
									ListaPessoas.this, Formulario.class); 

							
							irParaFormulario.putExtra("alunoSelecionado",
									pessoa);// AlunoSelecionado ??? um apelido

							startActivity(irParaFormulario);

							return false;
						}
					});
		//}

		if (tipoEmpresa != 1) {
			MenuItem ligar = menu.add(getString(R.string.lista_pessoas_ligar));

			ligar.setOnMenuItemClickListener(new OnMenuItemClickListener() {

				@Override
				public boolean onMenuItemClick(MenuItem arg0) {
					// TODO Auto-generated method stub

					
					Intent irParaTelaDeDiscagem = new Intent(Intent.ACTION_CALL);
					Uri discarPara = Uri.parse("tel:" + pessoa.getTelefone());
					irParaTelaDeDiscagem.setData(discarPara); // AQUI TEMOS DE
																// PASSAR UMA
																// URI, QUE ??? O
																// JEITO DO
																// ANDROID DE
																// ENCONTRAR
																// ENDERE???OS
					startActivity(irParaTelaDeDiscagem);
					return false;
				}
			});
		}
		final MenuItem enviarSMS = menu.add(getString(R.string.listar_pessoas_enviar_sms));

		enviarSMS.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				// TODO Auto-generated method stub

				Intent intentSms = new Intent(Intent.ACTION_VIEW);
				intentSms.setData(Uri.parse("sms:" + pessoa.getTelefone()));
				intentSms.putExtra("sms_body", "Mensagem");
				enviarSMS.setIntent(intentSms);

				return false;
			}
		});

		if (tipoEmpresa != 1) {
			final MenuItem verEnderecoMap = menu.add(getString(R.string.listar_pessoas_ver_endereco));

			verEnderecoMap
					.setOnMenuItemClickListener(new OnMenuItemClickListener() {

						@Override
						public boolean onMenuItemClick(MenuItem item) {
							// TODO Auto-generated method stub

							Intent intentMapa = new Intent(Intent.ACTION_VIEW);
							intentMapa.setData(Uri.parse("geo:0,0?z=14&q="
									+ pessoa.getEndereco()));
							verEnderecoMap.setIntent(intentMapa);

							return false;
						}
					});
		}
		final MenuItem enviarEmail = menu.add(getString(R.string.listar_pessoas_enviar_email));

		enviarEmail.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				// TODO Auto-generated method stub
				Intent intentEmail = new Intent(Intent.ACTION_SEND);
				intentEmail.setType("message/rtc822");
				intentEmail.putExtra(Intent.EXTRA_EMAIL,
						new String[] { "rezenderegis@gmail.com" });
				intentEmail.putExtra(Intent.EXTRA_SUBJECT,
						getString(R.string.listar_pessoas_assunto_email));
				intentEmail.putExtra(Intent.EXTRA_TEXT,
						getString(R.string.listar_pessoas_corpo_email));
				enviarEmail.setIntent(intentEmail);

				return false;
			}
		});

		if (tipoEmpresa != 1) {
			MenuItem navegarNoSite = menu.add(getString(R.string.listar_pessoas_ver_site));

			navegarNoSite
					.setOnMenuItemClickListener(new OnMenuItemClickListener() {

						@Override
						public boolean onMenuItemClick(MenuItem item) {
							// TODO Auto-generated method stub

							Intent irParaOSite = new Intent(Intent.ACTION_VIEW);

							Uri localSite = Uri.parse("http://"
									+ pessoa.getSite());
							irParaOSite.setData(localSite);
							startActivity(irParaOSite);
							return false;
						}
					});

		}
		if (tipoContaEmpresaUsuario != 2) { // Se a empresa for multi, n???o ser???
											// permitido que o usu???rio delete o
											// cliente

			// MOSTRAR MENU DE DELE??????O E DELETAR
			MenuItem deletar = menu.add(getString(R.string.listar_pessoas_deletar));
			deletar.setOnMenuItemClickListener(new OnMenuItemClickListener() {

				@Override
				public boolean onMenuItemClick(MenuItem item) {
					SaleDAO dao = new SaleDAO(ListaPessoas.this);
					dao.deletar(pessoa);
					dao.close();
					carregaLista();
					return false;
				}
			});
		}
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		// Em 15-02 troquei o onCreateOptionsMenu pelo onPrepare, pois o
		// onCreate s??? carrega uma vez.
		// A classe INFLATE o arquivo xml de menu e joga na tela
		tipoContaEmpresaUsuario = dao.tipoContaEmpresaUsuario();
		usuario_master = dao.informacoesUsuario();

		MenuInflater inflater = getMenuInflater();

		/* Data: 24-01-2014
		 * A partir de hoje o menu de cadastro de usu??rio fica liberado para todos os usu??rios
		 * */
		//if (usuario_master.equals("S")) {
			// Menu de empresas multi
			inflater.inflate(R.menu.menu_incluir_clientes, menu);

		//}

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
		case R.id.menu_novo:

			Intent irParaFormulario = new Intent(this, Formulario.class);

			startActivity(irParaFormulario);

			break;

		/*
		 * case R.id.cadastrar_produto:
		 * 
		 * Intent cadastrarProduto = new Intent(this,
		 * FormularioProdutoActivity.class); startActivity(cadastrarProduto);
		 * break;
		 */

		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		verificarSeHaSincronizacaoPendente();
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
				pessoas, this, ListaPessoas.this);
		// VINCULAR ADAPTER A LISTAGEM
		lista.setAdapter(adapter);
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();

		verificarSeHaSincronizacaoPendente();

		/*
		 * if (dao.existeAlunoClicado() != null) {
		 * 
		 * Long idVendaclienteSincronizar = dao.existeAlunoClicado();
		 * EnviaVendaTask task = new
		 * EnviaVendaTask(ListaPessoas.this,idVendaclienteSincronizar);
		 * 
		 * task.execute();
		 * 
		 * dao.deletarControle(); }
		 */
		Log.i("CICLO DE VIDA", "onRestart");
	}

	private void verificarSeHaSincronizacaoPendente() {
		// TODO Auto-generated method stub

		ConnectionDetector detectaConexao = new ConnectionDetector(
				ListaPessoas.this);

		Boolean conexao = detectaConexao.isConnectingToInternet();

		// Verifica se a sincroniza??????o de vendas ??? autom???tica ou manual
		String sincronizacaoEntradaAplicativo = dao
				.verificaParametroSincronizacao(1);
		// String sincronizacaoFechamentoVenda =
		// dao.verificaParametroSincronizacao(2);

		// Boolean servidor = detectaConexao.servidorAtivo();

		// Toast.makeText(ListaPessoas.this, "Servidor Ativo: "+servidor,
		// Toast.LENGTH_LONG).show();

		if (dao.existeAlunoClicado() != null && conexao == true
				&& sincronizacaoEntradaAplicativo.equals("S")) {

			List<ControleSincronizacao> idVendaclienteSincronizar = dao
					.vendaClienteParaSincronizar();
			EnviaVendaTask task = new EnviaVendaTask(ListaPessoas.this,
					idVendaclienteSincronizar, "somente_vendas_pendentes");

			task.execute();

		}

		Tools tool = new Tools();
		String data = tool.trazDataAtual();

		/*
		 * Verifica se os dados do usu???rio foram baixados na data de hoje. Se
		 * n???o tiverem sido, ele aciona a classe BaixarDadosUsuarioServidor e
		 * chama o m???todo dao.gravarSincronizacaoDataAtual() para registrar a
		 * data da grava??????o
		 */
		Boolean dadosUsuarioBaixadosDoServidorHoje = dao
				.dadosUsuarioBaixadosDoServidorHoje();
		if (dadosUsuarioBaixadosDoServidorHoje.equals(false) && conexao == true) {
			BaixarDadosUsuarioServidor taskUsuario = new BaixarDadosUsuarioServidor(
					this);
			taskUsuario.execute();
		}
	}

	/*
	 * @Override protected void onPause() { // TODO Auto-generated method stub
	 * super.onPause(); Log.i("CICLO DE VIDA", "onPause"); }
	 * 
	 * @Override protected void onStop() { // TODO Auto-generated method stub
	 * super.onStop(); Log.i("CICLO DE VIDA", "onStop"); }
	 * 
	 * @Override protected void onDestroy() { // TODO Auto-generated method stub
	 * super.onDestroy(); Log.i("CICLO DE VIDA", "onDestroi"); }
	 */

}
