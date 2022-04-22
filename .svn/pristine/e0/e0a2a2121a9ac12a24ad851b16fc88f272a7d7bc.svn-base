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

public class BuscaRelatorioActivity extends Activity implements OnClickListener {

	int layout;
	ListView formas_pagamento;
	private EditText data_inicial, data_final;
	private CheckBox pedidos_em_orcamento, pedidos_emitidos;

	private Button 
			botao_buscar_relatorio, botao_limpar_filtro_relatorio;
	private int mYear, mMonth, mDay;
	// private ListView listView1;
	private EditText valor_pagamento;
	Double valorRestante;

	private BuscaRelatorioHelper formularioPagamentoHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.);
		setContentView(R.layout.busca_rel_novo);
		getActionBar().setTitle("Relat—rio de Pedidos");
		getActionBar().setDisplayHomeAsUpEnabled(true);
		formularioPagamentoHelper = new BuscaRelatorioHelper(this);

		Intent intent = getIntent();
		Double valorRestante = (Double) intent
				.getSerializableExtra("calculoValorRestante");

		// Data do pagamento
		pedidos_em_orcamento = (CheckBox) findViewById(R.id.pedidos_em_orcamento);
		
		pedidos_emitidos = (CheckBox) findViewById(R.id.pedidos_emitidos);
		
		botao_buscar_relatorio = (Button) findViewById(R.id.botao_buscar_relatorio);

		data_inicial = (EditText) findViewById(R.id.data_inicial);

		data_final = (EditText) findViewById(R.id.data_final);

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
				pedidos_em_orcamento.setChecked(false);
				pedidos_emitidos.setChecked(false);
			}
		});
		botao_buscar_relatorio.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent irParaRelatorio = new Intent(
						BuscaRelatorioActivity.this, Relatorio2.class);
				irParaRelatorio.putExtra("data_inicial_buscar", data_inicial
						.getText().toString());
				irParaRelatorio.putExtra("data_final_buscar", data_final
						.getText().toString());
				
				
				String pedidos_em_orcamento_informado = "N";
				String pedidos_emitidos_informado = "N";

				if (pedidos_em_orcamento.isChecked()) {
					pedidos_em_orcamento_informado = "S";
				}
				
				if (pedidos_emitidos.isChecked()) {
					pedidos_emitidos_informado = "S";
				}
				
				
				irParaRelatorio.putExtra("pedidos_em_orcamento", pedidos_em_orcamento_informado.toString());
				irParaRelatorio.putExtra("pedidos_emitidos", pedidos_emitidos_informado.toString());
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
