package cadastro_usuario;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.sale.cadastro.R;
import com.sale.cadastro.FormularioProdutoActivity;
import com.sale.cadastro.ListaPessoas;
import com.sale.cadastro.TelaInicioActivity;
import com.sale.cadastro.util.Botao;
import com.sale.cadastro.util.ConnectionDetector;
import com.sale.cadastro.util.Constantes;
import com.sale.cadastro.util.MenuInicial;
import com.sale.dao.SaleDAO;
import com.sale.login.Login;
import com.sale.login.LoginJson;
import com.sale.login.TelaInicial;
import com.sale.login.Usuario;

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
public class CadastroUsuario extends Activity {
	WebView web;
	EditText nome_usuario, email_usuario, senha_usuario, senha_usuario_confirmacao, email_usuario_confirmacao;
	TextView error;
	Button salvar_usuario;
	Button cadastrar;
	private String resp;
	private String errorMsg;

	SaleDAO dao;
	ArrayList<Usuario> usuarioLogin;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cadastrar_usuario);

		nome_usuario = (EditText) findViewById(R.id.nome_usuario);
		email_usuario = (EditText) findViewById(R.id.email_usuario);
		senha_usuario = (EditText) findViewById(R.id.senha_usuario);
		salvar_usuario = (Button) findViewById(R.id.salvar_usuario);
		senha_usuario_confirmacao = (EditText) findViewById(R.id.senha_usuario_confirmacao);
		email_usuario_confirmacao = (EditText) findViewById(R.id.email_usuario_confirmacao);
	
		salvar_usuario.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				SaleDAO dao = new SaleDAO(CadastroUsuario.this);
				final String usuarioDispositivo = dao
						.verificaUsuarioDispositivo(2); // VERIFICA SE HÁ
														// USUÁRIO LOGADO
				dao.close();
				if (usuarioDispositivo != null) {
					Toast.makeText(
							CadastroUsuario.this,
							"Há um usuário logado no dispositivo. Entre no aplicativo e clique no menu Trocar de Usuário antes de cadastrar um novo.",
							Toast.LENGTH_LONG).show();
				} else {

					if (nome_usuario.getText().toString().isEmpty()
							|| email_usuario.getText().toString().isEmpty()
							|| senha_usuario.getText().toString().isEmpty()
							|| senha_usuario_confirmacao.getText().toString().isEmpty()
							|| email_usuario_confirmacao.getText().toString().isEmpty()) {
						Toast.makeText(CadastroUsuario.this,
								"Informe todos os dados!", Toast.LENGTH_SHORT)
								.show();

					} else if (!email_usuario.getText().toString().equals(email_usuario_confirmacao.getText().toString())) {
						Toast.makeText(CadastroUsuario.this, "E-mails digitados não conferem!", Toast.LENGTH_LONG).show();
					} else if (!senha_usuario.getText().toString().equals(senha_usuario_confirmacao.getText().toString())) {
						Toast.makeText(CadastroUsuario.this, "Senhas digitadas não conferem!", Toast.LENGTH_LONG).show();

					}else {

						ArrayList<NameValuePair> dados_enviar = new ArrayList<NameValuePair>();

						dados_enviar.add(new BasicNameValuePair("nome_usuario",
								nome_usuario.getText().toString()));
						dados_enviar.add(new BasicNameValuePair(
								"email_usuario", email_usuario.getText()
										.toString()));
						dados_enviar.add(new BasicNameValuePair(
								"senha_usuario", senha_usuario.getText()
										.toString()));

						String nome_depois = nome_usuario.getText().toString();
						String email_depois = email_usuario.getText()
								.toString();
						String senha_depois = senha_usuario.getText()
								.toString();

						usuarioLogin = new ArrayList<Usuario>();

						Usuario usuario_cadastrar = new Usuario();
						usuario_cadastrar.setNome(nome_depois);
						usuario_cadastrar.setEmail(email_depois);
						usuario_cadastrar.setPassword(senha_depois);

						usuarioLogin.add(usuario_cadastrar);

						ConnectionDetector cn = new ConnectionDetector(
								CadastroUsuario.this);

						if (cn.isConnectingToInternet() == true) {

							CadastraUsuarioJson json = new CadastraUsuarioJson(
									CadastroUsuario.this, usuarioLogin);

							json.execute();

						} else {

							Toast.makeText(
									CadastroUsuario.this,
									"Habilite conexão de iternet ou wi-fi no seu celular!",
									Toast.LENGTH_LONG).show();
						}

					}
				}
			}
		});

	}

	public void verificarAcesso(String acesso, List<Usuario> usuarioLogin) {

		// if (acesso.equals("T")) {

		Usuario usuario = null;
		SaleDAO dao = new SaleDAO(CadastroUsuario.this);
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

		if (resp != null && existe_cliente_no_dispositivo == false
				&& existe_categoria_no_dispositivo == false
				&& existe_produto_no_dispositivo == false) {

			Intent irTelaInicio = new Intent(CadastroUsuario.this,
					TelaInicioActivity.class);
			startActivity(irTelaInicio);
		} else if (resp != null && existe_cliente_no_dispositivo == true
				&& existe_categoria_no_dispositivo == true
				&& existe_produto_no_dispositivo == true) {
			Intent irTelaInicial = new Intent(CadastroUsuario.this,
					MenuInicial.class);

			startActivity(irTelaInicial);
		}

	}

}
