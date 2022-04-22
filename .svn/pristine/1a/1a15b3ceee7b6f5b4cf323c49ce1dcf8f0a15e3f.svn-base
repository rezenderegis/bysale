package com.sale.cadastro.task;

import java.util.List;

import com.sale.cadastro.util.Constantes;
import com.sale.cadastro.util.PessoaConveter;
import com.sale.cadastro.util.WebClient;
import com.sale.dao.SaleDAO;
import com.sale.modelo.CategoriaProduto;
import com.sale.modelo.Pessoa;
import com.sale.modelo.Produto;
import com.sale.modelo.VendaCliente;
import com.sale.modelo.Vendas;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

//USA O CONCEITO DE GENERICS
public class EnviaCadastrosServidorTask extends
		AsyncTask<Integer, Double, String> {

	private Activity context;
	private ProgressDialog progress;

	public EnviaCadastrosServidorTask(Activity context) {
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
		// O TIPO MAIS IMPORTANTE É A STRING, QUE ´O RETORNO DA TAREFA PESADA

		// ENVIAR OS ALUNOS ATRAVPES DA CLASSE QUE FAZ REQUISIÇÃO HTTP

		// FAZER A CONVERSÃO DO ALUNO EM JSON PARA OBTER A RESPOSTA E IMPRIMIR
		// NA TELA
		// String urlServer = "http://www.caelum.com.br/mobile";

		String urlServer = Constantes.ENDERECO + "/mysale/app/android.php";

		// Para enviar os dados devemos escolher um formato comum, nesse caso o
		// JSON (Java Script Object Notation)
		WebClient client = new WebClient(urlServer);
		// {chave : valor} FORMATO DE JSON ARRAY DE ALUNOS { "list": [{ "aluno":
		// [{"nome": "mara","nota": 9},{"nome": "maria", "nota": 7}] }]}
		// CLASSE QUE RECEBE UMA LISTA DE ALUNOS
		SaleDAO dao = new SaleDAO(context);

		// ENVIAR CLIENTES
		List<Pessoa> pessoas = dao.getLista();
		String dadosJson = new PessoaConveter().toJson(pessoas, context);
		final String respostaJSON = client.post(dadosJson);

		// ENVIAR PRODUTOS

		List<Produto> produto = dao.getListaProduto();
		String dadosProdutoJson = new PessoaConveter().toJsonProduto(produto,
				context);
		WebClient clientProduto = new WebClient(urlServer);
		final String respostaJSONProduto = clientProduto.post(dadosProdutoJson);

		// ENVIAR CATEGORIAS
		List<CategoriaProduto> categoriaProduto = dao
				.getListaCategoriaProduto();
		String dadosCategoriaJson = new PessoaConveter()
				.toJsonCategoriaProduto(categoriaProduto, context);
		WebClient clientCategoriaProduto = new WebClient(urlServer);
		final String respostaJSONCategoriaProduto = clientCategoriaProduto
				.post(dadosCategoriaJson);

		// VENDACLIENTE
		Long idvendaclienteParametro = null; // POR PADRÃO SERÁ NULO. O
												// PARÂMETRO É UTILIZADO EM
												// OUTRAS LISTAS
		List<VendaCliente> vendaCliente = dao.getListaVendaCliente(
				idvendaclienteParametro, null);
		String dadosVendaClienteJson = new PessoaConveter().toJsonVendaCliente(
				vendaCliente, context);
		WebClient clientvendaCliente = new WebClient(urlServer);

		final String respostaJSONVendaCliente = clientvendaCliente
				.post(dadosVendaClienteJson);

		// VENDAS
		List<Vendas> venda = dao.getListaVendasEnviarSite(null);
		String dadosVendas = new PessoaConveter().toJsonVenda(venda, context);
		WebClient clientVenda = new WebClient(urlServer);
		final String respostaVenda = clientVenda.post(dadosVendas);

		dao.close();
		/*
		 * if (respostaJSONProduto != null) {
		 * 
		 * return respostaJSONProduto.toString();
		 * 
		 * } else
		 */
		if (respostaVenda != null) {

			return respostaVenda.toString();

		} else

		// if (respostaJSONVendaCliente != null)
		{

			return respostaJSONVendaCliente.toString();
		}

		/*
		 * else {
		 * 
		 * return respostaJSON.toString(); }
		 */

	}

	@Override
	// TUDO QUE ESTÁ NO onPostExecute é executado pela thread da UI
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		progress.dismiss();// FAZENDO O PROGRESS PARAR NO FINAL DA CHAMADA
		Toast.makeText(context, result, Toast.LENGTH_LONG).show();

	}

}
