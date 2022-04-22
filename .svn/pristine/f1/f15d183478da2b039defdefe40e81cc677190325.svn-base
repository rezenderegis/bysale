package com.sale.cadastro;

import java.util.Calendar;
import java.util.Date;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.sale.relatorios.RelatorioPagamentos;

public class SMSReceiver extends BroadcastReceiver {

	Intent intent;
	
	
	
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
	
		
		setAlarm(context);
	//	enviarNotificacao(context);
	}
	
/*	
	public void enviarNotificacao (Context context) {
		
		
		if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
			Intent intent = new Intent("ALARME_DISPARADO_NOVO");
			PendingIntent p = PendingIntent.getBroadcast(context, 0, intent, 0);
			try {
				p.send(context, 0, intent);
			} catch (PendingIntent.CanceledException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
		
		
	}
	*/
	
	public void setAlarm(Context context) {

		boolean alarmeAtivo = (PendingIntent.getBroadcast(context, 0, new Intent("ALARME_DISPARADO_NOVO"), PendingIntent.FLAG_NO_CREATE) == null);
		
		if(alarmeAtivo){
			Log.i("Script", "Ativo");

			Intent intent = new Intent("ALARME_DISPARADO_NOVO");
			PendingIntent p = PendingIntent.getBroadcast(context, 0, intent, 0);
			
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(System.currentTimeMillis());
			c.add(Calendar.SECOND, 59);
			
			AlarmManager alarme = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
			alarme.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), 3600000, p);
		}
		else{
			Log.i("Script", "Nao ativo");
		}
		         
    }
	
	
	

}
