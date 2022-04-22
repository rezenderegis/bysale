package com.sale.cadastro;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.sale.cadastro.R;
import com.sale.cadastro.util.Tools;
import com.sale.modelo.Pagamento;

import android.widget.EditText;
import android.widget.TextView;

public class BuscaRelatorioPagamentoHelper {

	private EditText valor_pagamento;
	private TextView data_pagamento_informada;
	private Pagamento pagamento;

	public BuscaRelatorioPagamentoHelper(BuscaRelatorioPagamentosActivity buscaRelatorioActivity) {
		// TODO Auto-generated constructor stub

		data_pagamento_informada = (TextView) buscaRelatorioActivity
				.findViewById(R.id.data_pagamento_informada);
		pagamento = new Pagamento();
	}

	public Pagamento trazPagamentoFormulario(int forma_pagamento_informada)
			throws ParseException {

		String valor_pagamento_com_virgula_original = valor_pagamento.getText()
				.toString();

		String valor_pagamento_original = valor_pagamento_com_virgula_original
				.replace("R$", "");

		String data_pagamento = data_pagamento_informada.getText().toString();
		// SimpleDateFormat dateFormat = new
		// SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		// SimpleDateFormat formato_antigo_n = new
		// SimpleDateFormat(formato_antigo);
		Tools tools = new Tools();

		if (valor_pagamento_original.isEmpty()) {
			valor_pagamento_original = "0.0";
		}
		// Long categoria = Long.parseLong(editCategoria.getText().toString());
		// Long categoria = Long.parseLong(editCategoria.getText().toString());
		Double valor_pagamento_formatado = tools
				.retornaValorDecimal(valor_pagamento_original);

		// pagamento.setData_pagamento_informada();
		pagamento.setData_pagamento_informada(tools
				.retorna_data_a_partir_string(data_pagamento));

		pagamento.setValor_pagamento(valor_pagamento_formatado);
		pagamento.setForma_pagamento_informada(forma_pagamento_informada);
		return pagamento;

	}

}
