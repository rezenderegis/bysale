package com.sale.login;

import java.util.Calendar;
import java.util.Date;

import com.sale.cadastro.R;
import com.sale.cadastro.ListaPessoas;
import com.sale.cadastro.TelaInicioActivity;
import com.sale.cadastro.util.MenuInicial;
import com.sale.cadastro.util.Tools;
import com.sale.dao.SaleDAO;
import com.sale.relatorios.RelatorioPagamentos;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

public class VerificaLogin extends Activity {

	private String resp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		carregarDados();

		
		//Verificando se o alarme est‡ dipaado
	
				
		
		
		
		/*
		 * SaleDAO daoNovo = new SaleDAO(this);
		 * 
		 * resp = daoNovo.verificaUsuarioDispositivo(1);
		 * 
		 * 
		 * 
		 * Boolean existe_cliente_no_dispositivo =
		 * daoNovo.existe_cliente_no_dispositivo();
		 * 
		 * Boolean existe_categoria_no_dispositivo =
		 * daoNovo.existe_categoria_no_dispositivo();
		 * 
		 * Boolean existe_produto_no_dispositivo =
		 * daoNovo.existe_produto_no_dispositivo();
		 * 
		 * 
		 * 
		 * daoNovo.close();//Fechar DAO
		 * 
		 * if (resp != null) {
		 * 
		 * if (existe_cliente_no_dispositivo == true &&
		 * existe_categoria_no_dispositivo == true &&
		 * existe_produto_no_dispositivo == true) { Intent irTelaListaClientes =
		 * new Intent(VerificaLogin.this, ListaPessoas.class);
		 * startActivity(irTelaListaClientes); } else { Intent irTelaInicio =
		 * new Intent(VerificaLogin.this, TelaInicioActivity.class);
		 * startActivity(irTelaInicio); } } else{
		 * 
		 * Intent irTelaLogin = new Intent(VerificaLogin.this, Login.class);
		 * startActivity(irTelaLogin); }
		 */

	};

	/*
	 * @Override protected void onResume() { // TODO Auto-generated method stub
	 * super.onResume();
	 * 
	 * carregarDados(); }
	 */

	private void carregarDados() {
		// TODO Auto-generated method stub
		SaleDAO daoNovo = new SaleDAO(this);
		daoNovo.close();// Fechar DAO

		resp = daoNovo.verificaUsuarioDispositivo(1);

		/*
		 * Boolean existe_cliente_no_dispositivo = daoNovo
		 * .existe_cliente_no_dispositivo();
		 * 
		 * Boolean existe_categoria_no_dispositivo = daoNovo
		 * .existe_categoria_no_dispositivo();
		 * 
		 * Boolean existe_produto_no_dispositivo = daoNovo
		 * .existe_produto_no_dispositivo();
		 * 
		 * // Intent irTelaListaClientes = new Intent(VerificaLogin.this, //
		 * ListaPessoas.class); // startActivity(irTelaListaClientes);
		 */
		if (resp != null) {
			/*
			 * if (existe_cliente_no_dispositivo == true &&
			 * existe_categoria_no_dispositivo == true &&
			 * existe_produto_no_dispositivo == true) {
			 */
			Intent irTelaListaClientes = new Intent(VerificaLogin.this,
					MenuInicial.class);
			startActivity(irTelaListaClientes);
			/*
			 * } else { Intent irTelaInicio = new Intent(VerificaLogin.this,
			 * TelaInicioActivity.class); startActivity(irTelaInicio); }
			 */
		} else {

			Intent irTelaLogin = new Intent(VerificaLogin.this,
					TelaInicial.class);
			startActivity(irTelaLogin);
		}

	}
	/*
	 * @Override protected void onStart() { // TODO Auto-generated method stub
	 * super.onStart(); carregarDados();
	 * 
	 * Log.i("onStart VerifivaLogin", "onStart");
	 * 
	 * }
	 * 
	 * @Override protected void onRestart() { // TODO Auto-generated method stub
	 * super.onRestart(); carregarDados();
	 * 
	 * Log.i("chamou on onRestart", "onRestart");
	 * 
	 * }
	 * 
	 * private void onResumed() { // TODO Auto-generated method stub
	 * super.onResume(); carregarDados();
	 * 
	 * Log.i("chamou on onResume", "onResume");
	 * 
	 * }
	 */

	
	

	public void gerarNotificacao() {
		
		
		NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		
		Intent intent = new Intent(this,RelatorioPagamentos.class);
	
		Tools tools = new Tools();
		String dataAtual = tools.trazDataAtual();
		intent.putExtra("data_inicial_buscar", "");
		intent.putExtra("data_final_buscar", dataAtual);
		intent.putExtra("pagamentos_pendentes", "S");
		intent.putExtra("pagamentos_efetuados", "");

		PendingIntent p = PendingIntent.getActivity(this, 0, intent,0);
		
		//Utilizar o builder para construir o titulo
		
		NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
		
		builder.setTicker("Pagamentos Vencidos");
		
		builder.setContentTitle("Pagamentos Vencidos");
		
		builder.setContentText("Clique para verificar!");
		
		builder.setSmallIcon(R.drawable.logo);
		
		builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.logo));
		builder.setContentIntent(p);
		
		/*NotificationCompat.InboxStyle style = new NotificationCompat.InboxStyle();
		String[] desc = new String[]{"Descricao 1", "Desc 2", "Desc 3"};
		for (int i = 0; i < desc.length; i++) {
			style.addLine(desc[i]);
		}
		builder.setStyle(style);*/
		Notification n = builder.build();
		//Espera, vibracao, espera, vibracao
	
		//Fazer notifica‹o vibrar
	//	n.vibrate = new long[]{150, 300, 150, 600};
		n.flags = Notification.FLAG_AUTO_CANCEL;
		nm.notify(R.drawable.logo, n);
		
		/* Tocar campainha
		try {
			Uri som = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
			Ringtone toque = RingtoneManager.getRingtone(this, som);
			toque.play();
			
		} catch(Exception e) {
		
		}*/	
		
	}
	
}
