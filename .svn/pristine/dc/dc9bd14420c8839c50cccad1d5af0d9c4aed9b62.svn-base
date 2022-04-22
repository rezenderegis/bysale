package com.sale.cadastro;

import com.sale.cadastro.R;
import com.sale.cadastro.util.MenuInicial;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class TelaRelatorios extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	
		setContentView(R.layout.tela_relatorios);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle("Relat—rios");

		Button botao_relatorio_vendas = (Button) findViewById(R.id.botao_relatorio_vendas);
		
		Button botao_relatorio_pagamentos = (Button) findViewById(R.id.botao_relatorio_pagamentos);
		
		
		botao_relatorio_vendas.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent abrirRelatorio = new Intent(TelaRelatorios.this,
						BuscaRelatorioActivity.class);
				startActivity(abrirRelatorio);
			}
		});
		
		
		
		botao_relatorio_pagamentos.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent abrirRelatorioPagamento = new Intent(TelaRelatorios.this, BuscaRelatorioPagamentosActivity.class);
				startActivity(abrirRelatorioPagamento);
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
	
}
