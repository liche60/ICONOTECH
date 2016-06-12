package co.com.icono.tareas.cobro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import co.com.icono.cobro.mandamiento.MandamientoBuilder;
import co.com.icono.cobro.mandamiento.objects.MandamientoTransitoDataBean;
import co.com.icono.cobro.mandamiento.objects.MandamientoTransitoDetalleDataBean;
import co.com.icono.cobro.mandamiento.radicacion.RadicacionMandamiento;
import co.com.icono.core.dao.PersonaDao;
import co.com.icono.core.database.ICONOTECHConnection;
import co.com.icono.core.objects.Persona;


public class MandamientosTransitoPendientesTask {
	
	/*
	 * 
	 * insert into cobro.mandamientos_transito_temporal select
	 * b.radicado,a.identificacion,b.tipo_identificacion_infractor,a.comparendo,
	 * b.fecha_comparendo,a.nombre_infractor from
	 * simit.consulta_simit_resoluciones a,transito.comparendo_radicado b where
	 * (a.identificacion,tipo::bigint,a.comparendo,a.fecha_comparendo) not in (
	 * select identificacion,tipo_identificacion,comparendo,fecha_comparendo
	 * from cienaga.enviados_lecta ) and
	 * (a.identificacion,tipo::bigint,a.comparendo) in ( select
	 * identificacion_infractor,tipo_identificacion_infractor,comparendo from
	 * transito.comparendo_radicado ) and a.estado = 'Pendiente de pago' and
	 * a.secretaria like '%enag%' and a.comparendo = b.comparendo and
	 * a.identificacion = b.identificacion_infractor and a.tipo::bigint =
	 * b.tipo_identificacion_infractor;
	 */

	private static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

	private static int limit = 0;
	private static double procesados = 0;
	private static double cantidad_proceso = 0;
	private static int cliente = 1;
	private static int hilos = 500;
	private static HashMap<String, Integer> offesetMap;

	public static void main(String[] args) {
		Connection con;

		try {
			con = ICONOTECHConnection.getConnection();
			PreparedStatement extraer_proceso = con.prepareStatement(
					"select count(distinct (cedula_deudor,tipo_id_deudor)) from cobro.mandamientos_transito_temporal;");
			ResultSet extraer_procesoRs = extraer_proceso.executeQuery();
			if (extraer_procesoRs.next()) {
				cantidad_proceso = extraer_procesoRs.getInt(1);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

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
				offesetMap.put("MandamientosTransitoPendientesTask-thread-" + i, currentOffset);
				currentOffset = currentOffset + limit;
			}

			Runnable run = new Runnable() {
				@Override
				public void run() {
					MandamientosTransitoPendientesTask tarea = new MandamientosTransitoPendientesTask();
					tarea.procesarMandamientos();
				}
			};

			for (int i = 0; i < hilos; i++) {
				Thread hilos = new Thread(run, "MandamientosTransitoPendientesTask-thread-" + i);
				hilos.start();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public void procesarMandamientos() {

		try {
			Connection con = ICONOTECHConnection.getConnection();
			PreparedStatement personas = con.prepareStatement(
					"select cedula_deudor,tipo_id_deudor,nombre_deudor from cobro.mandamientos_transito_temporal "
							+ "group by cedula_deudor,tipo_id_deudor,nombre_deudor order by cedula_deudor,tipo_id_deudor desc LIMIT "
							+ limit + " OFFSET " + offesetMap.get(Thread.currentThread().getName()) + ";");

			PreparedStatement comparendos = con.prepareStatement(
					"select comparendo,fecha_comparendo,radicado_proceso from cobro.mandamientos_transito_temporal where tipo_id_deudor=? and cedula_deudor=? order by fecha_comparendo desc");

			PreparedStatement relacion = con
					.prepareStatement("insert into cobro.relacion_procesos_mandamientos values(?,?)");

			long current = 0;
			ResultSet personasRs = personas.executeQuery();
			while (personasRs.next()) {

				current = System.currentTimeMillis();
				String cedula_deudor = personasRs.getString(1);
				int tipo_id_deudor = personasRs.getInt(2);
				Persona persona = new Persona();
				persona.setIdentificacion(cedula_deudor);
				persona.setTipoIdentificacion(tipo_id_deudor);
				persona = new PersonaDao().obtenerMejorUbicacion(con, persona);
				String nombre_deudor = personasRs.getString(3);
				String direccion_deudor = persona.getDireccion();
				String ciudad_direccion = persona.getCiudad_direccion();

				if (direccion_deudor != null && ciudad_direccion != null) {

					MandamientoTransitoDataBean mandamiento = new MandamientoTransitoDataBean();
					mandamiento.setNombre_deudor(nombre_deudor);
					mandamiento.setDireccion_deudor(direccion_deudor);
					mandamiento.setCedula_deudor(cedula_deudor);
					mandamiento.setTipo_id_deudor(tipo_id_deudor);
					mandamiento.setCiudad_deudor(ciudad_direccion);
					comparendos.setInt(1, tipo_id_deudor);
					comparendos.setString(2, cedula_deudor);

					String radicado_mandamiento = RadicacionMandamiento.getNuevoRadicadoMandamiento(con, 1,
							cedula_deudor, tipo_id_deudor);

					mandamiento.setRadicado(radicado_mandamiento);

					ResultSet comparendosRs = comparendos.executeQuery();
					ArrayList<MandamientoTransitoDetalleDataBean> detalle = new ArrayList<>();

					while (comparendosRs.next()) {
						String comparendo = comparendosRs.getString(1);
						String fecha_comparendo = "";
						try {
							Date fecha = comparendosRs.getDate(2);
							fecha_comparendo = dateFormat.format(fecha);
						} catch (Exception e) {
						}
						String radicado_proceso = comparendosRs.getString(3);

						try {
							relacion.setString(1, radicado_proceso);
							relacion.setString(2, radicado_mandamiento);
							relacion.executeUpdate();
						} catch (Exception e) {
							e.printStackTrace();
						}

						detalle.add(new MandamientoTransitoDetalleDataBean(comparendo, fecha_comparendo));
					}
					mandamiento.setDetalle(detalle);
					try {
						MandamientoBuilder.getInstance(cliente).build(mandamiento);
					} catch (Exception e) { 
						e.printStackTrace();
					}
					try {
						comparendosRs.close();
					} catch (Exception e) {
					}
				}
				logProcess(Thread.currentThread().getName(), current);

			}
			try {
				relacion.close();
			} catch (Exception e) {
			}

			try {
				personasRs.close();
			} catch (Exception e) {
			}
			try {
				con.close();
			} catch (Exception e) {
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static synchronized void logProcess(String thread, long time) {
		double porc = (procesados / cantidad_proceso) * 100;
		System.out.println(new Date() + " - " + thread + " => " + procesados + " mandamientos, proceso: "
				+ ((int) Math.ceil(porc)) + "%, rata(seg): " + (time / 1000));
		procesados++;
	}

}
