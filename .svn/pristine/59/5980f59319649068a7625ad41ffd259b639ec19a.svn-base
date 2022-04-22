package com.sale.cadastro.util;


import java.net.URI;
import java.util.Date;
import java.util.Calendar;
import java.util.List;

import pedidos.PedidosFragment;

import com.sale.cadastro.R;
import com.sale.cadastro.Formulario;
import com.sale.cadastro.ListaCategoriasProdutos;
import com.sale.cadastro.ListaPessoas;
import com.sale.cadastro.ListaProdutosCadastradosActivity;
import com.sale.cadastro.SMSReceiver;
import com.sale.cadastro.TelaRelatorios;
import com.sale.cadastro.task.BaixarDadosServidor;
import com.sale.cadastro.task.EnviaCadastrosServidorTask;
import com.sale.cadastro.task.EnviaVendaTask;
import com.sale.dao.SaleDAO;
import com.sale.login.VerificaLogin;
import com.sale.modelo.ControleSincronizacao;
import com.sale.modelo.VendasDTO;
import com.sale.relatorios.RelatorioPagamentos;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class MenuInicial extends Activity {

	private ImageButton pedidos;
	private ImageButton clientes;
	private ImageButton produtos;
	private ImageButton categorias;
	private ImageButton relatorios;
	Long tipoContaEmpresaUsuario = null;
	Long tipoEmpresa = null;
	String usuario_master = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		
		/*
		
		boolean alarmeAtivo = (PendingIntent.getBroadcast(this, 0, new Intent("ALARME_DISPARADO"), PendingIntent.FLAG_NO_CREATE) == null);
		
		if(alarmeAtivo){
			Log.i("Script", "Ativo");

			Intent intent = new Intent("ALARME_DISPARADO");
			PendingIntent p = PendingIntent.getBroadcast(this, 0, intent, 0);
			
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(System.currentTimeMillis());
		//	c.add(Calendar.SECOND, 5);
			
			AlarmManager alarme = (AlarmManager) getSystemService(ALARM_SERVICE);
			alarme.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), 5000, p);
		}
		else{
			Log.i("Script", "Nao ativo");
		}
			
		*/	
		
		
		setContentView(R.layout.menu_inicial_table);
		
		
		
		// getActionBar().setDisplayHomeAsUpEnabled(true);

		pedidos = (ImageButton) findViewById(R.id.imageButton1);

		clientes = (ImageButton) findViewById(R.id.imageButton2);

		produtos = (ImageButton) findViewById(R.id.imageButton3);

		categorias = (ImageButton) findViewById(R.id.ImageButton01);

		relatorios = (ImageButton) findViewById(R.id.imageButton5);

		pedidos.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent irParaTelaDePedidos = new Intent(MenuInicial.this,
						PedidosFragment.class);
				startActivity(irParaTelaDePedidos);

			}
		});

		clientes.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
		
				Intent irParaTelaDeClientes = new Intent(MenuInicial.this,
						ListaPessoas.class);
				startActivity(irParaTelaDeClientes);
			}
		});

		produtos.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent irParaTelaDeProdutos = new Intent(MenuInicial.this,
						ListaProdutosCadastradosActivity.class);
				startActivity(irParaTelaDeProdutos);
			}
		});

		categorias.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent irParaTelaDeCategorias = new Intent(MenuInicial.this,
						ListaCategoriasProdutos.class);
				startActivity(irParaTelaDeCategorias);
			}
		});

		relatorios.setOnClickListener(new View.OnClickListener() {

			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Relatorio2
				//fazerBackup();
				Intent abrirRelatorio = new Intent(MenuInicial.this,
						TelaRelatorios.class);
				startActivity(abrirRelatorio);

			}
/*
			private void fazerBackup() {
				// TODO Auto-generated method stub
				File sd = Environment.getExternalStorageDirectory();
		        File data = Environment.getDataDirectory();
		        FileChannel source=null;
		        FileChannel destination=null;
		        String currentDBPath = "/data/"+ "com.sale.cadastro" +"/databases/"+"mysale.db";
		        String backupDBPath = "mysale.db";
		        File currentDB = new File(data, currentDBPath);
		        File backupDB = new File(sd, backupDBPath);
		        try {
		            source = new FileInputStream(currentDB).getChannel();
		            destination = new FileOutputStream(backupDB).getChannel();
		            destination.transferFrom(source, 0, source.size());
		            source.close();
		            destination.close();
		            //Toast.makeText(this, "DB Exported!", Toast.LENGTH_LONG).show();
		        } catch(IOException e) {
		        	e.printStackTrace();
		        }
			}*/
		});

	}

	private void haPedidosVencidos() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		// Em 15-02 troquei o onCreateOptionsMenu pelo onPrepare, pois o
		// onCreate s� carrega uma vez.
		// A classe INFLATE o arquivo xml de menu e joga na tela
		SaleDAO dao = new SaleDAO(this);
		tipoContaEmpresaUsuario = dao.tipoContaEmpresaUsuario();
		usuario_master = dao.informacoesUsuario();
		dao.close();
		MenuInflater inflater = getMenuInflater();

		if (usuario_master.equals("S")) {
			// Menu de empresas multi
			inflater.inflate(R.menu.menu_com_cadastro_usuario, menu);

		} else {
			// Menu de empresa single
			inflater.inflate(R.menu.menu_sem_cadastro_usuario, menu);
		}

		return super.onCreateOptionsMenu(menu);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		final SaleDAO dao = new SaleDAO(this);

		int itemClicado = item.getItemId();

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

		case R.id.enviar_clientes:
			// Menu para enviar vendas ao servidor
			ConnectionDetector detectaConexao = new ConnectionDetector(
					MenuInicial.this);

			Boolean conexao = detectaConexao.isConnectingToInternet();

			// S� deixa sincronizar as vendas se houver conex�o

			if (conexao == false) {
				String mensagem = null;

				mensagem = "Seu aparelho est� sem conex�o com internet. Ative a conex�o para sincronizar.";

				AlertDialog.Builder alertDialogTrocar = new AlertDialog.Builder(
						MenuInicial.this);
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

				// Mudar a cor do bot�o
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
				EnviaVendaTask task = new EnviaVendaTask(MenuInicial.this,
						idVendaclienteSincronizar, "enviar_todas_as_vendas");

				// EnviaClientesTask task = new EnviaClientesTask(this);
				task.execute();
			}

			// Fim verifica��o internet

			break;

		case R.id.bloquear_tela:

			View mensagemConfirmacao = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE))
					.inflate(R.layout.mensagem_bloquear, null);
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					MenuInicial.this);
			alertDialogBuilder.setView(mensagemConfirmacao);
			alertDialogBuilder.setPositiveButton("Confirmar",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int id) {
							// TODO Auto-generated method stub
							dao.FazerLogof();
							Intent loginAposLogof = new Intent(
									MenuInicial.this, VerificaLogin.class);
							startActivity(loginAposLogof);
						}
					}).setNegativeButton("Cancelar",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.cancel();
						}
					});

			AlertDialog alertDialog = alertDialogBuilder.create();
			alertDialog.show();

			// Mudar a cor do bot�o
			((Button) alertDialog.findViewById(android.R.id.button2))
					.setBackgroundResource(R.color.botao_picker);
			((Button) alertDialog.findViewById(android.R.id.button1))
					.setBackgroundResource(R.color.botao_picker);
			((Button) alertDialog.findViewById(android.R.id.button1))
					.setTextColor(getResources().getColor(R.color.branco));
			((Button) alertDialog.findViewById(android.R.id.button2))
					.setTextColor(getResources().getColor(R.color.branco));

			break;

		case R.id.mudarUsuario:

			Long idVendaSincronizacao = dao.existeAlunoClicado();
			Boolean existeVendaAtiva = dao.existeVendaAtiva();

			if (idVendaSincronizacao != null || existeVendaAtiva == true) {
				String mensagem = null;
				if (idVendaSincronizacao != null) {
					mensagem = "Voc� deve sincronizar todas as vendas antes de trocar de usu�rio!";
				} else if (existeVendaAtiva == true) {
					mensagem = "Existe venda ativa. Feche todas as vendas antes de trocar de usu�rio!";
				}

				AlertDialog.Builder alertDialogTrocar = new AlertDialog.Builder(
						MenuInicial.this);
				alertDialogTrocar.setMessage(mensagem);
				alertDialogTrocar.setPositiveButton("Confirmar",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int id) {
								// TODO Auto-generated method stub

							}
						});

				AlertDialog alertDialogNovo = alertDialogTrocar.create();
				alertDialogNovo.show();

				// Mudar a cor do bot�o
				((Button) alertDialogNovo.findViewById(android.R.id.button2))
						.setBackgroundResource(R.color.botao_picker);
				((Button) alertDialogNovo.findViewById(android.R.id.button1))
						.setBackgroundResource(R.color.botao_picker);
				((Button) alertDialogNovo.findViewById(android.R.id.button1))
						.setTextColor(getResources().getColor(R.color.branco));
				((Button) alertDialogNovo.findViewById(android.R.id.button2))
						.setTextColor(getResources().getColor(R.color.branco));
			} else {

				dao.mudarUsuario();

				Intent loginAposMudarUsuario = new Intent(this,
						VerificaLogin.class);
				startActivity(loginAposMudarUsuario);
				finish();
			}

			break;
		/*
		 * case R.id.baixar_dados: //Menu para baixar produtos BaixarDadosTask
		 * taskDados = new BaixarDadosTask(this); taskDados.execute();
		 * 
		 * break;
		 */
		/*
		 * case R.id.sincronizar_dados_empresa: //Baixar clientes
		 * BaixarDadosClientes taskDadosEmpresa = new BaixarDadosClientes(this);
		 * taskDadosEmpresa.execute(); break;
		 */
		case R.id.enviar_cadastros_servidor:
			EnviaCadastrosServidorTask taskEnviarCadastrosServidor = new EnviaCadastrosServidorTask(
					this);
			taskEnviarCadastrosServidor.execute();

			break;

		case R.id.baixar_clientes:
			BaixarDadosServidor taskClientes = new BaixarDadosServidor(this);
			taskClientes.execute();

			break;

		/*
		 * case R.id.baixar_categorias: BaixarDadosCategoria taskCategoria = new
		 * BaixarDadosCategoria(this); taskCategoria.execute();
		 * 
		 * 
		 * break;
		 */
		/*
		 * case R.id.cadastrar_categoria:
		 * 
		 * 
		 * Intent cadastrar_categoria = new Intent(this,
		 * FormularioCategoriaActivity.class);
		 * startActivity(cadastrar_categoria); break;
		 */

		case R.id.categorias_cadastradas:
			Intent listar_categorias = new Intent(this,
					ListaCategoriasProdutos.class);
			startActivity(listar_categorias);
			break;

		case R.id.produtos_cadastrados:
			Intent listar_produtos_cadastrados = new Intent(this,
					ListaProdutosCadastradosActivity.class);
			startActivity(listar_produtos_cadastrados);
			break;
		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		// super.onBackPressed();

		Intent irHome = new Intent(Intent.ACTION_MAIN);
		irHome.addCategory(irHome.CATEGORY_HOME);
		irHome.setFlags(irHome.FLAG_ACTIVITY_NEW_TASK);
		startActivity(irHome);

	}
	

	
	
	
	
}
