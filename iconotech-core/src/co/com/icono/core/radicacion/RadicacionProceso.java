package co.com.icono.core.radicacion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import co.com.icono.core.database.ICONOTECHConnection;

public class RadicacionProceso {

	private static DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");

	/*
	 * consecutivo_proceso: 20 => numerico 0000000000000000000 fecha_radicado:8
	 * => ddMMaaaa 00000000 tipo_proceso: 3 => numerico 000 codigo_cliente: 4 =>
	 * numerico 0000
	 */

	public static String getNuevoRadicadoProceso(Connection con,int cliente, int tipo) throws SQLException {

		try {

			// TODO: verificar que el cliente y el tipo existan en la base de
			// datos antes de generar el consecutivo para el radicado

			PreparedStatement consecutivo = con.prepareStatement("select nextval('core.secuencia_radicados');");
			PreparedStatement radicar_proceso = con.prepareStatement("insert into core.proceso values(?,?,?,?);");
			ResultSet consecutivoRs = consecutivo.executeQuery();
			consecutivoRs.next();
			int cons = consecutivoRs.getInt(1);
			String consecutivo_proceso = padString(cons, 20);
			Date date = new Date();
			String dia_radicado = dateFormat.format(date);
			String tipo_proceso = padString(tipo, 3);
			String cliente_radicado = padString(cliente, 4);
			StringBuffer radicado = new StringBuffer();
			radicado.append(tipo_proceso);
			radicado.append(cliente_radicado);
			radicado.append(dia_radicado);
			radicado.append(consecutivo_proceso);

			radicar_proceso.setString(1, radicado.toString());
			radicar_proceso.setInt(2, tipo);
			java.sql.Date sqlDate = new java.sql.Date(date.getTime());
			radicar_proceso.setDate(3, sqlDate);
			radicar_proceso.setInt(4, cliente);
			radicar_proceso.executeUpdate();

			try {
				radicar_proceso.close();
			} catch (Exception e) {
			}

			try {
				consecutivoRs.close();
			} catch (Exception e) {
			}
			try {
				consecutivo.close();
			} catch (Exception e) {
			}

			return radicado.toString();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	private static String padString(int valor, int pad) {
		return String.format("%0" + pad + "d", valor);
	}

}
