package co.com.icono.core.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import co.com.icono.core.objects.Persona;
import co.com.icono.core.util.IconoUtilidades;

public class PersonaDao {

	public synchronized void saveOrUpdatePersona(Connection con, Persona persona) throws SQLException {
		boolean existeP = existePersona(con, persona);
		if (!existeP) {
			PreparedStatement personaStmt = con.prepareStatement("insert into core.personas values(?,?,?,?)");
			personaStmt.setString(1, persona.getIdentificacion());
			personaStmt.setInt(2, persona.getTipoIdentificacion());
			personaStmt.setString(3, persona.getNombres());
			personaStmt.setString(4, persona.getApellidos());
			personaStmt.executeUpdate();
			try {
				personaStmt.close();
			} catch (Exception e) {
			}
		}
		saveUbicacion(con, persona);
	}

	public synchronized void saveUbicacion(Connection con, Persona persona) throws SQLException {
		boolean existeU = existeUbicacion(con, persona);
		if (!existeU) {
			PreparedStatement ubicacionStmt = con
					.prepareStatement("insert into core.historial_ubicacion_persona values(?,?,?,?,?,?,?)");
			ubicacionStmt.setString(1, persona.getIdentificacion());
			ubicacionStmt.setInt(2, persona.getTipoIdentificacion());
			ubicacionStmt.setString(3, persona.getNumeroContacto());
			ubicacionStmt.setString(4, persona.getDireccion());
			ubicacionStmt.setInt(5, persona.getCiudadID());
			ubicacionStmt.setString(6, persona.getEmail());
			ubicacionStmt.setDate(7, IconoUtilidades.fechaSQL(persona.getFecha_registro()));
			ubicacionStmt.executeUpdate();
			try {
				ubicacionStmt.close();
			} catch (Exception e) {
			}
		}
	}

	public synchronized boolean existePersona(Connection con, Persona persona) throws SQLException {
		boolean res = false;
		PreparedStatement personaStmt = con.prepareStatement(
				"select identificacion from core.personas where identificacion=? and tipo_identificacion=?;");

		personaStmt.setString(1, persona.getIdentificacion());
		personaStmt.setInt(2, persona.getTipoIdentificacion());

		ResultSet rs = personaStmt.executeQuery();
		if (rs.next()) {
			res = true;
		}
		try {
			personaStmt.close();
		} catch (Exception e) {
		}

		return res;
	}

	public synchronized boolean existeUbicacion(Connection con, Persona persona) throws SQLException {
		boolean res = false;
		PreparedStatement personaStmt = con.prepareStatement(
				"select identificacion from core.historial_ubicacion_persona where identificacion=? and tipo_identificacion=? and direccion=?  and telefono=? and email=? and id_ciudad=? and fecha_registro=?;");

		personaStmt.setString(1, persona.getIdentificacion());
		personaStmt.setInt(2, persona.getTipoIdentificacion());
		personaStmt.setString(3, persona.getDireccion());
		personaStmt.setString(4, persona.getNumeroContacto());
		personaStmt.setString(5, persona.getEmail());
		personaStmt.setInt(6, persona.getCiudadID());
		personaStmt.setDate(7, IconoUtilidades.fechaSQL(persona.getFecha_registro()));
		ResultSet rs = personaStmt.executeQuery();
		if (rs.next()) {
			res = true;
		}
		try {
			personaStmt.close();
		} catch (Exception e) {
		}

		return res;
	}

	public static void main(String[] args) {

		System.out.println(IconoUtilidades.contarPalabras(""));

	}

	public synchronized Persona obtenerMejorUbicacion(Connection con, Persona persona) throws SQLException {

		PreparedStatement personaStmt = con.prepareStatement(
				"select direccion,id_ciudad,fecha_registro from core.historial_ubicacion_persona where identificacion=? and tipo_identificacion=? order by fecha_registro asc;");

		personaStmt.setString(1, persona.getIdentificacion());
		personaStmt.setInt(2, persona.getTipoIdentificacion());

		ResultSet rs = personaStmt.executeQuery();
		String direccion = null;
		String ciudad_direccion = null;
		while (rs.next()) {

			if (direccion == null) {
				String dir = rs.getString(1);
				if (IconoUtilidades.contarPalabras(dir) > 3) {
					direccion = dir;
					persona.setDireccion(direccion);
				} else {
					if ((dir.contains("CL") || dir.contains("CR") || dir.contains("CALLE") || dir.contains("CARRERA"))
							&& !dir.equals("CALLE") && !dir.equals("CARRERA")) {
						direccion = dir;
						persona.setDireccion(direccion);
					}
				}
			}
			if (ciudad_direccion == null) {
				int ci = rs.getInt(2);
				if (ci != 0) {
					try {
						ciudad_direccion = new CiudadDao().obtenerCiudadParaDireccion(con, ci);
						persona.setCiudad_direccion(ciudad_direccion);
					} catch (SQLException e) {
					}
				}
			}
		}
		try {
			personaStmt.close();
		} catch (Exception e) {
		}

		return persona;
	}

}
