package co.com.icono.transito.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import co.com.icono.core.radicacion.RadicacionProceso;
import co.com.icono.core.util.IconoUtilidades;
import co.com.icono.transito.objects.Comparendo;

public class ComparendoDao {

	public synchronized void guardarComparendo(Connection con, Comparendo comparendo) throws SQLException {
		if (existeComparendo(con, comparendo)) {
			guardarComparendoDuplicado(con, comparendo);
		} else {
			PreparedStatement comparendoStmt = null;
			comparendoStmt = con.prepareStatement(
					"insert into transito.comparendo_radicado  values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			comparendoStmt.setString(2, comparendo.getComparendo());
			comparendoStmt.setDate(3, IconoUtilidades.fechaSQL(comparendo.getFecha_comparendo()));
			comparendoStmt.setTime(4, IconoUtilidades.timepoSQL(comparendo.getHora_comparendo()));
			comparendoStmt.setString(5, comparendo.getDireccion_comparendo());
			comparendoStmt.setInt(6, comparendo.getDivipo_comparendo());
			comparendoStmt.setString(7, comparendo.getLocalizacion());
			comparendoStmt.setString(8, comparendo.getPlaca_vehiculo());
			comparendoStmt.setString(9, comparendo.getDivipo_matricula());
			comparendoStmt.setString(10, comparendo.getTipo_vehículo());
			comparendoStmt.setString(11, comparendo.getTipo_servicio());
			comparendoStmt.setString(12, comparendo.getRadio_acción());
			comparendoStmt.setString(13, comparendo.getModalidad());
			comparendoStmt.setString(14, comparendo.getPasajeros());
			comparendoStmt.setString(15, comparendo.getIdentificacion_infractor());
			comparendoStmt.setInt(16, comparendo.getTipo_identificacion_infractor());
			comparendoStmt.setString(17, comparendo.getEdad_infractor());
			comparendoStmt.setString(18, comparendo.getLicencia_infractor());
			comparendoStmt.setString(19, comparendo.getCategoria_licencia_infractor());
			comparendoStmt.setInt(20, comparendo.getSecretaria_licencia_infractor());
			comparendoStmt.setDate(21, IconoUtilidades.fechaSQL(comparendo.getFecha_vencimiento_licencia()));
			comparendoStmt.setInt(22, comparendo.getTipo_infractor());
			comparendoStmt.setString(23, comparendo.getLicencia_infractor());
			comparendoStmt.setInt(24, comparendo.getDivipo_licencia_transito());
			comparendoStmt.setString(25, comparendo.getIdentificacion_propietario());
			comparendoStmt.setInt(26, comparendo.getTipo_identificacion_propietario());
			comparendoStmt.setString(27, comparendo.getNombre_propietario());
			comparendoStmt.setString(28, comparendo.getEmpresa_propietario());
			comparendoStmt.setString(29, comparendo.getNit_propietario());
			comparendoStmt.setString(30, comparendo.getTarjeta_operacion());
			comparendoStmt.setString(31, comparendo.getPlaca_agente());
			comparendoStmt.setString(32, comparendo.getObservaciones_agente());
			comparendoStmt.setString(33, comparendo.getFuga());
			comparendoStmt.setString(34, comparendo.getAccidente());
			comparendoStmt.setString(35, comparendo.getInmovilizacion());
			comparendoStmt.setString(36, comparendo.getNombre_patio());
			comparendoStmt.setString(37, comparendo.getDireccion_patio());
			comparendoStmt.setString(38, comparendo.getNumero_grua());
			comparendoStmt.setString(39, comparendo.getPlaca_grua());
			comparendoStmt.setString(40, comparendo.getConsecutivo_inmovilizacion());
			comparendoStmt.setString(41, comparendo.getIdentificacion_testigo());
			comparendoStmt.setString(42, comparendo.getNombre_testigo());
			comparendoStmt.setString(43, comparendo.getTelefono_testigo());
			comparendoStmt.setInt(44, comparendo.getValor());
			comparendoStmt.setInt(45, comparendo.getValor_adicional());
			comparendoStmt.setInt(46, comparendo.getSecretaria());
			comparendoStmt.setInt(47, comparendo.getEstado_simit());
			comparendoStmt.setString(48, comparendo.getPolca());
			comparendoStmt.setString(49, comparendo.getCodigo_infraccion());
			comparendoStmt.setInt(50, comparendo.getValor_infraccion());
			comparendoStmt.setString(51, comparendo.getGrado_alcoholemia());
			comparendoStmt.setString(52, comparendo.getFotomulta());
			comparendoStmt.setDate(53, IconoUtilidades.fechaSQL(comparendo.getFecha_notificacion()));
			try {
				String radicado = RadicacionProceso.getNuevoRadicadoProceso(con, comparendo.getCliente(),
						comparendo.getTipo_proceso());
				comparendoStmt.setString(1, radicado);
				comparendoStmt.executeUpdate();
			} catch (Exception e) {
				guardarComparendoDuplicado(con, comparendo);
			}
			try {
				comparendoStmt.close();
			} catch (Exception e) {
			}
		}

	}

	public synchronized void guardarComparendoDuplicado(Connection con, Comparendo comparendo) throws SQLException {

		PreparedStatement comparendoStmt = null;
		comparendoStmt = con.prepareStatement(
				"insert into transito.comparendo_duplicado values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		comparendoStmt.setDate(1, new Date(new java.util.Date().getTime()));
		comparendoStmt.setString(2, comparendo.getComparendo());
		comparendoStmt.setDate(3, IconoUtilidades.fechaSQL(comparendo.getFecha_comparendo()));
		comparendoStmt.setTime(4, IconoUtilidades.timepoSQL(comparendo.getHora_comparendo()));
		comparendoStmt.setString(5, comparendo.getDireccion_comparendo());
		comparendoStmt.setInt(6, comparendo.getDivipo_comparendo());
		comparendoStmt.setString(7, comparendo.getLocalizacion());
		comparendoStmt.setString(8, comparendo.getPlaca_vehiculo());
		comparendoStmt.setString(9, comparendo.getDivipo_matricula());
		comparendoStmt.setString(10, comparendo.getTipo_vehículo());
		comparendoStmt.setString(11, comparendo.getTipo_servicio());
		comparendoStmt.setString(12, comparendo.getRadio_acción());
		comparendoStmt.setString(13, comparendo.getModalidad());
		comparendoStmt.setString(14, comparendo.getPasajeros());
		comparendoStmt.setString(15, comparendo.getIdentificacion_infractor());
		comparendoStmt.setInt(16, comparendo.getTipo_identificacion_infractor());
		comparendoStmt.setString(17, comparendo.getEdad_infractor());
		comparendoStmt.setString(18, comparendo.getLicencia_infractor());
		comparendoStmt.setString(19, comparendo.getCategoria_licencia_infractor());
		comparendoStmt.setInt(20, comparendo.getSecretaria_licencia_infractor());
		comparendoStmt.setDate(21, IconoUtilidades.fechaSQL(comparendo.getFecha_vencimiento_licencia()));
		comparendoStmt.setInt(22, comparendo.getTipo_infractor());
		comparendoStmt.setString(23, comparendo.getLicencia_infractor());
		comparendoStmt.setInt(24, comparendo.getDivipo_licencia_transito());
		comparendoStmt.setString(25, comparendo.getIdentificacion_propietario());
		comparendoStmt.setInt(26, comparendo.getTipo_identificacion_propietario());
		comparendoStmt.setString(27, comparendo.getNombre_propietario());
		comparendoStmt.setString(28, comparendo.getEmpresa_propietario());
		comparendoStmt.setString(29, comparendo.getNit_propietario());
		comparendoStmt.setString(30, comparendo.getTarjeta_operacion());
		comparendoStmt.setString(31, comparendo.getPlaca_agente());
		comparendoStmt.setString(32, comparendo.getObservaciones_agente());
		comparendoStmt.setString(33, comparendo.getFuga());
		comparendoStmt.setString(34, comparendo.getAccidente());
		comparendoStmt.setString(35, comparendo.getInmovilizacion());
		comparendoStmt.setString(36, comparendo.getNombre_patio());
		comparendoStmt.setString(37, comparendo.getDireccion_patio());
		comparendoStmt.setString(38, comparendo.getNumero_grua());
		comparendoStmt.setString(39, comparendo.getPlaca_grua());
		comparendoStmt.setString(40, comparendo.getConsecutivo_inmovilizacion());
		comparendoStmt.setString(41, comparendo.getIdentificacion_testigo());
		comparendoStmt.setString(42, comparendo.getNombre_testigo());
		comparendoStmt.setString(43, comparendo.getTelefono_testigo());
		comparendoStmt.setInt(44, comparendo.getValor());
		comparendoStmt.setInt(45, comparendo.getValor_adicional());
		comparendoStmt.setInt(46, comparendo.getSecretaria());
		comparendoStmt.setInt(47, comparendo.getEstado_simit());
		comparendoStmt.setString(48, comparendo.getPolca());
		comparendoStmt.setString(49, comparendo.getCodigo_infraccion());
		comparendoStmt.setInt(50, comparendo.getValor_infraccion());
		comparendoStmt.setString(51, comparendo.getGrado_alcoholemia());
		comparendoStmt.setString(52, comparendo.getFotomulta());
		comparendoStmt.setDate(53, IconoUtilidades.fechaSQL(comparendo.getFecha_notificacion()));
		try {
			comparendoStmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			comparendoStmt.close();
		} catch (Exception e) {
		}

	}

	public synchronized boolean existeComparendo(Connection con, Comparendo comparendo) throws SQLException {
		boolean res = false;
		PreparedStatement comparendoStmt = con
				.prepareStatement("select comparendo from transito.comparendo_radicado where comparendo=?;");
		comparendoStmt.setString(1, comparendo.getComparendo());
		ResultSet rs = comparendoStmt.executeQuery();
		if (rs.next()) {
			res = true;
		}
		try {
			comparendoStmt.close();
		} catch (Exception e) {
		}
		return res;
	}

	public synchronized String getRadicadoComparendo(Connection con, Comparendo comparendo) throws SQLException {
		String resolucion = null;
		PreparedStatement comparendoStmt = con.prepareStatement(
				"select radicado from transito.comparendo_radicado where comparendo=? and identificacion_infractor=? and tipo_identificacion_infractor=?;");
		comparendoStmt.setString(1, comparendo.getComparendo());
		comparendoStmt.setString(2, comparendo.getIdentificacion_infractor());
		comparendoStmt.setInt(3, comparendo.getTipo_identificacion_infractor());
		ResultSet rs = comparendoStmt.executeQuery();
		if (rs.next()) {
			resolucion = rs.getString(1);
		}
		try {
			comparendoStmt.close();
		} catch (Exception e) {
		}
		return resolucion;
	}

}
