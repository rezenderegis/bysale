package com.sale.cadastro.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class WebClient {
	// A CLASSE WEBCLIENT SÓ DEVE ENVIAR DADOS PARA O SERVIDOR
	private String urlServer;

	public WebClient(String urlServer) {

		this.urlServer = urlServer;
	}

	public String post(String dadosJSON) {
		try { // TOTO CONTEUDO DE TENTAR CONECTAR COM O SERVER DENTRO DO TRY
			HttpClient client = new DefaultHttpClient();

			HttpPost post = new HttpPost(urlServer);

			post.setEntity(new StringEntity(dadosJSON)); // ENVIAR OS DADOS

			// AVIAR AO SERVIDOR QUE O FORMATO É JSON
			post.setHeader("content-type", "application/json");
			// post.setHeader("json", dadosJSON.toString());
			// AVISAR AO SERVIDOR QUE A RESPOSTA TABÉM DEVE SER JSON
			post.setHeader("Accept", "application/json");

			HttpResponse response = client.execute(post);

			int status = response.getStatusLine().getStatusCode();

			HttpEntity resposta = response.getEntity();

			String respostaJSON = EntityUtils.toString(resposta);

			return respostaJSON;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			// FAZER UM TRATAMENTO MAIS SIMPLES DE EXCEÇÃO E DEIXAR PROSSEGUIR.
			// DEPOIS DEVO MELHORAR
			throw new RuntimeException();
		}

	}

}
