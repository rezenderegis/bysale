package com.sale.cadastro.util;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * This BroadcastReceiver automatically (re)starts the alarm when the device is
 * rebooted. This receiver is set to be disabled (android:enabled="false") in the
 * application's manifest file. When the user sets the alarm, the receiver is enabled.
 * When the user cancels the alarm, the receiver is disabled, so that rebooting the
 * device will not trigger this receiver.
 */
// BEGIN_INCLUDE(autostart)
public class SampleBootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
       
    	

		
		boolean alarmeAtivo = (PendingIntent.getBroadcast(context, 0, new Intent("ALARME_DISPARADO"), PendingIntent.FLAG_NO_CREATE) == null);
		
	//	if(alarmeAtivo){
			Log.i("Script", "Ativo");

			intent = new Intent("ALARME_DISPARADO");
			PendingIntent p = PendingIntent.getBroadcast(context, 0, intent, 0);
			
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(System.currentTimeMillis());
			c.add(Calendar.SECOND, 50);
			
			AlarmManager alarme = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
			alarme.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), 5000, p);
	/*	}
		else{
			Log.i("Script", "Nao ativo");
		}*/
				
    	
    	
    }
}
//END_INCLUDE(autostart)
