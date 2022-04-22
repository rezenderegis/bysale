package com.sale.cadastro.task;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sale.cadastro.ListaPessoas;
import com.sale.cadastro.TelaInicioActivity;
import com.sale.cadastro.util.ConnectionDetector;
import com.sale.cadastro.util.Constantes;
import com.sale.cadastro.util.PessoaConveter;
import com.sale.cadastro.util.WebClient;
import com.sale.dao.SaleDAO;
import com.sale.login.Usuario;
import com.sale.modelo.CategoriaProduto;
import com.sale.modelo.Pessoa;
import com.sale.modelo.Produto;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

//USA O CONCEITO DE GENERICS
public class BaixarDadosUsuarioServidor extends
		AsyncTask<Integer, Double, String> {

	private Activity context;
	private ProgressDialog progress;

	public BaixarDadosUsuarioServidor(Activity context) {
		// TODO Auto-generated constructor stub
		this.context = context;

	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		// mostrar um componente visual

		// O primeiro true informa que pode ser enviado por tempo indeterminado
		// e o segundo, que pode ser cancelado.
		progress = ProgressDialog.show(context, "Aguarde", "Sincronizando",
				true, true);

	}

	@Override
	protected String doInBackground(Integer... params) {
		// TODO Auto-generated method stub
		// O TIPO MAIS IMPORTANTE � A STRING, QUE �O RETORNO DA TAREFA PESADA

		String resposta = null;

		// salvarClientesDoSite();

		// salvarProdutosDoSite();

		// salvarCategoriasDoSite();

		salvarDadosUsuarioDoServidor();

		return resposta;

	}

	// }
	private void salvarDadosUsuarioDoServidor() {
		// TODO Auto-generated method stub

		ConnectionDetector detectaConexao = new ConnectionDetector(null);

		Boolean servidorAtivo = detectaConexao.isConnectedToServer();
		// Log.i("Situa��o do servidor", "situacao"+servidorAtivo);

		if (servidorAtivo == true) { // VERIFICA SE O SERVIDOR EST� DISPON�VEL
										// ANTES DE SINCRONIZAR

			String urlServer = Constantes.ENDERECO
					+ "/mysale/app/sincronizar_produtos.php";

			// String urlServer = "http://192.168.0.9/mysale/app/android.php";
			SaleDAO dao = new SaleDAO(context);
			List<Usuario> usuario = dao.getDadosUsuario();
			String tipo = "busca_usuario";

			String dadosUsuario = new PessoaConveter().toJsonUsuario(usuario,
					tipo);
			WebClient usuarioPost = new WebClient(urlServer);

			final String respostaJSONVendaCliente = usuarioPost
					.post(dadosUsuario);
			String resposta = null;
			String usuario_master = null;
			String sincronizacaoentradaaplicativo = null;
			Long idusuario = null;
			try {
				String result = respostaJSONVendaCliente;
				// CONVERT RESPONSE STRING TO JSON ARRAY
				// JSONObject jsonObject = new JSONObject(result);

				// JSONArray ja = new JSONArray(result);

				JSONObject obj = new JSONObject(result);

				// PRODUTOS
				JSONArray jsonArray = obj.getJSONArray("usuario");

				int length = jsonArray.length();
				String[] cities = new String[length];

				if (length > 0) {
					for (int i = 0; i < length; i++) {

						JSONObject oneObject = jsonArray.getJSONObject(i);
						sincronizacaoentradaaplicativo = oneObject
								.getString("sincronizacaoentradaaplicativo");
						idusuario = Long.parseLong(oneObject
								.getString("idusuario"));
						usuario_master = oneObject.getString("usuario_master");
						cities[i] = jsonArray.getString(i);

					}
				}

				Usuario usuarioAtualisar = new Usuario();

				for (int i = 0; i < cities.length; i++) {

					// CategoriaProduto categoriaProduto = new
					// CategoriaProduto();
					JSONObject oneObject = jsonArray.getJSONObject(i);
					// categoriaProduto.setIdCategoria(Long.parseLong(oneObject.getString("idcategoriaproduto")));
					// categoriaProduto.setNomeCategoria(oneObject.getString("nomecategoriaproduto"));

					// usuarioAtualisar.(oneObject.getString("sincronizacaoentradaaplicativo"));
					// usuarioAtualisar.add(oneObject.getString("sincronizacaoentradaaplicativo"));
					usuarioAtualisar
							.setSincronizacaoEntradaAplicativo(oneObject
									.getString("sincronizacaoentradaaplicativo"));
					usuarioAtualisar.setIdusuario(Long.parseLong(oneObject
							.getString("idusuario")));
					usuarioAtualisar.setUsuario_master(oneObject
							.getString("usuario_master"));
				}

				dao.atualizarUsuario(usuarioAtualisar);

				dao.gravarSincronizacaoDataAtual();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	private void salvarCategoriasDoSite() {
		// TODO Auto-generated method stub

		String urlServer = Constantes.ENDERECO
				+ "/mysale/app/sincronizar_produtos.php";

		// String urlServer = "http://192.168.0.9/mysale/app/android.php";
		SaleDAO dao = new SaleDAO(context);
		List<Usuario> usuario = dao.getDadosUsuario();
		String tipo = "categoria";

		String dadosUsuario = new PessoaConveter().toJsonUsuario(usuario, tipo);
		WebClient usuarioPost = new WebClient(urlServer);

		final String respostaJSONVendaCliente = usuarioPost.post(dadosUsuario);
		String resposta = null;
		String idcategoriaproduto = null;
		String nomecategoriaproduto = null;
		try {
			String result = respostaJSONVendaCliente;
			// CONVERT RESPONSE STRING TO JSON ARRAY
			// JSONObject jsonObject = new JSONObject(result);

			// JSONArray ja = new JSONArray(result);

			JSONObject obj = new JSONObject(result);

			// PRODUTOS
			JSONArray jsonArray = obj.getJSONArray("categoria");

			int length = jsonArray.length();
			String[] cities = new String[length];

			if (length > 0) {
				for (int i = 0; i < length; i++) {

					JSONObject oneObject = jsonArray.getJSONObject(i);
					idcategoriaproduto = oneObject
							.getString("idcategoriaproduto");
					nomecategoriaproduto = oneObject
							.getString("nomecategoriaproduto");

					cities[i] = jsonArray.getString(i);

				}
			}

			List<CategoriaProduto> categorias = new ArrayList<CategoriaProduto>();

			for (int i = 0; i < cities.length; i++) {

				// CategoriaProduto categoriaProduto = new CategoriaProduto();
				JSONObject oneObject = jsonArray.getJSONObject(i);
				// categoriaProduto.setIdCategoria(Long.parseLong(oneObject.getString("idcategoriaproduto")));
				// categoriaProduto.setNomeCategoria(oneObject.getString("nomecategoriaproduto"));

				categorias.add(new CategoriaProduto(oneObject
						.getString("idcategoriaproduto"), oneObject
						.getString("nomecategoriaproduto")));

			}

			dao.salvaCategoriaProdutoDoSite(categorias);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void salvarClientesDoSite() {
		// TODO Auto-generated method stub

		String urlServer = Constantes.ENDERECO
				+ "/mysale/app/sincronizar_produtos.php";
		// String urlServer = "http://192.168.0.9/mysale/app/android.php";
		SaleDAO dao = new SaleDAO(context);
		List<Usuario> usuario = dao.getDadosUsuario();
		String tipo = "cliente";
		String dadosUsuario = new PessoaConveter().toJsonUsuario(usuario, tipo);
		WebClient usuarioPost = new WebClient(urlServer);

		final String respostaJSONVendaCliente = usuarioPost.post(dadosUsuario);
		String resposta = null;
		String nome = null;
		String idpessoa = null;
		String email = null;
		String endereco = null;
		String telefone = null;
		String site = null;
		try {
			String result = respostaJSONVendaCliente;
			// CONVERT RESPONSE STRING TO JSON ARRAY
			// JSONObject jsonObject = new JSONObject(result);

			JSONObject obj = new JSONObject(result);

			// PRODUTOS
			JSONArray jsonArray = obj.getJSONArray("produtos");

			int length = jsonArray.length();
			String[] cities = new String[length];

			if (length > 0) {
				for (int i = 0; i < length; i++) {

					JSONObject oneObject = jsonArray.getJSONObject(i);
					idpessoa = oneObject.getString("idpessoa");
					nome = oneObject.getString("nome");
					email = oneObject.getString("email");
					endereco = oneObject.getString("endereco");
					telefone = oneObject.getString("telefone");
					site = oneObject.getString("site");

					cities[i] = jsonArray.getString(i);

				}
			}

			List<Pessoa> pessoas = new ArrayList<Pessoa>();

			for (int i = 0; i < cities.length; i++) {

				Pessoa pessoa = new Pessoa();
				JSONObject oneObject = jsonArray.getJSONObject(i);
				pessoa.setId(oneObject.getString("idpessoa"));
				pessoa.setNome(oneObject.getString("nome"));
				pessoa.setEmail(oneObject.getString("email"));
				pessoa.setEndereco(oneObject.getString("endereco"));
				pessoa.setTelefone(oneObject.getString("telefone"));
				pessoa.setSite(oneObject.getString("site"));

				pessoas.add(pessoa);

			}

			dao.salvaPessoasDoSite(pessoas);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void salvarProdutosDoSite() {

		// O TIPO MAIS IMPORTANTE � A STRING, QUE �O RETORNO DA TAREFA PESADA

		// ENVIAR OS ALUNOS ATRAVPES DA CLASSE QUE FAZ REQUISI��O HTTP

		// FAZER A CONVERS�O DO ALUNO EM JSON PARA OBTER A RESPOSTA E IMPRIMIR
		// NA TELA
		// String urlServer = "http://www.caelum.com.br/mobile";

		String urlServer = Constantes.ENDERECO
				+ "/mysale/app/sincronizar_produtos.php";

		// String urlServer = "http://192.168.0.9/mysale/app/android.php";
		SaleDAO dao = new SaleDAO(context);
		List<Usuario> usuario = dao.getDadosUsuario();
		String tipo = "produto";

		String dadosUsuario = new PessoaConveter().toJsonUsuario(usuario, tipo);
		WebClient usuarioPost = new WebClient(urlServer);

		final String respostaJSONVendaCliente = usuarioPost.post(dadosUsuario);
		String resposta = null;
		String idusuario = null;
		String nomeproduto = null;
		String preco = null;
		String categoria = null;
		String id = null;
		try {
			String result = respostaJSONVendaCliente;
			// CONVERT RESPONSE STRING TO JSON ARRAY
			// JSONObject jsonObject = new JSONObject(result);

			// JSONArray ja = new JSONArray(result);

			JSONObject obj = new JSONObject(result);

			// PRODUTOS
			JSONArray jsonArray = obj.getJSONArray("produtos");

			int length = jsonArray.length();
			String[] cities = new String[length];

			if (length > 0) {
				for (int i = 0; i < length; i++) {

					JSONObject oneObject = jsonArray.getJSONObject(i);
					id = oneObject.getString("id");
					nomeproduto = oneObject.getString("nomeproduto");
					preco = oneObject.getString("preco");
					categoria = oneObject.getString("categoria");

					cities[i] = jsonArray.getString(i);

				}
			}

			List<Produto> produtos = new ArrayList<Produto>();

			for (int i = 0; i < cities.length; i++) {

				Produto produto = new Produto();
				JSONObject oneObject = jsonArray.getJSONObject(i);
				produto.setId(oneObject.getString("id"));
				produto.setNomeProduto(oneObject.getString("nomeproduto"));
				produto.setPreco(Double.parseDouble(oneObject
						.getString("preco")));
				produto.setCategoria(oneObject
						.getString("categoria"));

				produtos.add(produto);

			}

			dao.salvaProdutoDoSite(produtos);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	// TUDO QUE EST� NO onPostExecute � executado pela thread da UI
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		progress.dismiss();// FAZENDO O PROGRESS PARAR NO FINAL DA CHAMADA

		// context.startActivity(new Intent(context, TelaInicioActivity.class));

		/*
		 * SaleDAO daoNovo = new SaleDAO(context);
		 * 
		 * Boolean existe_cliente_no_dispositivo =
		 * daoNovo.existe_cliente_no_dispositivo();
		 * 
		 * Boolean existe_categoria_no_dispositivo =
		 * daoNovo.existe_categoria_no_dispositivo();
		 * 
		 * Boolean existe_produto_no_dispositivo =
		 * daoNovo.existe_produto_no_dispositivo();
		 * 
		 * if (existe_cliente_no_dispositivo == false &&
		 * existe_categoria_no_dispositivo == false &&
		 * existe_produto_no_dispositivo == false) { context.startActivity(new
		 * Intent(context, TelaInicioActivity.class));
		 * 
		 * } else { context.startActivity(new Intent(context,
		 * ListaPessoas.class));
		 * 
		 * } Toast.makeText(context, result, Toast.LENGTH_LONG).show();
		 * 
		 * //Baixar dados das categorias
		 */
	}

}
