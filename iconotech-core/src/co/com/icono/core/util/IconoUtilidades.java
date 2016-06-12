package co.com.icono.core.util;

import java.util.Date;
import java.util.StringTokenizer;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class IconoUtilidades {

	private static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	private static DateFormat timeFormat = new SimpleDateFormat("hh:mm");
	private static SimpleDateFormat timeFormat2 = new SimpleDateFormat("hhmm");
	
	public synchronized static int contarPalabras(String str){
		StringTokenizer st = new StringTokenizer(str, " ");
		int resp = st.countTokens();
		st = null;
		return resp;
	}

	public synchronized static java.sql.Date fechaSQL(Date fechaStr) {
		java.sql.Date fecha = null;
		if (fechaStr != null) {
			try {
				fecha = new java.sql.Date(fechaStr.getTime());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return fecha;
	}

	public synchronized static java.sql.Time timepoSQL(Date horaStr) {
		java.sql.Time hora = null;
		if (horaStr != null) {
			try {
				hora = new java.sql.Time(horaStr.getTime());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return hora;
	}

	public synchronized static Date parseFecha(String fechaStr) {
		Date fecha = null;
		if (!fechaStr.equals("")) {
			try {
				fecha = dateFormat.parse(fechaStr.trim());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return fecha;
	}

	public synchronized static Date parseHora(String horaStr) {
		Date hora = null;
		if (!horaStr.equals("")) {
			try {
				if (horaStr.length() == 4) {
					hora = timeFormat2.parse(horaStr.trim());
				} else {
					hora = timeFormat.parse(horaStr.trim());
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return hora;
	}

	public synchronized static int parseInt(String intStr) {
		int i = -1;
		if (!intStr.equals("")) {
			try {
				i = Integer.parseInt(intStr.trim());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return i;
	}

}
