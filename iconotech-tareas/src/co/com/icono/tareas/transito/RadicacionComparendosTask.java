package co.com.icono.tareas.transito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;

import co.com.icono.core.dao.PersonaDao;
import co.com.icono.core.database.ICONOTECHConnection;
import co.com.icono.core.objects.Persona;
import co.com.icono.core.util.IconoUtilidades;
import co.com.icono.transito.dao.ComparendoDao;
import co.com.icono.transito.objects.Comparendo;

public class RadicacionComparendosTask {

	/*
	 * consecutivo_proceso: 19 => numerico 0000000000000000000 fecha_radicado:8
	 * => ddMMaaaa 00000000 tipo_proceso: 3 => numerico 000 codigo_cliente: 4 =>
	 * numerico 0000
	 */

	private static double cantidad_proceso = 0;
	private static int limit = 0;
	private static double procesados = 0;
	private static int hilos = 500;
	private static HashMap<String, Integer> offesetMap;

	public static void main(String[] args) {

		Connection con;

		try {
			con = ICONOTECHConnection.getConnection();
			PreparedStatement comparendos = con.prepareStatement("select transito.diferencial_comparendos_radicados()");
			ResultSet comparendosRs = comparendos.executeQuery();
			if (comparendosRs.next()) {
				cantidad_proceso = comparendosRs.getInt(1);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		System.out.println(cantidad_proceso);
		if (cantidad_proceso > 0) {

			double tmp1 = (cantidad_proceso / hilos);
			limit = (int) Math.ceil(tmp1);

			if (cantidad_proceso < hilos * 2) {
				hilos = 1;
			}

			System.out.println("hilos: " + hilos);
			System.out.println("consultas por hilo: " + limit);
			offesetMap = new HashMap<>();
			int currentOffset = 0;
			for (int i = 0; i < hilos; i++) {
				offesetMap.put("RadicacionComparendosTask-thread-" + i, currentOffset);
				currentOffset = currentOffset + limit;
			}

			Runnable run = new Runnable() {
				@Override
				public void run() {

					RadicacionComparendosTask tarea = new RadicacionComparendosTask();
					tarea.procesarComparendos();
				}
			};

			for (int i = 0; i < hilos; i++) {
				Thread hilos = new Thread(run, "RadicacionComparendosTask-thread-" + i);
				hilos.start();

			}
		}

	}

	public void procesarComparendos() {

		try {

			Connection con = ICONOTECHConnection.getConnection();
			PreparedStatement comparendos = null;

			comparendos = con
					.prepareStatement("select * from transito.radicar_comparendos_temporal order by campo1 asc LIMIT "
							+ limit + " OFFSET " + offesetMap.get(Thread.currentThread().getName()) + ";");
			System.out.println("Hilo: " + Thread.currentThread().getName()
					+ " Query: select * from transito.radicar_comparendos_temporal order by campo1 asc LIMIT " + limit
					+ " OFFSET " + offesetMap.get(Thread.currentThread().getName()) + ";");

			ResultSet comparendosRs = comparendos.executeQuery();

			long current = 0;
			while (comparendosRs.next()) {
				try {
					current = System.currentTimeMillis();
					HashMap<String, String> registro = new HashMap<String, String>();

					for (int i = 1; i <= 59; i++) {
						registro.put("campo" + i, comparendosRs.getString(i));
					}
					guardarComparendo(con, registro, 1);

				} catch (Exception e) {
					e.printStackTrace();
				}

				current = System.currentTimeMillis() - current;
				logProcess(Thread.currentThread().getName(), current);
			}

			try {
				comparendosRs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static synchronized void logProcess(String thread, long time) {
		double porc = (procesados / cantidad_proceso) * 100;
		System.out.println(new Date() + " - " + thread + " => " + procesados + " comparendos, proceso: "
				+ ((int) Math.ceil(porc)) + "%, rata(seg): " + (time / 1000));
		procesados++;
	}

	private Comparendo guardarComparendo(Connection con, HashMap<String, String> simit, int cliente)
			throws SQLException {

		Comparendo comparendo = new Comparendo();
		comparendo.setComparendo(simit.get("campo1"));
		comparendo.setFecha_comparendo(IconoUtilidades.parseFecha(simit.get("campo2")));
		comparendo.setHora_comparendo(IconoUtilidades.parseHora(simit.get("campo3")));
		comparendo.setDireccion_comparendo(simit.get("campo4"));
		comparendo.setDivipo_comparendo(IconoUtilidades.parseInt(simit.get("campo5")));
		comparendo.setLocalizacion(simit.get("campo6"));
		comparendo.setPlaca_vehiculo(simit.get("campo7"));
		comparendo.setDivipo_matricula(simit.get("campo8"));
		comparendo.setTipo_vehículo(simit.get("campo9"));
		comparendo.setTipo_servicio(simit.get("campo10"));
		comparendo.setRadio_acción(simit.get("campo11"));
		comparendo.setModalidad(simit.get("campo12"));
		comparendo.setPasajeros(simit.get("campo13"));
		String id = simit.get("campo14");
		id = id.replace(".", "");
		comparendo.setIdentificacion_infractor(id);
		comparendo.setTipo_identificacion_infractor(IconoUtilidades.parseInt(simit.get("campo15")));
		// Se crea el objeto persona para el core
		Persona persona = new Persona();
		persona.setFecha_registro(comparendo.getFecha_comparendo());
		persona.setIdentificacion(comparendo.getIdentificacion_infractor());
		persona.setTipoIdentificacion(comparendo.getTipo_identificacion_infractor());
		persona.setNombres(simit.get("campo16"));
		persona.setApellidos(simit.get("campo17"));
		comparendo.setEdad_infractor(simit.get("campo18"));
		persona.setDireccion(simit.get("campo19"));
		persona.setEmail(simit.get("campo20"));
		persona.setNumeroContacto(simit.get("campo21"));
		persona.setCiudadID(IconoUtilidades.parseInt(simit.get("campo22")));
		comparendo.setLicencia_infractor(simit.get("campo23"));
		comparendo.setCategoria_licencia_infractor(simit.get("campo24"));
		comparendo.setSecretaria_licencia_infractor(IconoUtilidades.parseInt(simit.get("campo25")));
		comparendo.setFecha_vencimiento_licencia(IconoUtilidades.parseFecha(simit.get("campo26")));
		comparendo.setTipo_infractor(IconoUtilidades.parseInt(simit.get("campo27")));
		comparendo.setLicencia_transito_infractor(simit.get("campo28"));
		comparendo.setDivipo_licencia_transito(IconoUtilidades.parseInt(simit.get("campo29")));
		comparendo.setIdentificacion_propietario(simit.get("campo30"));
		comparendo.setTipo_identificacion_propietario(IconoUtilidades.parseInt(simit.get("campo31")));
		comparendo.setNombre_propietario(simit.get("campo32"));
		comparendo.setEmpresa_propietario(simit.get("campo33"));
		comparendo.setNit_propietario(simit.get("campo34"));
		comparendo.setTarjeta_operacion(simit.get("campo35"));
		comparendo.setPlaca_agente(simit.get("campo36"));
		comparendo.setObservaciones_agente(simit.get("campo37"));
		comparendo.setFuga(simit.get("campo38"));
		comparendo.setAccidente(simit.get("campo39"));
		comparendo.setInmovilizacion(simit.get("campo40"));
		comparendo.setNombre_patio(simit.get("campo41"));
		comparendo.setDireccion_patio(simit.get("campo42"));
		comparendo.setNumero_grua(simit.get("campo43"));
		comparendo.setPlaca_agente(simit.get("campo44"));
		comparendo.setConsecutivo_inmovilizacion(simit.get("campo45"));
		comparendo.setIdentificacion_testigo(simit.get("campo46"));
		comparendo.setNombre_testigo(simit.get("campo47"));
		comparendo.setDireccion_testigo(simit.get("campo48"));
		comparendo.setTelefono_testigo(simit.get("campo49"));
		comparendo.setValor(IconoUtilidades.parseInt(simit.get("campo50")));
		comparendo.setValor_adicional(IconoUtilidades.parseInt(simit.get("campo51")));
		comparendo.setSecretaria(Integer.parseInt(simit.get("campo52")));
		comparendo.setEstado_simit(Integer.parseInt(simit.get("campo53")));
		comparendo.setPolca(simit.get("campo54"));
		comparendo.setCodigo_infraccion(simit.get("campo55"));
		comparendo.setValor_infraccion(IconoUtilidades.parseInt(simit.get("campo56")));
		String campo57 = simit.get("campo57");
		if (campo57.equals("S") || campo57.equals("N")) {
			comparendo.setGrado_alcoholemia("");
			comparendo.setFotomulta(campo57);
			comparendo.setFecha_notificacion(IconoUtilidades.parseFecha(simit.get("campo58")));
		} else {
			comparendo.setGrado_alcoholemia(campo57);
			comparendo.setFotomulta(simit.get("campo58"));
			comparendo.setFecha_notificacion(IconoUtilidades.parseFecha(simit.get("campo59")));
		}

		comparendo.setCliente(cliente);
		try {
			PersonaDao dao = new PersonaDao();
			dao.saveOrUpdatePersona(con, persona);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			ComparendoDao dao = new ComparendoDao();
			dao.guardarComparendo(con, comparendo);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return comparendo;
	}

}
