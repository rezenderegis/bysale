package com.sale.cadastro;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import com.sale.cadastro.R;
import com.sale.dao.SaleDAO;
import com.sale.modelo.CategoriaProduto;
import com.sale.modelo.Pagamento;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class PagamentoActivity extends Activity implements OnClickListener {

	int layout;
	ListView formas_pagamento;
	private int forma_pagamento_informada;
	private Spinner forma_pagamento;
	private EditText subTotalPagamento, data_pagamento_informada;
	private Button data_pagamento, adicionar_tipo_pagamento;
	private int mYear, mMonth, mDay;
	// private ListView listView1;
	private EditText valor_pagamento;
	Double valorRestante;

	private FormularioPagamentoHelper formularioPagamentoHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.);
		setContentView(R.layout.forma_pagamento_inclusao);
		getActionBar().setTitle("Inclus�o de Pagamento");
		getActionBar().setDisplayHomeAsUpEnabled(true);
		formularioPagamentoHelper = new FormularioPagamentoHelper(this);

		forma_pagamento = (Spinner) findViewById(R.id.forma_pagamento);
		List<String> list = new ArrayList<String>();
		list.add(getText(R.string.lista_pagamentos_adapter_cartao_credito).toString());
		list.add(getText(R.string.lista_pagamentos_adapter_dinheiro).toString());
		list.add(getText(R.string.lista_pagamentos_adapter_cheque).toString());
		list.add(getText(R.string.lista_pagamentos_adapter_boleto).toString());

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item, list);

		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		forma_pagamento.setAdapter(dataAdapter);
		Intent intent = getIntent();
		Double valorRestante = (Double) intent
				.getSerializableExtra("calculoValorRestante");

		// Data do pagamento
		data_pagamento = (Button) findViewById(R.id.data_pagamento);
		data_pagamento_informada = (EditText) findViewById(R.id.data_pagamento_informada);
		valor_pagamento = (EditText) findViewById(R.id.valor_pagamento);
		valor_pagamento.addTextChangedListener(new TextWatcher() {

			private boolean isUpdating = false;
			// Pega a formatacao do sistema, se for brasil R$ se EUA US$
			private NumberFormat nf = NumberFormat.getCurrencyInstance();

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int after) {
				// Evita que o m�todo seja executado varias vezes.
				// Se tirar ele entre em loop
				if (isUpdating) {
					isUpdating = false;
					return;
				}

				isUpdating = true;
				String str = s.toString();
				// Verifica se j� existe a m�scara no texto.
				boolean hasMask = ((str.indexOf("R$") > -1 || str.indexOf("$") > -1) && (str
						.indexOf(".") > -1 || str.indexOf(",") > -1));
				// Verificamos se existe m�scara
				if (hasMask) {
					// Retiramos a m�scara.
					str = str.replaceAll("[R$]", "").replaceAll("[,]", "")
							.replaceAll("[.]", "");
				}

				try {
					// Transformamos o n�mero que est� escrito no EditText em
					// monet�rio.
					str = nf.format(Double.parseDouble(str) / 100);
					valor_pagamento.setText(str);
					valor_pagamento.setSelection(valor_pagamento.getText()
							.length());
				} catch (NumberFormatException e) {
					s = "";
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// N�o utilizamos
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}

		});
		subTotalPagamento = (EditText) findViewById(R.id.subTotalPagamento);

		// DecimalFormat precoFormatado = new DecimalFormat("####,###,###.00");
		final DecimalFormat precoFormatado = new DecimalFormat(
				"##,###,###,##0.00", new DecimalFormatSymbols(new Locale("pt",
						"BR")));

		String subTotalPagamentoFormatado = "0,00";
		if (valorRestante != 0.0) {
			subTotalPagamentoFormatado = precoFormatado.format(valorRestante);
		}

		subTotalPagamento.setText("R$ " + subTotalPagamentoFormatado);
		data_pagamento_informada.setOnClickListener(this);
		adicionar_tipo_pagamento = (Button) findViewById(R.id.adicionar_tipo_pagamento);
		subTotalPagamento.setEnabled(false);

		adicionar_tipo_pagamento.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Toast.makeText(PagamentoActivity.this,
				// valor_pagamento.getText().toString(),
				// Toast.LENGTH_LONG).show();

				Pagamento pagamentoSalvar = null;

				try {
					pagamentoSalvar = formularioPagamentoHelper
							.trazPagamentoFormulario(forma_pagamento_informada);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				Intent intent = getIntent();
				// A vari�vel valorRestanteMostrar � a mesma que est� declarada
				// no in�cio da classe.
				Double valorRestanteMostrar = (Double) intent
						.getSerializableExtra("calculoValorRestante");

				if (pagamentoSalvar.equals(null)) {
					Toast.makeText(PagamentoActivity.this,
							"Informe todos os dados do pagamento!",
							Toast.LENGTH_LONG).show();
				} else {
					if (pagamentoSalvar.getData_pagamento_informada() == null
							|| pagamentoSalvar.getValor_pagamento().toString() == "0.0") {

						Toast.makeText(PagamentoActivity.this,
								"Informe todos os dados!", Toast.LENGTH_LONG)
								.show();
					} else if (valorRestanteMostrar < pagamentoSalvar
							.getValor_pagamento()) {
						Toast.makeText(PagamentoActivity.this,
								"Valor informado maior que valor restante!",
								Toast.LENGTH_LONG).show();
					} else {

						Long idVendaCliente = (Long) intent
								.getSerializableExtra("idVendaCliente");

						SaleDAO dao = new SaleDAO(PagamentoActivity.this);
						dao.salvarPagamento(pagamentoSalvar, idVendaCliente);

						finish();
						
						/* Utilizar caso decida inflar o cabe�alho de pagamento n�o informado
						Intent irParaListaPagamentos = new Intent(PagamentoActivity.this, ListaPagamentos.class);
						irParaListaPagamentos.putExtra("idVendaCliente", idVendaCliente);
						*/
						
					}
				}
			}

		});

		forma_pagamento
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent, View v,
							int position, long id) {
						// TODO Auto-generated method stub
						forma_pagamento_informada = position;
						// Toast.makeText(PagamentoActivity.this,
						// "Clique"+position, Toast.LENGTH_LONG).show();
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});

		// Fechamento do bundle
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		int itemClicado = item.getItemId();

		if (itemClicado == android.R.id.home) {
			finish();
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {

		if (v == data_pagamento_informada) {

			// Process to get Current Date
			final Calendar c = Calendar.getInstance();
			mYear = c.get(Calendar.YEAR);
			mMonth = c.get(Calendar.MONTH);
			mDay = c.get(Calendar.DAY_OF_MONTH);

			// Launch Date Picker Dialog
			DatePickerDialog dpd = new DatePickerDialog(this,
					new DatePickerDialog.OnDateSetListener() {

						@Override
						public void onDateSet(DatePicker view, int year,
								int monthOfYear, int dayOfMonth) {
							// Display Selected date in textbox
							data_pagamento_informada.setText(dayOfMonth + "-"
									+ (monthOfYear + 1) + "-" + year);

						}
					}, mYear, mMonth, mDay);
			dpd.show();
		}

	}

}
