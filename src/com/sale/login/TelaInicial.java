package com.sale.login;

import cadastro_usuario.CadastraUsuarioJson;
import cadastro_usuario.CadastroUsuario;

import com.sale.cadastro.R;
import com.sale.dao.SaleDAO;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class TelaInicial extends Activity {

	Button cadastrar;
	Button logar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.tela_inicial);

		cadastrar = (Button) findViewById(R.id.cadastrar_usuario_tela_inicial);
		logar = (Button) findViewById(R.id.fazer_login_tela_inicial);

		cadastrar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent cadastrar_usuario = new Intent(TelaInicial.this,
						CadastroUsuario.class);
				startActivity(cadastrar_usuario);

			}
		});

		logar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent logar_usuario = new Intent(TelaInicial.this, Login.class);
				startActivity(logar_usuario);
			}
		});
	}

	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent home = new Intent(Intent.ACTION_MAIN);
		home.addCategory(Intent.CATEGORY_HOME);
		home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(home);

	}

}
