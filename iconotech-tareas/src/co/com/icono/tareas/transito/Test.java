package co.com.icono.tareas.transito;

import java.sql.Connection;

import co.com.icono.core.database.ICONOTECHConnection;
import co.com.icono.transito.resolucion.ResolucionBuilder;
import net.sf.jasperreports.engine.JRException;

public class Test {

	public static void main(String[] args) {
		try {
			Connection con = ICONOTECHConnection.getConnection();
			
			con.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
