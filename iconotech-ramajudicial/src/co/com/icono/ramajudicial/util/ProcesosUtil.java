package co.com.icono.ramajudicial.util;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

import co.com.icono.ramajudicial.objects.Radicado;

public class ProcesosUtil {

	private static final DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");

	public static Radicado getRadicado(String cup) {
		Radicado radicado = new Radicado();
		radicado.setIdmunicipio(Integer.parseInt(cup.substring(0, 5)));
		radicado.setIdjuzgado(Integer.parseInt(cup.substring(5, 7)));
		radicado.setIdespecialidad(Integer.parseInt(cup.substring(7, 9)));
		radicado.setConsecutivo_despacho(Integer.parseInt(cup.substring(9, 12)));
		radicado.setAño(Integer.parseInt(cup.substring(12, 16)));
		radicado.setConsecutivo_radicado(Integer.parseInt(cup.substring(16, 21)));
		radicado.setConsecutivo_recurso(Integer.parseInt(cup.substring(21, 23)));
		return radicado;
	}

	public static String padString(String valor, String pad, int size) {
		return StringUtils.leftPad(valor, size, pad);
	}

	public static Date getDate(String date) {

		try {
			if (!date.equals("")) {
				try {
					java.util.Date d = dateFormat.parse(date);
					return new Date(d.getTime());
				} catch (ParseException e) {
					SimpleDateFormat dateFormatTmp = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
					java.util.Date d = dateFormatTmp.parse(date);
					return new Date(d.getTime());
				}
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getCurrentDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		java.util.Date date = new java.util.Date();
		return dateFormat.format(date);

	}

	public static void main(String[] args) {
		String proceso = "05001233100020010448000";
		System.out.println(getRadicado(proceso).getIdmunicipio());
		System.out.println(getRadicado(proceso).getIdjuzgado());
		System.out.println(getRadicado(proceso).getIdespecialidad());
		System.out.println(getRadicado(proceso).getConsecutivo_despacho());
		System.out.println(getRadicado(proceso).getAño());
		System.out.println(getRadicado(proceso).getConsecutivo_radicado());
		System.out.println(getRadicado(proceso).getConsecutivo_recurso());
	}

}
