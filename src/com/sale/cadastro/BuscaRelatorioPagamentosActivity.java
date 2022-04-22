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
import com.sale.relatorios.Relatorio2;
import com.sale.relatorios.RelatorioPagamentos;

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
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class BuscaRelatorioPagamentosActivity extends Activity implements OnClickListener {

	int layout;
	ListView formas_pagamento;
	private EditText data_inicial, data_final;

	private Button 
			botao_buscar_relatorio, botao_limpar_filtro_relatorio;
	
	private CheckBox pagamentos_pendentes, pagamentos_efetuados;
	private int mYear, mMonth, mDay;
	// private ListView listView1;
	private EditText valor_pagamento;
	Double valorRestante;

	private BuscaRelatorioPagamentoHelper formularioPagamentoHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.);
		setContentView(R.layout.busca_relatorio_pagamento);
		getActionBar().setTitle("Relat—rio de Pagamentos");
		getActionBar().setDisplayHomeAsUpEnabled(true);
		formularioPagamentoHelper = new BuscaRelatorioPagamentoHelper(this);

		Intent intent = getIntent();
		Double valorRestante = (Double) intent
				.getSerializableExtra("calculoValorRestante");

		// Data do pagamento
	
		botao_buscar_relatorio = (Button) findViewById(R.id.botao_buscar_relatorio);

		data_inicial = (EditText) findViewById(R.id.data_inicial);

		data_final = (EditText) findViewById(R.id.data_final);
		
		pagamentos_efetuados = (CheckBox) findViewById(R.id.pagamentos_efetuados);
		
		pagamentos_pendentes = (CheckBox) findViewById(R.id.pagamentos_pendentes);
				botao_limpar_filtro_relatorio = (Button) findViewById(R.id.botao_limpar_filtro_relatorio);
		
		data_inicial.setOnClickListener(this);
		data_final.setOnClickListener(this);

		// Fechamento do bundle
		botao_limpar_filtro_relatorio.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				data_inicial.setText("");
				data_final.setText("");
				pagamentos_efetuados.setChecked(false);
				pagamentos_pendentes.setChecked(false);
			}
		});
		botao_buscar_relatorio.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent irParaRelatorio = new Intent(
						BuscaRelatorioPagamentosActivity.this, RelatorioPagamentos.class);
				irParaRelatorio.putExtra("data_inicial_buscar", data_inicial
						.getText().toString());
				irParaRelatorio.putExtra("data_final_buscar", data_final
						.getText().toString());
				
				String pagamentos_pendentes_informado = "N";
				String pagamentos_efetuados_informado = "N";

				if (pagamentos_pendentes.isChecked()) {
					pagamentos_pendentes_informado = "S";
				}
				
				if (pagamentos_efetuados.isChecked()) {
					pagamentos_efetuados_informado = "S";
				}
				
				irParaRelatorio.putExtra("pagamentos_efetuados", pagamentos_pendentes_informado.toString());
				irParaRelatorio.putExtra("pagamentos_pendentes", pagamentos_efetuados_informado.toString());

				startActivity(irParaRelatorio);
			}
		});

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

		if (v == data_inicial) {

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
							data_inicial.setText(dayOfMonth + "/"
									+ (monthOfYear + 1) + "/" + year);

						}
					}, mYear, mMonth, mDay);
			dpd.show();
		}

		if (v == data_final) {

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
							data_final.setText(dayOfMonth + "/"
									+ (monthOfYear + 1) + "/" + year);

						}
					}, mYear, mMonth, mDay);
			dpd.show();
		}

	}

}
