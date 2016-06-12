package co.com.icono.transito.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import co.com.icono.core.util.IconoUtilidades;
import co.com.icono.transito.objects.ResolucionComparendo;

public class ResolucionComparendoDao {

	public synchronized void guardarResolucion(Connection con, ResolucionComparendo resolucion) throws SQLException {
		PreparedStatement resolucionStmt = con
				.prepareStatement("insert into transito.resolucion_comparendo  values(?,?,?,?,?,?,?)");
		resolucionStmt.setString(1, resolucion.getResolucion());
		resolucionStmt.setDate(2, IconoUtilidades.fechaSQL(resolucion.getFecha_resolucion()));
		resolucionStmt.setInt(3, resolucion.getTipo_resolucion());
		resolucionStmt.setString(4, resolucion.getResolucion_anterior());
		resolucionStmt.setDate(5, IconoUtilidades.fechaSQL(resolucion.getFecha_hasta()));
		resolucionStmt.setString(6, resolucion.getRadicado_comparendo());
		resolucionStmt.setString(7, resolucion.getComparendo());
		try {
			resolucionStmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			resolucionStmt.close();
		} catch (Exception e) {
		}

	}

}
