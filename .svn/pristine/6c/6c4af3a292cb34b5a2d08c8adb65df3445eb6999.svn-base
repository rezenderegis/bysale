package com.sale.cadastro.task;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sale.cadastro.util.Constantes;
import com.sale.cadastro.util.PessoaConveter;
import com.sale.cadastro.util.WebClient;
import com.sale.dao.SaleDAO;
import com.sale.login.Usuario;
import com.sale.modelo.Produto;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

//USA O CONCEITO DE GENERICS
public class BaixarDadosTask extends AsyncTask<Integer, Double, String> {

	private Activity context;
	private ProgressDialog progress;

	public BaixarDadosTask(Activity context) {
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

		return resposta;

	}

	@Override
	// TUDO QUE EST� NO onPostExecute � executado pela thread da UI
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		progress.dismiss();// FAZENDO O PROGRESS PARAR NO FINAL DA CHAMADA
		Toast.makeText(context, result, Toast.LENGTH_LONG).show();

	}

}
