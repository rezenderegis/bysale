package com.sale.cadastro;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.sale.cadastro.R;
import com.sale.cadastro.util.MenuInicial;
import com.sale.cadastro.util.Tools;
import com.sale.dao.SaleDAO;
import com.sale.login.VerificaLogin;
import com.sale.relatorios.RelatorioPagamentos;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class SMSReceiver1 extends BroadcastReceiver {

	Intent intent;
	

	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		
		SaleDAO dao = new SaleDAO(context);
		Boolean haPedidosVencidos = dao.haPedidosVencidos();
		dao.close();
		
		GregorianCalendar g = new GregorianCalendar();
		
		if (haPedidosVencidos == true && g.get(Calendar.HOUR) == 10) {
			Notification(context, "");
		}
	}
	
	
	

	public void Notification(Context context, String message) {
			
		NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		
		Intent intent = new Intent(context,RelatorioPagamentos.class);
	

		intent.putExtra("data_inicial_buscar", "");
		intent.putExtra("data_final_buscar", "");
		intent.putExtra("pagamentos_pendentes", "S");
		intent.putExtra("pagamentos_efetuados", "");

		PendingIntent p = PendingIntent.getActivity(context, 0, intent,0);
		
		//Utilizar o builder para construir o titulo
		
		NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
		
		builder.setTicker("Pagamentos Pendentes");
		
		builder.setContentTitle("Pagamentos Pendentes");
		
		builder.setContentText("Clique para verificar!");
		
		builder.setSmallIcon(R.drawable.logo);
		
		builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.drawable.logo));
		builder.setContentIntent(p);
		
		/*NotificationCompat.InboxStyle style = new NotificationCompat.InboxStyle();
		String[] desc = new String[]{"Descricao 1", "Desc 2", "Desc 3"};
		for (int i = 0; i < desc.length; i++) {
			style.addLine(desc[i]);
		}
		builder.setStyle(style);*/
		Notification n = builder.build();
		//Espera, vibracao, espera, vibracao
		n.vibrate = new long[]{150, 300, 150, 600};
		n.flags = Notification.FLAG_AUTO_CANCEL;
		nm.notify(R.drawable.logo, n);
		
		
		try {
			Uri som = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
			Ringtone toque = RingtoneManager.getRingtone(context, som);
			toque.play();
			
		} catch(Exception e) {
			
		}
		

	
	
}
	

}
