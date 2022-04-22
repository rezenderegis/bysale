package com.sale.cadastro;

import com.sale.cadastro.R;
import com.sale.cadastro.util.MenuInicial;
import com.sale.dao.SaleDAO;
import com.sale.login.VerificaLogin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class TelaInicioActivity extends Activity {

	Boolean existe_cliente_no_dispositivo;

	Boolean existe_categoria_no_dispositivo;

	Boolean existe_produto_no_dispositivo;

	private ListView produtos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.tela_inicio);

		SaleDAO dao = new SaleDAO(this);
		existe_cliente_no_dispositivo = dao.existe_cliente_no_dispositivo();

		existe_categoria_no_dispositivo = dao.existe_categoria_no_dispositivo();

		existe_produto_no_dispositivo = dao.existe_produto_no_dispositivo();
		dao.close();

		final Button botao_cadastrar_produto_inicio = (Button) findViewById(R.id.botao3_tela_inicio);

		final Button botao_cadastrar_cliente_inicio = (Button) findViewById(R.id.botao1_tela_inicio);

		final Button botao_cadastrar_categoria_inicio = (Button) findViewById(R.id.botao2_tela_inicio);

		if (existe_cliente_no_dispositivo == true) {

			botao_cadastrar_cliente_inicio
					.setBackgroundResource(R.color.cinza_claro);
			botao_cadastrar_cliente_inicio.setEnabled(false);
		}

		if (existe_categoria_no_dispositivo == true) {
			botao_cadastrar_categoria_inicio
					.setBackgroundResource(R.color.cinza_claro);
			botao_cadastrar_categoria_inicio.setEnabled(false);
		}

		if (existe_categoria_no_dispositivo == false) {
			botao_cadastrar_produto_inicio.setEnabled(false);
			botao_cadastrar_produto_inicio
					.setBackgroundResource(R.color.cinza_claro);
		}

		if (existe_cliente_no_dispositivo == true
				&& existe_categoria_no_dispositivo == true
				&& existe_produto_no_dispositivo == true) {
			Intent irTelaInicio = new Intent(TelaInicioActivity.this,
					MenuInicial.class);
			startActivity(irTelaInicio);
		}

		// botao_cadastrar_cliente_inicio.setVisibility(5);

		// final Button botao_cadastrar_
		botao_cadastrar_produto_inicio
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent cadastrarProduto = new Intent(
								TelaInicioActivity.this,
								FormularioProdutoActivity.class);
						startActivity(cadastrarProduto);

					}
				});

		botao_cadastrar_cliente_inicio
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						Intent cadastrarClientes = new Intent(
								TelaInicioActivity.this, Formulario.class);
						startActivity(cadastrarClientes);

					}
				});

		botao_cadastrar_categoria_inicio
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						Intent cadastrarCategorias = new Intent(
								TelaInicioActivity.this,
								FormularioCategoriaActivity.class);
						startActivity(cadastrarCategorias);

					}
				});
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		Intent intent = getIntent();
		finish();
		startActivity(intent);

	}
}
