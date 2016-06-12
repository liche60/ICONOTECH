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
import co.com.icono.transito.dao.ResolucionComparendoDao;
import co.com.icono.transito.objects.Comparendo;
import co.com.icono.transito.objects.ResolucionComparendo;

public class RadicarResolucionesComparendosTask {

	private static double cantidad_proceso = 0;
	private static int limit = 0;
	private static double procesados = 0;
	private static int hilos = 500;
	private static HashMap<String, Integer> offesetMap;
	


	public static void main(String[] args) {

		Connection con;

		try {
			con = ICONOTECHConnection.getConnection();
			PreparedStatement comparendos = con.prepareStatement("select  transito.diferencial_resoluciones();");
			ResultSet comparendosRs = comparendos.executeQuery();
			if (comparendosRs.next()) {
				cantidad_proceso = comparendosRs.getInt(1);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		System.out.println(cantidad_proceso);
		if (cantidad_proceso > 0) {
			if (cantidad_proceso < hilos * 2) {
				hilos = 1;
			}
			double tmp1 = (cantidad_proceso / hilos);
			limit = (int) Math.ceil(tmp1);

			int currentOffset = 0;
			offesetMap = new HashMap<>();
			for (int i = 0; i < hilos; i++) {
				offesetMap.put("RadicarResolucionesComparendosTask-thread-" + i, currentOffset);
				currentOffset = currentOffset + limit;
			}

			System.out.println("hilos: " + hilos);
			System.out.println("consultas por hilo: " + limit);

			Runnable run = new Runnable() {
				@Override
				public void run() {

					RadicarResolucionesComparendosTask tarea = new RadicarResolucionesComparendosTask();
					tarea.procesarComparendos();
				}
			};

			for (int i = 0; i < hilos; i++) {
				Thread hilos = new Thread(run, "RadicarResolucionesComparendosTask-thread-" + i);
				hilos.start();
			}
		}

	}

	public void procesarComparendos() {

		try {
			Connection con = ICONOTECHConnection.getConnection();
			PreparedStatement comparendos = null;

			comparendos = con.prepareStatement("select * from transito.resoluciones_temporal order by campo1 asc LIMIT "
					+ limit + " OFFSET " + offesetMap.get(Thread.currentThread().getName()) + ";");

			ResultSet resolucionesRs = comparendos.executeQuery();
			// System.out.println(comparendosRs.getFetchSize());
			// ArrayList<HashMap<String, String>> resoluciones_simit = new
			// ArrayList<HashMap<String, String>>();
			long current = 0;
			while (resolucionesRs.next()) {
				try {
					current = System.currentTimeMillis();
					HashMap<String, String> registro = new HashMap<String, String>();

					for (int i = 1; i <= 25; i++) {
						registro.put("campo" + i, resolucionesRs.getString(i));
					}
					guardarResolucion(con, registro, 1);
					// resoluciones_simit.add(registro);

				} catch (Exception e) {
					e.printStackTrace();
				}

				current = System.currentTimeMillis() - current;
				logProcess(Thread.currentThread().getName(), current);
			}
			try {
				resolucionesRs.close();
			} catch (Exception e) {
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

	private void guardarResolucion(Connection con, HashMap<String, String> simit, int cliente) throws SQLException {
		ResolucionComparendo resolucion = new ResolucionComparendo();
		resolucion.setResolucion(simit.get("campo1"));
		resolucion.setResolucion_anterior(simit.get("campo2"));
		resolucion.setFecha_resolucion(IconoUtilidades.parseFecha(simit.get("campo3")));
		resolucion.setTipo_resolucion(IconoUtilidades.parseInt(simit.get("campo4")));
		resolucion.setFecha_hasta(IconoUtilidades.parseFecha(simit.get("campo5")));
		resolucion.setComparendo(simit.get("campo6"));
		String id = simit.get("campo8");
		id = id.replace(".", "");
		int tipo_id = IconoUtilidades.parseInt(simit.get("campo9"));
		// Se crea el objeto persona para el core
		Persona persona = new Persona();
		persona.setFecha_registro(IconoUtilidades.parseFecha(simit.get("campo3")));
		persona.setIdentificacion(id);
		persona.setTipoIdentificacion(tipo_id);
		persona.setDireccion(simit.get("campo12"));
		persona.setNumeroContacto(simit.get("campo13"));
		persona.setEmail(null);
		persona.setCiudadID(IconoUtilidades.parseInt(simit.get("campo14")));
		try {
			PersonaDao dao = new PersonaDao();
			dao.saveOrUpdatePersona(con, persona);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Comparendo comparendo = new Comparendo();
			comparendo.setIdentificacion_infractor(persona.getIdentificacion());
			comparendo.setTipo_identificacion_infractor(persona.getTipoIdentificacion());
			comparendo.setComparendo(resolucion.getComparendo());
			ComparendoDao dao = new ComparendoDao();
			String radicado = dao.getRadicadoComparendo(con, comparendo);
			if(radicado == null){
				resolucion.setRadicado_comparendo("N");
			}else{
				resolucion.setRadicado_comparendo(radicado);	
			}
			
			ResolucionComparendoDao resolucionDao = new ResolucionComparendoDao();
			resolucionDao.guardarResolucion(con, resolucion);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
