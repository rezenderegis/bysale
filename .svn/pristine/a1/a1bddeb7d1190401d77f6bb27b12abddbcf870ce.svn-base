package com.sale.cadastro.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Button;

import com.sale.cadastro.R;
import com.sale.login.Login;

public class Botao extends Activity {

	String mensagem;

	public Botao(String mensagem) {
		super();
		this.mensagem = mensagem;
	}

	private void onCreateBu() {
		// TODO Auto-generated method stub

		String mensagem = "Entre no aplicativo com o usuário ";
		String mensagem1 = " e clique em Trocar Usuário!";
		// View mensagemConfirmação = ((LayoutInflater)
		// getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.mensagem_bloquear,
		// null);
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				Botao.this);
		alertDialogBuilder.setMessage(mensagem);
		alertDialogBuilder.setPositiveButton("Confirmar",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int id) {
						// TODO Auto-generated method stub

					}
				})/*
				 * .setNegativeButton("Cancelar", new
				 * DialogInterface.OnClickListener() {
				 * 
				 * @Override public void onClick(DialogInterface dialog, int
				 * which) { // TODO Auto-generated method stub dialog.cancel();
				 * } })
				 */;

		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();

		// Mudar a cor do bot„o
		((Button) alertDialog.findViewById(android.R.id.button2))
				.setBackgroundResource(R.color.botao_picker);
		((Button) alertDialog.findViewById(android.R.id.button1))
				.setBackgroundResource(R.color.botao_picker);
		((Button) alertDialog.findViewById(android.R.id.button1))
				.setTextColor(getResources().getColor(R.color.branco));
		((Button) alertDialog.findViewById(android.R.id.button2))
				.setTextColor(getResources().getColor(R.color.branco));

	}

}
