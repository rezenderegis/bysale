package com.sale.cadastro;

import java.util.List;

import com.sale.cadastro.R;
import com.sale.adapter.ListaPessoasAdapter;
import com.sale.cadastro.ListaVendas;
import com.sale.cadastro.task.BaixarDadosCategoria;
import com.sale.cadastro.task.BaixarDadosServidor;
import com.sale.cadastro.task.BaixarDadosTask;
import com.sale.cadastro.task.BaixarDadosUsuarioServidor;
import com.sale.cadastro.task.EnviaCadastrosServidorTask;
import com.sale.cadastro.task.EnviaClientesTask;
import com.sale.cadastro.task.EnviaVendaTask;
import com.sale.cadastro.util.ConnectionDetector;
import com.sale.cadastro.util.MenuInicial;
import com.sale.cadastro.util.Tools;
import com.sale.dao.SaleDAO;
import com.sale.login.Login;
import com.sale.login.VerificaLogin;
import com.sale.modelo.ControleSincronizacao;
import com.sale.modelo.Pessoa;
import com.sale.modelo.VendasDTO;

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

public class ListaVendasAbertas extends Activity {

	private String tipo_vendas_enviar = null; // Registra se as vendas a serem
												// enviadas serão totais ou
												// parciais

	private ListView lista;
	public VendasDTO vendaSelecionada;
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
		getActionBar().setTitle("Pedidos");

		lista = (ListView) findViewById(R.id.lista);

		// AVISAR AO AONDROID QUE PRECISA PASSAR UM MENU COM OP«’ES
		registerForContextMenu(lista);

