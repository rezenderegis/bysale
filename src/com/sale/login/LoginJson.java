package com.sale.login;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.sale.cadastro.task.BaixarDadosServidor;
import com.sale.cadastro.task.BaixarDadosTask;
import com.sale.cadastro.util.ConnectionDetector;
import com.sale.cadastro.util.Constantes;
import com.sale.cadastro.util.PessoaConveter;
import com.sale.cadastro.util.WebClient;

//USA O CONCEITO DE GENERICS
public class LoginJson extends AsyncTask<Integer, Double, String> {
	private static final String TAG_QUERY = "query";

	private Activity context;
	private ProgressDialog progress;
	List<Usuario> usuarioLogin;

	public LoginJson(Activity context, List<Usuario> usuario) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.usuarioLogin = usuario;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		// mostrar um componente visual

		// O primeiro true informa que pode ser enviado por tempo indeterminado
		// e o segundo, que pode ser cancelado.
		progress = ProgressDialog.show(context, "Aguarde", "Verificando Dados",
				true, true);

	}

	@Override
	protected String doInBackground(Integer... params) {
		// TODO Auto-generated method stub

		ConnectionDetector detectaConexao = new ConnectionDetector(null);

		Boolean servidorAtivo = detectaConexao.isConnectedToServer();

		if (servidorAtivo == true) { // VERIFICA SE O SERVIDOR EST� DISPON�VEL
										// ANTES DE SINCRONIZAR

			String urlServer = Constantes.ENDERECO + "/mysale/app/login.php";

			// USUARIO

			List<Usuario> usuario = usuarioLogin;
			String dadosUsuario = new PessoaConveter().toJsonUsuario(
					usuarioLogin, null);
			WebClient usuarioPost = new WebClient(urlServer);

			final String respostaJSONVendaCliente = usuarioPost
					.post(dadosUsuario);
			String resposta = null;
			String idusuario = null;
			String empresa = null;
			String acesso = null;
			String tipoContaEmpresa = null;
			String tipoEmpresa = null;
			String usuario_master = null;
			String sincronizacaoRegistroProduto = null;
			String sincronizacaoFechamentoVenda = null;
			String sincronizacaoEntradaAplicativo = null;
			String fluxo_aprovacao_cadastro = null;
			
			try {
				String result = respostaJSONVendaCliente;
				// CONVERT RESPONSE STRING TO JSON ARRAY
				// JSONObject jsonObject = new JSONObject(result);

				// JSONArray ja = new JSONArray(result);

				JSONObject obj = new JSONObject(result);

				JSONArray jsonArray = obj.getJSONArray("userdetails");

				int length = jsonArray.length();
				String[] cities = new String[length];

				if (length > 0) {
					for (int i = 0; i < length; i++) {

						JSONObject oneObject = jsonArray.getJSONObject(i);
						String oneObjectsid = oneObject.getString("acesso");
						empresa = oneObject.getString("idempresa");
						idusuario = oneObject.getString("idusuario");
						acesso = oneObject.getString("acesso");
						tipoContaEmpresa = oneObject
								.getString("tipoContaEmpresa");
						tipoEmpresa = oneObject.getString("tipoEmpresa");
						usuario_master = oneObject.getString("usuario_master");
						sincronizacaoRegistroProduto = oneObject
								.getString("sincronizacaoRegistroProduto");
						sincronizacaoFechamentoVenda = oneObject
								.getString("sincronizacaoFechamentoVenda");
						sincronizacaoEntradaAplicativo = oneObject
								.getString("sincronizacaoEntradaAplicativo");
						fluxo_aprovacao_cadastro = oneObject.getString("fluxo_aprovacao_cadastro");
						cities[i] = jsonArray.getString(i);

					}
				}

				for (int i = 0; i < cities.length; i++) {
					Log.d("JSON parsing ", cities[i]);
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			for (Usuario usuariosAtualizacao : usuarioLogin) {
				usuariosAtualizacao.setIdempresa(Long.parseLong(empresa));
				usuariosAtualizacao.setIdusuario(Long.parseLong(idusuario));
				usuariosAtualizacao.setAcesso(acesso);
				usuariosAtualizacao.setTipoContaEmpresa(Long
						.parseLong(tipoContaEmpresa));
				usuariosAtualizacao.setTipoEmpresa(Long.parseLong(tipoEmpresa));
				usuariosAtualizacao.setUsuario_master(usuario_master);
				usuariosAtualizacao
						.setSincronizacaoRegistroProduto(sincronizacaoRegistroProduto);
				usuariosAtualizacao
						.setSincronizacaoFechamentoVenda(sincronizacaoFechamentoVenda);
				usuariosAtualizacao
						.setSincronizacaoEntradaAplicativo(sincronizacaoEntradaAplicativo);
				usuariosAtualizacao.setFluxo_aprovacao_cadastro(fluxo_aprovacao_cadastro);

			}

			return resposta;
		} else { // RETORNA NULO SE N�O CONSEGUIR COMUNICA��O COM O SERVIDOR. S�
					// SER� EMITIDA A MENSAGEM NO POST EXECUTE

			return null;
		}
	}

	@Override
	// TUDO QUE EST� NO onPostExecute � executado pela thread da UI�
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		progress.dismiss();// FAZENDO O PROGRESS PARAR NO FINAL DA CHAMADA
		String acesso = null;
		for (Usuario usuariosAtualizacao : usuarioLogin) {
			acesso = usuariosAtualizacao.getAcesso();
			// usuariosAtualizacao.ge

		}
		if (acesso != null) { // O acesso vem preenchido com o usu�rio se tiver
								// havido comunica��o com o banco. Caso
								// contr�rio, vem nulo.
			if (acesso.equals("T")) {

				Toast.makeText(context, "Login realizado com sucesso!",
						Toast.LENGTH_LONG).show();
				((Login) context).verificarAcesso(acesso, usuarioLogin);

				BaixarDadosServidor taskDadosClientes = new BaixarDadosServidor(
						context);
				taskDadosClientes.execute();

			} else {

				Toast.makeText(context, "Usuário ou senha inválidos!",
						Toast.LENGTH_LONG).show();
			}

		} else {
			Toast.makeText(
					context,
					"Servidor indisponivel! Tente novamente após alguns minutos!",
					Toast.LENGTH_LONG).show();
		}
	}

}
