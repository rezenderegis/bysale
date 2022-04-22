package com.sale.cadastro.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import com.sale.cadastro.SMSReceiver;


/**
 * When the alarm fires, this WakefulBroadcastReceiver receives the broadcast Intent 
 * and then starts the IntentService {@code SampleSchedulingService} to do some work.
 */
public class SampleAlarmReceiver extends WakefulBroadcastReceiver {
    // The app's AlarmManager, which provides access to the system alarm services.
    private AlarmManager alarmMgr;
    // The pending intent that is triggered when the alarm fires.
    private PendingIntent alarmIntent;
  
    @Override
    public void onReceive(Context context, Intent intent) {   
        
        Intent service = new Intent(context, SampleAlarmReceiver.class);
        
        startWakefulService(context, service);
    }
   
    public void setAlarm(Context context) {

		boolean alarmeAtivo = (PendingIntent.getBroadcast(context, 0, new Intent("ALARME_DISPARADO"), PendingIntent.FLAG_NO_CREATE) == null);
		
		if(alarmeAtivo){
			Log.i("Script", "Ativo");

			Intent intent = new Intent("ALARME_DISPARADO");
			PendingIntent p = PendingIntent.getBroadcast(context, 0, intent, 0);
			
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(System.currentTimeMillis());
			c.add(Calendar.SECOND, 30);
			
			AlarmManager alarme = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
			alarme.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), 5000, p);
		}
		else{
			Log.i("Script", "Nao ativo");
		}
		         
    }
    // END_INCLUDE(set_alarm)

    /**
     * Cancels the alarm.
     * @param context
     */
    // BEGIN_INCLUDE(cancel_alarm)
    public void cancelAlarm(Context context) {
        // If the alarm has been set, cancel it.
        if (alarmMgr!= null) {
            alarmMgr.cancel(alarmIntent);
        }
        
        // Disable {@code SampleBootReceiver} so that it doesn't automatically restart the 
        // alarm when the device is rebooted.
        ComponentName receiver = new ComponentName(context, SMSReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }
    // END_INCLUDE(cancel_alarm)
}
