package co.com.icono.core.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CiudadDao {

	public synchronized String obtenerCiudadParaDireccion(Connection con, int id_ciudad) throws SQLException {
		String ciudad_direccion = null;
		PreparedStatement personaStmt = con.prepareStatement(
				"select b.ciudad,c.municipio,a.departamento from core.ciudad b,core.departamento a,core.municipio c where b.municipio_id = c.municipio_id and a.departamento_id = c.departamento_id and ciudad_id = ?");

		personaStmt.setInt(1, id_ciudad);

		ResultSet rs = personaStmt.executeQuery();
		if (rs.next()) {
			String c = rs.getString(1);
			String m = rs.getString(2);
			String d = rs.getString(3);
			if (c.equals(m)) {
				ciudad_direccion = c + ", " + d;
			}else if (c.equals(m) && c.equals(d)){
				ciudad_direccion = c;
			}else {
				ciudad_direccion = c + ", " + m + ", " + d;
			}

		}
		try {
			personaStmt.close();
		} catch (Exception e) {
		}

		return ciudad_direccion;
	}

}