		// INCLUIR COMPORTAMENTO PARA QUANDO CLICAR EM ALGUM ÕTEM DA LISTA
		lista.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int posicao, long id) {
				// TODO Auto-generated method stub

				vendaSelecionada = (VendasDTO) adapter
						.getItemAtPosition(posicao);
				Intent irListaVendas = new Intent(ListaVendasAbertas.this,
						ListaVendas.class); // IR PARA A TELA DE EDI«√O

				// PASSAR O PAR¬METRO DO ID DO CLIENTE PARA CONSULTAR
				irListaVendas.putExtra(
						"vendaSelecionadaFromListaVendasAbertas",
						vendaSelecionada.getIdvendacliente());// AlunoSelecionado
				// È um
				// apelido
				startActivity(irListaVendas);

			}

		});

		lista.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View view,
					int posicao, long id) {
				// TODO Auto-generated method stub
				/*
				 * Toast.makeText(ListaPessoas.this,
				 * "Clique longo"+adapter.getItemIdAtPosition(posicao),
				 * Toast.LENGTH_SHORT).show();
				 */

				// pessoa = (Pessoa) adapter.getItemAtPosition(posicao);

				vendaSelecionada = (VendasDTO) adapter
						.getItemAtPosition(posicao);
				// pessoa.setId(Long.parseLong(posicao+""));
				return false; // COLOQUEI TRUE NA O CLIQUE LOGON CONSUMIR O
								// ENVENTO E N√O IMPRIMIR O EVENTO DE CLIQUE
								// CURTO TAMB…M

				// Ao invÈs de utilizar +posicao, poderia utilizar
				// +adapter.getItemAtPosition(posicao)
				// Quando utilizei o +nomes[posicao] o eclise mandou converter
				// nomes e finaly

			}

		});

		// Log.i("CICLO DE VIDA", "onCreate");
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub

		/*
		 * MenuItem verTabVendas = menu.add("Ver Tab");
		 * verTabVendas.setOnMenuItemClickListener(new OnMenuItemClickListener()
		 * {
		 * 
		 * @Override public boolean onMenuItemClick(MenuItem arg0) { // TODO
		 * Auto-generated method stub
		 * 
		 * Intent irListaVendaTab = new Intent(ListaPessoas.this,
		 * ProdutoActivity.class); // IR PARA A TELA DE EDI«√O
		 * 
		 * //PASSAR O PAR¬METRO DO ID DO CLIENTE PARA CONSULTAR
		 * 
		 * 
		 * // irListaVendas.putExtra("alunoSelecionado",
		 * pessoa);//AlunoSelecionado È um apelido
		 * 
		 * 
		 * 
		 * 
		 * startActivity(irListaVendaTab);
		 * 
		 * return false; } });
		 */

		/*
		 * MenuItem fecharVendaAtiva = menu.add("Fechar Venda Ativa");
		 * fecharVendaAtiva .setOnMenuItemClickListener(new
		 * OnMenuItemClickListener() {
		 * 
		 * @Override public boolean onMenuItemClick(MenuItem arg0) { // TODO
		 * Auto-generated method stub
		 * 
		 * // / Intent irListaVendas = new // Intent(ListaPessoas.this,
		 * ListaVendas.class); // IR // PARA A TELA DE EDI«√O
		 * 
		 * // PASSAR O PAR¬METRO DO ID DO CLIENTE PARA CONSULTAR
		 * 
		 * // irListaVendas.putExtra("alunoSelecionado", //
		 * pessoa);//AlunoSelecionado È um apelido
		 * 
		 * // startActivity(irListaVendas);
		 * 
		 * SaleDAO dao = new SaleDAO(ListaVendasAbertas.this);
		 * 
		 * String idvendacliente = dao.consultaVendaAberta(pessoa);
		 * 
		 * dao.fecharVenda(idvendacliente); dao.close();
		 * 
		 * // CONTROLE DE SINCRONIZA«√O: S” IR¡ SINCRONIZAR SE ESSE // VALOR
		 * ESTIVER PREENCHIDO.
		 * dao.salvaControleSincronizacaoCliente(idvendacliente); dao.close();
		 * 
		 * // Restartar a activity atual para sincronizar apÛs // fechar a venda
		 * Intent intent = getIntent(); finish(); startActivity(intent);
		 * 
		 * return false; } });
		 * 
		 * tipoContaEmpresaUsuario = dao.tipoContaEmpresaUsuario(); tipoEmpresa
		 * = dao.tipoEmpresa();
		 * 
		 * usuario_master = dao.informacoesUsuario();
		 * 
		 * if (usuario_master.equals("S")) { // Se a empresa for multi, n„o ser·
		 * // permitido que o usu·rio edite o // cliente MenuItem editarCadastro
		 * = menu.add("Editar Cadastro");
		 * 
		 * editarCadastro .setOnMenuItemClickListener(new
		 * OnMenuItemClickListener() {
		 * 
		 * @Override public boolean onMenuItemClick(MenuItem item) { // TODO
		 * Auto-generated method stub
		 * 
		 * Intent irParaFormulario = new Intent( ListaVendasAbertas.this,
		 * Formulario.class); // IR // PARA // A // TELA // DE // EDI«√O
		 * 
		 * 
		 * irParaFormulario.putExtra("alunoSelecionado", pessoa);//
		 * AlunoSelecionado È um apelido
		 * 
		 * startActivity(irParaFormulario);
		 * 
		 * return false; } }); }
		 * 
		 * if (tipoEmpresa != 1) { MenuItem ligar = menu.add("Ligar");
		 * 
		 * ligar.setOnMenuItemClickListener(new OnMenuItemClickListener() {
		 * 
		 * @Override public boolean onMenuItemClick(MenuItem arg0) { // TODO
		 * Auto-generated method stub
		 * 
		 * // Chamar a classe que faz a ligaÁ„o. Mas o aplicativo // poder· ser
		 * utilizado em tablete que n„o faz, ent„o temos // de utilizar uma
		 * funÁ„o que pergunta quem faz ligaÁıes. Intent irParaTelaDeDiscagem =
		 * new Intent(Intent.ACTION_CALL); Uri discarPara = Uri.parse("tel:" +
		 * pessoa.getTelefone()); irParaTelaDeDiscagem.setData(discarPara); //
		 * AQUI TEMOS DE // PASSAR UMA // URI, QUE … O // JEITO DO // ANDROID DE
		 * // ENCONTRAR // ENDERE«OS startActivity(irParaTelaDeDiscagem); return
		 * false; } }); } final MenuItem enviarSMS = menu.add("Enviar SMS");
		 * 
		 * enviarSMS.setOnMenuItemClickListener(new OnMenuItemClickListener() {
		 * 
		 * @Override public boolean onMenuItemClick(MenuItem item) { // TODO
		 * Auto-generated method stub
		 * 
		 * Intent intentSms = new Intent(Intent.ACTION_VIEW);
		 * intentSms.setData(Uri.parse("sms:" + pessoa.getTelefone()));
		 * intentSms.putExtra("sms_body", "Mensagem");
		 * enviarSMS.setIntent(intentSms);
		 * 
		 * return false; } });
		 * 
		 * if (tipoEmpresa != 1) { final MenuItem verEnderecoMap =
		 * menu.add("Endereço no Mapa");
		 * 
		 * verEnderecoMap .setOnMenuItemClickListener(new
		 * OnMenuItemClickListener() {
		 * 
		 * @Override public boolean onMenuItemClick(MenuItem item) { // TODO
		 * Auto-generated method stub
		 * 
		 * Intent intentMapa = new Intent(Intent.ACTION_VIEW);
		 * intentMapa.setData(Uri.parse("geo:0,0?z=14&q=" +
		 * pessoa.getEndereco())); verEnderecoMap.setIntent(intentMapa);
		 * 
		 * return false; } }); }
		 */
		final MenuItem enviarEmail = menu.add("E-mail");

		enviarEmail.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				// TODO Auto-generated method stub
				Intent intentEmail = new Intent(Intent.ACTION_SEND);
				intentEmail.setType("message/rtc822");
				intentEmail.putExtra(Intent.EXTRA_EMAIL,
						new String[] { "rezenderegis@gmail.com" });
				intentEmail.putExtra(Intent.EXTRA_SUBJECT,
						"Testando subject do email");
				intentEmail.putExtra(Intent.EXTRA_TEXT,
						"Testando corpo do email");
				enviarEmail.setIntent(intentEmail);

				return false;
			}
		});

		/*
		 * if (tipoEmpresa != 1) { MenuItem navegarNoSite = menu.add("Site");
		 * 
		 * navegarNoSite .setOnMenuItemClickListener(new
		 * OnMenuItemClickListener() {
		 * 
		 * @Override public boolean onMenuItemClick(MenuItem item) { // TODO
		 * Auto-generated method stub
		 * 
		 * Intent irParaOSite = new Intent(Intent.ACTION_VIEW);
		 * 
		 * Uri localSite = Uri.parse("http://" + pessoa.getSite());
		 * irParaOSite.setData(localSite); startActivity(irParaOSite); return
		 * false; } });
		 * 
		 * } if (tipoContaEmpresaUsuario != 2) { // Se a empresa for multi, n„o
		 * ser· // permitido que o usu·rio delete o // cliente
		 * 
		 * // MOSTRAR MENU DE DELE«√O E DELETAR MenuItem deletar =
		 * menu.add("Deletar"); deletar.setOnMenuItemClickListener(new
		 * OnMenuItemClickListener() {
		 * 
		 * @Override public boolean onMenuItemClick(MenuItem item) { SaleDAO dao
		 * = new SaleDAO(ListaVendasAbertas.this); dao.deletar(pessoa);
		 * dao.close(); carregaLista(); return false; } }); }
		 */

		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		// Em 15-02 troquei o onCreateOptionsMenu pelo onPrepare, pois o
		// onCreate só carrega uma vez.
		// A classe INFLATE o arquivo xml de menu e joga na tela

		MenuInflater inflater = getMenuInflater();

		inflater.inflate(R.menu.menu_incluir_venda, menu);

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
		case R.id.menu_incluir_venda:

			Intent irParaFormulario = new Intent(this,
					ListaPessoasAbrirVenda.class);

			startActivity(irParaFormulario);

			break;

		case R.id.enviar_clientes:
			// Menu para enviar vendas ao servidor
			ConnectionDetector detectaConexao = new ConnectionDetector(
					ListaVendasAbertas.this);

			Boolean conexao = detectaConexao.isConnectingToInternet();

			// Só deixa sincronizar as vendas se houver conexão

			if (conexao == false) {
				String mensagem = null;

				mensagem = "Seu aparelho está sem conexão com internet. Ative a conexão para sincronizar.";

				AlertDialog.Builder alertDialogTrocar = new AlertDialog.Builder(
						ListaVendasAbertas.this);
				alertDialogTrocar.setMessage(mensagem);
				alertDialogTrocar.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int id) {
								// TODO Auto-generated method stub

							}
						});

				AlertDialog alertDialogNovo = alertDialogTrocar.create();
				alertDialogNovo.show();

				// Mudar a cor do bot„o
				((Button) alertDialogNovo.findViewById(android.R.id.button2))
						.setBackgroundResource(R.color.botao_picker);
				((Button) alertDialogNovo.findViewById(android.R.id.button1))
						.setBackgroundResource(R.color.botao_picker);
				((Button) alertDialogNovo.findViewById(android.R.id.button1))
						.setTextColor(getResources().getColor(R.color.branco));
				((Button) alertDialogNovo.findViewById(android.R.id.button2))
						.setTextColor(getResources().getColor(R.color.branco));
			} else {

				List<ControleSincronizacao> idVendaclienteSincronizar = dao
						.vendaClienteParaSincronizar();
				EnviaVendaTask task = new EnviaVendaTask(
						ListaVendasAbertas.this, idVendaclienteSincronizar,
						"enviar_todas_as_vendas");

				// EnviaClientesTask task = new EnviaClientesTask(this);
				task.execute();
			}

			// Fim verificação internet

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
		List<VendasDTO> vendasAbertas = dao.getClientesVendasAbertas2(0);
		// List<Pessoa> pessoas = dao.getClientesVendasAbertas();
		dao.close(); // TODA VEZ QUE INSTANCIARMOS O DAO ELE DEVE SER FECHADO

		// ListaPessoasAdapter adapter = new ListaPessoasAdapter(vendasAbertas,
		// this,
		// ListaVendasAbertas.this);
		// VINCULAR ADAPTER A LISTAGEM
		// lista.setAdapter(adapter);
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
				ListaVendasAbertas.this);

		Boolean conexao = detectaConexao.isConnectingToInternet();

		// Verifica se a sincronização de vendas é automática ou manual
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
			EnviaVendaTask task = new EnviaVendaTask(ListaVendasAbertas.this,
					idVendaclienteSincronizar, "somente_vendas_pendentes");

			task.execute();

		}

		Tools tool = new Tools();
		String data = tool.trazDataAtual();

		/*
		 * Verifica se os dados do usuário foram baixados na data de hoje. Se
		 * não tiverem sido, ele aciona a classe BaixarDadosUsuarioServidor e
		 * chama o método dao.gravarSincronizacaoDataAtual() para registrar a
		 * data da gravação
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
