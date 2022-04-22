package com.sale.login;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import cadastro_usuario.CadastroUsuario;

import com.sale.cadastro.R;
import com.sale.cadastro.ListaPessoas;
import com.sale.cadastro.TelaInicioActivity;
import com.sale.cadastro.util.Botao;
import com.sale.cadastro.util.ConnectionDetector;
import com.sale.cadastro.util.Constantes;
import com.sale.cadastro.util.MenuInicial;
import com.sale.dao.SaleDAO;
import com.sale.modelo.CategoriaProduto;
import com.sale.modelo.Pessoa;
import com.sale.modelo.Produto;
import com.sale.modelo.ProdutoDTO;
import com.sale.negocio.NegocioUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class Login extends Activity {
	WebView web;
	EditText un, pw;
	TextView error;
	Button ok;
	private String resp;
	private String errorMsg;
	SaleDAO dao;
	ArrayList<Usuario> usuarioLogin;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		un = (EditText) findViewById(R.id.et_un);
		pw = (EditText) findViewById(R.id.et_pw);
		ok = (Button) findViewById(R.id.btn_login);
		error = (TextView) findViewById(R.id.tv_error);

		TextView cadastrar = (TextView) findViewById(R.id.link_cadastrese);
		TextView link_esqueceu_senha = (TextView) findViewById(R.id.link_esqueceu_senha);
		
		link_esqueceu_senha.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent irParaOSite = new Intent(Intent.ACTION_VIEW);
				
				Uri localSite = Uri.parse(Constantes.ENDERECO_ESQUECI_SENHA);
				irParaOSite.setData(localSite);
				startActivity(irParaOSite);
			}
		});
		cadastrar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent cadastrar_usuario = new Intent(Login.this,
						CadastroUsuario.class);
				startActivity(cadastrar_usuario);

			}
		});

		
		ok.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				/**
				 * According with the new StrictGuard policy, running long tasks
				 * on the Main UI thread is not possible So creating new thread
				 * to create and execute http operations
				 */

				if (un.getText().toString().isEmpty()
						|| pw.getText().toString().isEmpty()) {
					Toast.makeText(Login.this, "Informe o e-mail e a senha!",
							Toast.LENGTH_SHORT).show();
				} else {

					ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
					postParameters.add(new BasicNameValuePair("username", un
							.getText().toString()));
					postParameters.add(new BasicNameValuePair("password", pw
							.getText().toString()));

					String user = un.getText().toString();
					String password = pw.getText().toString();

					usuarioLogin = new ArrayList<Usuario>();
					Usuario usuario = new Usuario();
					usuario.setEmail(user);
					usuario.setPassword(password);
					usuarioLogin.add(usuario);

					//
					dao = new SaleDAO(Login.this);

					String usuarioDispositivo = dao
							.verificaUsuarioDispositivo(2); // VERIFICA SE H�
															// USU�RIO LOGADO
					Long idVendaSincronizacao = dao.existeAlunoClicado();

					Boolean existeVendaAtiva = dao.existeVendaAtiva();

					dao.close();

					try {

						if (usuarioDispositivo != null) {
							if (usuarioDispositivo.equals(user)) {
								// fazerLogin(usuarioLogin);

								ConnectionDetector cn = new ConnectionDetector(
										Login.this);

								if (cn.isConnectingToInternet() == true) {

									LoginJson json = new LoginJson(Login.this,
											usuarioLogin);

									json.execute();

								} else {
									
									Toast.makeText(
											Login.this, getString(R.string.login_habilite_conexao)
											,
											Toast.LENGTH_LONG).show();
								}

							} else if ((usuarioDispositivo != null && idVendaSincronizacao != null)
									|| (existeVendaAtiva == true && usuarioDispositivo != null)) {

								String mensagem = null;
								if (usuarioDispositivo != null
										&& idVendaSincronizacao != null) {
									mensagem = getString(R.string.login_sincronizar_vendas_usuario)
											+ usuarioDispositivo
											+ getString(R.string.login_entre_com_usuario_para_sincronizar);
								} else if (existeVendaAtiva == true
										&& usuarioDispositivo != null) {
									mensagem = getString(R.string.login_existe_venda_ativa);
								}

								// View mensagemConfirma��o = ((LayoutInflater)
								// getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.mensagem_bloquear,
								// null);
								AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
										Login.this);
								alertDialogBuilder.setMessage(mensagem);
								alertDialogBuilder.setPositiveButton(
										getString(R.string.login_confirmar),
										new DialogInterface.OnClickListener() {

											@Override
											public void onClick(
													DialogInterface dialog,
													int id) {
												// TODO Auto-generated method
												// stub

											}
										});

								AlertDialog alertDialog = alertDialogBuilder
										.create();
								alertDialog.show();

								// Mudar a cor do bot�o
								((Button) alertDialog
										.findViewById(android.R.id.button2))
										.setBackgroundResource(R.color.botao_picker);
								((Button) alertDialog
										.findViewById(android.R.id.button1))
										.setBackgroundResource(R.color.botao_picker);
								((Button) alertDialog
										.findViewById(android.R.id.button1))
										.setTextColor(getResources().getColor(
												R.color.branco));
								((Button) alertDialog
										.findViewById(android.R.id.button2))
										.setTextColor(getResources().getColor(
												R.color.branco));

							} else if (usuarioDispositivo != null
									&& idVendaSincronizacao == null) {

								// View mensagemConfirma��o = ((LayoutInflater)
								// getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.mensagem_bloquear,
								// null);
								AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
										Login.this);
								alertDialogBuilder
										.setMessage(getString(R.string.login_se_entrar_com_usuario)
												+ user
												+ getString(R.string.login_apagara_dados_usuario)
												+ usuarioDispositivo
												+ getString(R.string.login_mesmo_assim_deseja_continuar));
								alertDialogBuilder.setPositiveButton(getString(R.string.login_sim),
										new DialogInterface.OnClickListener() {

											@Override
											public void onClick(
													DialogInterface dialog,
													int id) {
												// TODO Auto-generated method
												// stub
												dao.mudarUsuario();
												fazerLogin(usuarioLogin);

											}
										}).setNegativeButton(getString(R.string.login_nao),
										new DialogInterface.OnClickListener() {

											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {
												// TODO Auto-generated method
												// stub
												dialog.cancel();
											}
										});

								AlertDialog alertDialog = alertDialogBuilder
										.create();
								alertDialog.show();

								// Mudar a cor do bot�o
								((Button) alertDialog
										.findViewById(android.R.id.button2))
										.setBackgroundResource(R.color.botao_picker);
								((Button) alertDialog
										.findViewById(android.R.id.button1))
										.setBackgroundResource(R.color.botao_picker);
								((Button) alertDialog
										.findViewById(android.R.id.button1))
										.setTextColor(getResources().getColor(
												R.color.branco));
								((Button) alertDialog
										.findViewById(android.R.id.button2))
										.setTextColor(getResources().getColor(
												R.color.branco));
							}

						} else {

							fazerLogin(usuarioLogin);

						}

					} catch (Exception e) {
						e.printStackTrace();

					}

				}

			}

		});

		//

	}

	/*
	 * @Override public boolean onKeyDown(int keyCode, KeyEvent event) {
	 * if(event.getAction() == KeyEvent.ACTION_DOWN){ switch(keyCode) { case
	 * KeyEvent.KEYCODE_BACK: if(web.canGoBack()){ Intent irTelaLogin = new
	 * Intent(this, Login.class); startActivity(irTelaLogin); }else{ Intent
	 * irTelaLogin = new Intent(this, Login.class); startActivity(irTelaLogin);
	 * } return true; }
	 * 
	 * } return super.onKeyDown(keyCode, event); }
	 */
	/*
	 * private void onBack() { // TODO Auto-generated method stub Intent
	 * irTelaLogin = new Intent(this, Login.class); startActivity(irTelaLogin);
	 * }
	 */
	public void verificarAcesso(String acesso, List<Usuario> usuarioLogin) {

		// if (acesso.equals("T")) {

		Usuario usuario = null;
		SaleDAO dao = new SaleDAO(Login.this);
		dao.insereUsuario(usuarioLogin);
		// dao.close();

		SaleDAO daoNovo = new SaleDAO(this);

		resp = daoNovo.verificaUsuarioDispositivo(1);

		Boolean existe_cliente_no_dispositivo = daoNovo
				.existe_cliente_no_dispositivo();

		Boolean existe_categoria_no_dispositivo = daoNovo
				.existe_categoria_no_dispositivo();

		Boolean existe_produto_no_dispositivo = daoNovo
				.existe_produto_no_dispositivo();

		/*
		 * if (resp != null && existe_cliente_no_dispositivo == false &&
		 * existe_categoria_no_dispositivo == false &&
		 * existe_produto_no_dispositivo == false) {
		 * 
		 * Intent irTelaInicio = new Intent(Login.this,
		 * TelaInicioActivity.class); startActivity(irTelaInicio);
		 */
		if (existe_cliente_no_dispositivo == false
				&& existe_categoria_no_dispositivo == false
				&& existe_produto_no_dispositivo == false) {

			NegocioUtil negocioUtil = new NegocioUtil();
			negocioUtil.salvarDadosPadrao(this);
		}

		if (resp != null) {
			Intent irTelaInicial = new Intent(Login.this, MenuInicial.class);

			startActivity(irTelaInicial);
		}

	}

	/*
	 * @Override public void onBackPressed() { // TODO Auto-generated method
	 * stub Intent home = new Intent(Intent.ACTION_MAIN);
	 * home.addCategory(Intent.CATEGORY_HOME);
	 * home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); startActivity(home);
	 * 
	 * 
	 * }
	 */

	private void fazerLogin(ArrayList<Usuario> usuarioLogin) {
		// TODO Auto-generated method stub
		ConnectionDetector cn = new ConnectionDetector(Login.this);

		if (cn.isConnectingToInternet() == true) {

			LoginJson json = new LoginJson(Login.this, usuarioLogin);

			json.execute();

		} else {

			Toast.makeText(Login.this,
					"Habilite conexão de iternet ou wi-fi no seu celular!",
					Toast.LENGTH_LONG).show();
		}
	}

}
