package com.sale.cadastro.task;

import java.util.List;

import com.sale.cadastro.util.ConnectionDetector;
import com.sale.cadastro.util.Constantes;
import com.sale.cadastro.util.PessoaConveter;
import com.sale.cadastro.util.WebClient;
import com.sale.dao.SaleDAO;
import com.sale.modelo.ControleSincronizacao;
import com.sale.modelo.VendaCliente;
import com.sale.modelo.Vendas;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

//20-05 FAZER COM QUE SINCRONIZE SÓ O CLIENTE QUE FOI CLICADO. PAREI NA PARTE DE BUSCA DA VENDA DESSE CLIENTE, MAS AINDA NÃO ESTÁ BUSCANDO A VENDA CORRETA.
//USA O CONCEITO DE GENERICS
public class EnviaVendaTask extends AsyncTask<Integer, Double, String> {

	private Activity context;
	private ProgressDialog progress;
	private List<ControleSincronizacao> idVendaclienteSincronizar;
	private String tipo_venda_sincronizar;

	public EnviaVendaTask(Activity context,
			List<ControleSincronizacao> idVendaclienteSincronizar2,
			String tipo_venda_sincronizar) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.idVendaclienteSincronizar = idVendaclienteSincronizar2;
		this.tipo_venda_sincronizar = tipo_venda_sincronizar;
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

		ConnectionDetector detectaConexao = new ConnectionDetector(null);

		Boolean servidorAtivo = detectaConexao.isConnectedToServer();
		// Log.i("Situação do servidor", "situacao"+servidorAtivo);

		if (servidorAtivo == true) { // VERIFICA SE O SERVIDOR ESTÁ DISPONÍVEL
										// ANTES DE SINCRONIZAR
			String urlServer = Constantes.ENDERECO + "/mysale/app/android.php";

			// Para enviar os dados devemos escolher um formato comum, nesse
			// caso o JSON (Java Script Object Notation)
			WebClient client = new WebClient(urlServer);
			// {chave : valor} FORMATO DE JSON ARRAY DE ALUNOS { "list": [{
			// "aluno": [{"nome": "mara","nota": 9},{"nome": "maria", "nota":
			// 7}] }]}
			// CLASSE QUE RECEBE UMA LISTA DE ALUNOS
			SaleDAO dao = new SaleDAO(context);

			// VENDACLIENTE
			Long idvendaclienteParametro = null; // POR PADRÃO SERÁ NULO. O
													// PARÂMETRO É UTILIZADO EM
													// OUTRAS LISTAS

			List<VendaCliente> vendaCliente = dao
					.getListaVendaClienteSincronizar(idvendaclienteParametro,
							idVendaclienteSincronizar, tipo_venda_sincronizar);
			String dadosVendaClienteJson = new PessoaConveter()
					.toJsonVendaCliente(vendaCliente, context);
			WebClient clientvendaCliente = new WebClient(urlServer);

			final String respostaJSONVendaCliente = clientvendaCliente
					.post(dadosVendaClienteJson);

			// VENDAS
			List<Vendas> venda = dao
					.getListaVendasEnviarSiteSincronizar(idVendaclienteSincronizar);
			String dadosVendas = new PessoaConveter().toJsonVenda(venda,
					context);
			WebClient clientVenda = new WebClient(urlServer);
			final String respostaVenda = clientVenda.post(dadosVendas);

			dao.deletarControle();
			dao.close();

			return respostaVenda.toString();
		} else {

			return "Servidor Indisponivel";
		}
	}

	@Override
	// TUDO QUE ESTÁ NO onPostExecute é executado pela thread da UI
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		progress.dismiss();// FAZENDO O PROGRESS PARAR NO FINAL DA CHAMADA
		Toast.makeText(context, result, Toast.LENGTH_LONG).show();

	}

}
