package co.com.icono.tareas.transito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import co.com.icono.core.database.ICONOTECHConnection;
import co.com.icono.simit.client.SimitClient;
import co.com.icono.simit.client.objectos.ComparendoFromSimit;
import co.com.icono.simit.client.objectos.ResolucionFromSimit;
import co.com.icono.simit.client.objectos.SimitQuery;

public class ConsultaEstadoSimitTask {

	private static int limit = 0;
	private static double procesados = 0;
	private static double cantidad_proceso = 0;
	private static int hilos = 500;
	private static HashMap<String, Integer> offesetMap;

	public static void main(String[] args) {
		Connection con;

		try {
			con = ICONOTECHConnection.getConnection();
			PreparedStatement extraer_proceso = con.prepareStatement("select simit.diferencial_personas_simit();");
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
				offesetMap.put("ConsultaEstadoSimitTask-thread-" + i, currentOffset);
				currentOffset = currentOffset + limit;
			}

			Runnable run = new Runnable() {
				@Override
				public void run() {
					ConsultaEstadoSimitTask tarea = new ConsultaEstadoSimitTask();
					tarea.procesarComparendosSimit();
				}
			};

			for (int i = 0; i < hilos; i++) {
				Thread hilos = new Thread(run, "ConsultaEstadoSimitTask-thread-" + i);
				hilos.start();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public void procesarComparendosSimit() {

		try {
			SimitClient client = new SimitClient();
			Connection con = ICONOTECHConnection.getConnection();

			PreparedStatement personas = con.prepareStatement(
					"select tipo_identificacion,identificacion from simit.procesar_simit_temporal order by identificacion desc LIMIT "
							+ limit + " OFFSET " + offesetMap.get(Thread.currentThread().getName()) + ";");
			PreparedStatement insertCompSimit = con.prepareStatement(
					"insert into simit.consulta_simit_comparendos values (?,?,?,?,?,?,?,?,?,?,?,?,?,now()) ");
			PreparedStatement insertResoSimit = con.prepareStatement(
					"insert into simit.consulta_simit_resoluciones values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,now()) ");

			PreparedStatement insertQuerySimit = con
					.prepareStatement("insert into simit.historico_procesados_simit values (?,?,now()) ");

			long current = 0;
			ResultSet personasRs = personas.executeQuery();
			while (personasRs.next()) {
				current = System.currentTimeMillis();
				int tipo = personasRs.getInt(1);
				String id = personasRs.getString(2);
				try {
					SimitQuery query = client.querySimit(id, tipo);

					if (query != null) {
						Iterator<ComparendoFromSimit> itcomparendo = query.getComparendoList().iterator();
						try {
							while (itcomparendo.hasNext()) {
								ComparendoFromSimit comparendo = (ComparendoFromSimit) itcomparendo.next();
								insertCompSimit.setString(1, id);
								insertCompSimit.setInt(2, tipo);
								insertCompSimit.setString(3, comparendo.getComparendo());
								insertCompSimit.setString(4, comparendo.getSecretaria());
								insertCompSimit.setString(5, comparendo.getFechaComparendo());
								insertCompSimit.setString(6, comparendo.getFechaNotificacion());
								insertCompSimit.setString(7, comparendo.getNombreInfractor());
								insertCompSimit.setString(8, comparendo.getEstado());
								insertCompSimit.setString(9, comparendo.getInfraccion());
								insertCompSimit.setString(10, comparendo.getValorMulta() + "");
								insertCompSimit.setString(11, comparendo.getValorAdicional() + "");
								insertCompSimit.setString(12, comparendo.getTotal() + "");
								insertCompSimit.setString(13, comparendo.getValorPagar() + "");
								insertCompSimit.executeUpdate();
								// System.out.println("nuevo comparendo");
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						Iterator<ResolucionFromSimit> itresolucion = query.getResolucionesList().iterator();
						try {

							while (itresolucion.hasNext()) {
								ResolucionFromSimit resolucion = (ResolucionFromSimit) itresolucion.next();
								insertResoSimit.setString(1, id);
								insertResoSimit.setInt(2, tipo);
								insertResoSimit.setString(3, resolucion.getResolucion());
								insertResoSimit.setString(4, resolucion.getFechaResolucion());
								insertResoSimit.setString(5, resolucion.getComparendo());
								insertResoSimit.setString(6, resolucion.getFechaComparendo());
								insertResoSimit.setString(7, resolucion.getSecretaria());
								insertResoSimit.setString(8, resolucion.getNombreInfractor());
								insertResoSimit.setString(9, resolucion.getEstado());
								insertResoSimit.setString(10, resolucion.getInfraccion());
								insertResoSimit.setString(11, resolucion.getValorMulta() + "");
								insertResoSimit.setString(12, resolucion.getMora() + "");
								insertResoSimit.setString(13, resolucion.getValorAdicional() + "");
								insertResoSimit.setString(14, resolucion.getValorPagar() + "");
								insertResoSimit.executeUpdate();
								// System.out.println("nueva resolucion");
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					try {
						insertQuerySimit.setInt(1, tipo);
						insertQuerySimit.setString(2, id);
						insertQuerySimit.executeUpdate();

					} catch (Exception e) {
						e.printStackTrace();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				current = System.currentTimeMillis() - current;
				logProcess(Thread.currentThread().getName(), current);

			}

			try {
				insertCompSimit.close();
			} catch (Exception e) {
				insertQuerySimit.close();
			}
			try {
				insertCompSimit.close();
			} catch (Exception e) {
			}
			try {
				insertResoSimit.close();
			} catch (Exception e) {
			}
			try {
				personasRs.close();
			} catch (Exception e) {
			}
			try {
				personas.close();
			} catch (Exception e) {
			}
			try {
				con.close();
			} catch (Exception e) {
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}

	}

	public static synchronized void logProcess(String thread, long time) {
		double porc = (procesados / cantidad_proceso) * 100;
		System.out.println(new Date() + " - " + thread + " => " + procesados + " consultas, proceso: "
				+ ((int) Math.ceil(porc)) + "%, rata(seg): " + (time / 1000));
		procesados++;
	}

}
