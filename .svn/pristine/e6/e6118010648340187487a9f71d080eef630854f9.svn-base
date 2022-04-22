package com.sale.cadastro.util;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.UUID;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import com.sale.cadastro.R;
import com.sale.dao.SaleDAO;
import com.sale.login.VerificaLogin;
import com.sale.relatorios.RelatorioPagamentos;

public class Tools {

	public String trazDataAtual() {

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		return dateFormat.format(date);

	}

	/*
	 * public String retorna_data_a_partir_string (String dataConverter) {
	 * 
	 * 
	 * SimpleDateFormat dataString = new SimpleDateFormat("dd-mm-yyyy"); String
	 * valor_pagamento_original = dataConverter;
	 * 
	 * SimpleDateFormat dataStringNova = new
	 * SimpleDateFormat("dd/mm/yyyy HH:mm:ss");
	 * 
	 * String data_retornar = null; try {
	 * 
	 * Date date = dataString.parse(valor_pagamento_original); data_retornar =
	 * dataStringNova.format(date);
	 * 
	 * } catch (ParseException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }
	 * 
	 * 
	 * return data_retornar;
	 * 
	 * 
	 * }
	 */

	public Date retorna_data_a_partir_string(String dataConverter) {

		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		String dateInString = dataConverter;
		Date data_retornar = null;
		try {
			// Est� dando erro na formta��o da data, mas isso � novo n�o
			// acontecia.
			data_retornar = formatter.parse(dateInString);

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return data_retornar;

	}
	
	
	public String retorna_data_com_barra(String dataConverter) {

		String startDateString = dataConverter;
	    DateFormat df = new SimpleDateFormat("dd/MM/yyyy"); 
	    Date startDate;
	    String newDateString = null;
	    try {
	        startDate = df.parse(startDateString);
	         newDateString = df.format(startDate);
	    } catch (ParseException e) {
	        e.printStackTrace();
	    }
	    
	    return newDateString;

	}
	
	public String retorna_data_com_traco(String data) {

		  SimpleDateFormat originalFormat = new SimpleDateFormat("dd/MM/yyyy");
		   SimpleDateFormat targetFormat = new SimpleDateFormat("dd-MM-yyyy" );
		   Date date = null;
		   
		   String retorno = null;
		   try {
		     date = originalFormat.parse(data);
	
		     retorno = targetFormat.format(date);

		    } catch (ParseException ex) {

		       }
		return retorno;
	}

	public Double retornaValorDecimal(String valor) throws ParseException {
		DecimalFormat precoFormatado = new DecimalFormat();
		DecimalFormatSymbols simbolo = new DecimalFormatSymbols();
		simbolo.setDecimalSeparator(',');
		simbolo.setGroupingSeparator('.');
		precoFormatado.setDecimalFormatSymbols(simbolo);
		Number number = precoFormatado.parse(valor);

		return Double.parseDouble(number.toString());
	}
	
	
	public static Date converte_string_para_data (String data) {
		//"dd/MM/yyyy"
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		
		Date date = null;
		try {

			date = formatter.parse(data);
		

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
		
		
		
		
	}
	
	
public static String retorna_data_com_traco_yyyyMMdd(String data) throws ParseException {

		
		DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		
		String dateString = data;
		Date dateObject = sdf.parse(dateString);		
		
		String nova = new SimpleDateFormat("yyyy-MM-dd").format(dateObject);
		
		return nova;

		
	}

public static Date retorna_data_com_traco_ddmmyy(String data)  {

	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	String dateInString = data;
	Date date = null;
	try {

		 date = formatter.parse(dateInString);
		//System.out.println(date);
		//System.out.println(formatter.format(date));

	} catch (ParseException e) {
		e.printStackTrace();
	}
	
	
	return date;

	
}



/*
public void alarme(Context context) {
	

	boolean alarmeAtivo = (PendingIntent.getBroadcast(context, 0, new Intent("ALARME_DISPARADO"), PendingIntent.FLAG_NO_CREATE) == null);
	
	if(alarmeAtivo){
		Log.i("Script", "Ativo");

		Intent intent = new Intent("ALARME_DISPARADO");
		PendingIntent p = PendingIntent.getBroadcast(context, 0, intent, 0);
		
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(System.currentTimeMillis());
		c.add(Calendar.SECOND, 3);
		
		AlarmManager alarme = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		alarme.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), 9000, p);
	}
	else{
		Log.i("Script", "Nao ativo");
	}
			
			
			
	
	
}

*/

public void gerarNotificacao(Context context) {
	
	
	SaleDAO dao = new SaleDAO(context);
	Boolean haPedidosVencidos = dao.haPedidosVencidos();
	dao.close();

	if (haPedidosVencidos == true) {
	
	NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
	
	Intent intent = new Intent(context,RelatorioPagamentos.class);

	Tools tools = new Tools();
	String dataAtual = tools.trazDataAtual();
	intent.putExtra("data_inicial_buscar", "");
	intent.putExtra("data_final_buscar", dataAtual);
	intent.putExtra("pagamentos_pendentes", "S");
	intent.putExtra("pagamentos_efetuados", "");

	PendingIntent p = PendingIntent.getActivity(context, 0, intent,0);
	
	//Utilizar o builder para construir o titulo
	
	NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
	
	builder.setTicker("Pagamentos Vencidos");
	
	builder.setContentTitle("Pagamentos Vencidos");
	
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

	//Fazer notifica��o vibrar
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

//Método alternativo para trazer id, mas não confio por que retirei os código em hexadecimais
public static int generateUniqueId() {      
    UUID idOne = UUID.randomUUID();
    String str=""+idOne;        
    int uid=str.hashCode();
    String filterStr=""+uid;
    str=filterStr.replaceAll("-", "");
    return Integer.parseInt(str);
    
    
}

public String retornarIdParaInserir (Long idUsuario, Long idEmpresa) {
	
    
	SimpleDateFormat dateFormat = new SimpleDateFormat(
			"dd/MM/yy HH:mm:ss.SSS");
	Calendar cal = Calendar.getInstance();
	String dataAtual = dateFormat.format(cal.getTime());

	String dataSemAspas = dataAtual.replaceAll("/", "");
	String dataSemDoisPontos = dataSemAspas.replaceAll(":", "");
	String dataSemPonto = dataSemDoisPontos.replaceAll("\\.", "");
	String dataSemEspaco = dataSemPonto.replaceAll("\\s+", "");
	
	StringBuilder idRetornar = new StringBuilder(idUsuario.toString());
	//idRetornar.append(idEmpresa.toString());
	idRetornar.append(dataSemEspaco);
	//idRetornar = idRetornar.toString()
	String idRetorno = idRetornar.toString();
	return idRetorno;

}



}
