package co.com.icono.tareas.transito;

import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import co.com.icono.cienaga.integracion.IntegracionComparendosCienaga;
import co.com.icono.core.database.ICONOTECHConnection;
import co.com.icono.core.util.DateIterator;

public class ActualizarComparendosCienaga {

	private static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

	public static void main(String[] args) {
		try {
			Connection con = ICONOTECHConnection.getConnection();
			
			Calendar cal1 = Calendar.getInstance();
			cal1.add(Calendar.DATE, -15);
			Date d1 = cal1.getTime();
			
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -1);
			Date d2 = cal.getTime();
			
			
			Iterator<Date> i = new DateIterator(d1, d2);
			while (i.hasNext()) {
				Date date = i.next();
				String fecha = dateFormat.format(date);
				try {
					IntegracionComparendosCienaga.revisarActualizacionesCienaga(fecha, con);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			try {
				con.close();
			} catch (Exception e) {
			}
		} catch (Exception e) {
		}

	}

}
