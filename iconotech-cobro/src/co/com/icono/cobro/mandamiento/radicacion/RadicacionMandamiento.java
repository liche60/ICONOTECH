package co.com.icono.cobro.mandamiento.radicacion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import co.com.icono.core.database.ICONOTECHConnection;

public class RadicacionMandamiento {

	private static DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");

	/*
	 * consecutivo_proceso: 20 => numerico 0000000000000000000 fecha_radicado:8
	 * => ddMMaaaa 00000000 tipo_proceso: 3 => numerico 000 codigo_cliente: 4 =>
	 * numerico 0000
	 */

	public static String getNuevoRadicadoMandamiento(Connection con, int cliente, String id_deudor, int tipo_id_deudor)
			throws SQLException {

		try {

			// TODO: verificar que el cliente y el tipo existan en la base de
			// datos antes de generar el consecutivo para el radicado

			PreparedStatement consecutivo = con.prepareStatement("select nextval('cobro.consecutivo_mandamiento');");
			PreparedStatement radicar_proceso = con.prepareStatement(
					"insert into cobro.mandamiento_pago (radicado,fecha_radicado,cliente_id,identificacion_deudor,tipo_identificacion_deudor) values(?,?,?,?,?);");

			ResultSet consecutivoRs = consecutivo.executeQuery();
			consecutivoRs.next();
			int cons = consecutivoRs.getInt(1);
			String consecutivo_proceso = padString(cons, 20);
			Date date = new Date();
			String dia_radicado = dateFormat.format(date);

			String cliente_radicado = padString(cliente, 4);
			StringBuffer radicado = new StringBuffer();
			radicado.append("M");
			radicado.append(cliente_radicado);
			radicado.append(dia_radicado);
			radicado.append(consecutivo_proceso);

			radicar_proceso.setString(1, radicado.toString());
			java.sql.Date sqlDate = new java.sql.Date(date.getTime());
			radicar_proceso.setDate(2, sqlDate);
			radicar_proceso.setInt(3, cliente);
			radicar_proceso.setString(4, id_deudor);
			radicar_proceso.setInt(5, tipo_id_deudor);
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
