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
import com.sale.modelo.CategoriaProduto;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

//USA O CONCEITO DE GENERICS
public class BaixarDadosCategoria extends AsyncTask<Integer, Double, String> {

	private Activity context;
	private ProgressDialog progress;

	public BaixarDadosCategoria(Activity context) {
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

		return resposta;

	}

	// }

	@Override
	// TUDO QUE EST� NO onPostExecute � executado pela thread da UI
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		progress.dismiss();// FAZENDO O PROGRESS PARAR NO FINAL DA CHAMADA
		Toast.makeText(context, result, Toast.LENGTH_LONG).show();

		// Baixar dados dos clientes
		BaixarDadosTask taskDados = new BaixarDadosTask(context);
		taskDados.execute();
	}

}
