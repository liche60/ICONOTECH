package co.com.icono.ramajudicial;

//05001233100019980241400 Este radicado tiene una actuación que no fué notificada (OJO)

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import co.com.icono.ramajudicial.objects.Actuacion;
import co.com.icono.ramajudicial.objects.Ciudad;
import co.com.icono.ramajudicial.objects.Ciudades;
import co.com.icono.ramajudicial.objects.DatosProceso;
import co.com.icono.ramajudicial.objects.Entidad;
import co.com.icono.ramajudicial.objects.Entidades;
import co.com.icono.ramajudicial.objects.Radicado;
import co.com.icono.ramajudicial.parse.ParseCiudades;
import co.com.icono.ramajudicial.parse.ParseDatosProceso;
import co.com.icono.ramajudicial.parse.ParseEntidades;
import co.com.icono.ramajudicial.util.ProcesosUtil;

public class RamaJudicialClient {

	private final String USER_AGENT = "Mozilla/5.0";
	private static HashMap<String, String> parametros = new HashMap<>();
	// private static HashMap<String, Entidades> entidadesxciudad = new
	// HashMap<>();
	private static Connection connection = null;

	private static String IP_BD = "localhost";

	public static boolean TEST = false;
	// private static final SimpleDateFormat dateOu = new
	// SimpleDateFormat("yyyy/MM/dd");

	public static void main(String[] args) throws Exception {
		if (TEST) {
			IP_BD = "52.5.168.98";
			args[0] = "11001|0306|05001231500019950124601";
		}
		RamaJudicialClient.init(args);
	}

	public static void init(String[] args) throws Exception {

		Connection con = RamaJudicialClient.getConnection();

		RamaJudicialClient http = new RamaJudicialClient();

		PreparedStatement existeCiudad = con.prepareStatement("select * from sgl.ciudad where id_ciudad=?;");
		PreparedStatement insertCiudad = con.prepareStatement("insert into sgl.ciudad (id_ciudad,nombre) values(?,?);");
		PreparedStatement existeEntidad = con
				.prepareStatement("select * from sgl.entidad where id_entidad=? and id_ciudad=?;");
		PreparedStatement insertEntidad = con
				.prepareStatement("insert into sgl.entidad (id_entidad,id_ciudad,query,entidad) values(?,?,?,?);");
		PreparedStatement insertProceso = con.prepareStatement(
				"insert into sgl.proceso (radicado,despacho,ponente,tipo,clase,recurso,ubicacion_expediente,demandante,demandado,ciudad,entidad,r_municipio,r_juzgado,r_especialidad,r_consecutivo_despacho,r_año,r_consecutivo_radicado,r_consecutivo_recurso) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		PreparedStatement updateLastActProceso = con.prepareStatement(
				"update sgl.proceso set ultima_actualizacion=?,ciudad=?,entidad=? where proceso_id=?");
		PreparedStatement insertActuacion = con.prepareStatement(
				"insert into sgl.actuacion (proceso_id,radicado,fecha_actuacion,actuacion,anotacion,fecha_inicia_termino,fecha_finaliza_termino,fecha_registro) values  (?,?,?,?,?,?,?,?)");
		PreparedStatement existProceso = con.prepareStatement(
				"select proceso_id,ultima_actualizacion,ciudad,entidad from sgl.proceso where radicado=? and despacho=? and ponente=? and tipo=? and clase=? and recurso=? and demandante=? and demandado=?;");
		PreparedStatement existActuacion = con.prepareStatement(
				"select actuacion_id from sgl.actuacion where proceso_id=? and actuacion=? and anotacion=? and fecha_registro=?;");

		/*
		 * radicado valor_txt NOT NULL, despacho txt_corto NOT NULL, ponente
		 * txt_corto NOT NULL, tipo txt_corto NOT NULL, clase txt_corto NOT
		 * NULL, recurso txt_corto NOT NULL, ubicacion_expediente txt_medio NOT
		 * NULL, demandante txt_largo NOT NULL, demandado txt_largo NOT NULL,
		 * r_municipio bigint, r_juzgado bigint, r_especialidad bigint,
		 * r_consecutivo_despacho bigint, "r_año" bigint, r_consecutivo_radicado
		 * bigint, r_consecutivo_recurso bigint, entidad bigint, ciudad bigint,
		 */

		StringTokenizer res = new StringTokenizer(args[0], ",");

		while (res.hasMoreTokens()) {
			boolean refresh = true;
			long time = System.currentTimeMillis();
			String params = res.nextToken();
			StringTokenizer tk = new StringTokenizer(params, "|");
			String ciudadParam = tk.nextToken();
			long ciudadParamLong = Long.parseLong(ciudadParam);
			String entidadParam = tk.nextToken();
			long entidadParamLong = Long.parseLong(entidadParam);
			String radicadoParam = tk.nextToken();
			if (refresh) {
				existeCiudad.setLong(1, ciudadParamLong);
				ResultSet existeCiudadrs = existeCiudad.executeQuery();
				if (existeCiudadrs.next()) {

					existeEntidad.setLong(1, entidadParamLong);
					existeEntidad.setLong(2, ciudadParamLong);
					ResultSet existeEntidadrs = existeEntidad.executeQuery();
					if (existeEntidadrs.next()) {

						String query = existeEntidadrs.getString("query");
						String key = entidadParam + "-" + ciudadParam;
						parametros.put(key, query);
						refresh = false;
					}
				}

				if (refresh) {

					List<Ciudad> ciudades = http.getCiudades().getCiudades();
					for (int i = 0; i < ciudades.size(); i++) {
						Ciudad ciudadObj = ciudades.get(i);
						existeCiudad.setLong(1, ciudadObj.getId());
						existeCiudadrs = existeCiudad.executeQuery();
						if (!existeCiudadrs.next()) {

							insertCiudad.setLong(1, ciudadObj.getId());
							insertCiudad.setString(2, ciudadObj.getCiudad());
							insertCiudad.executeUpdate();
							// nueva ciudad
							System.out.println("[" + ProcesosUtil.getCurrentDateTime() + "] [NOTIFICACION-03] "
									+ ciudadObj.getId());
						}
						List<Entidad> entidades = ciudadObj.getEntidades().getEntidades();
						for (int j = 0; j < entidades.size(); j++) {
							Entidad entidadObj = entidades.get(j);
							existeEntidad.setLong(1, entidadObj.getId());
							existeEntidad.setLong(2, ciudadObj.getId());
							ResultSet existeEntidadrs = existeEntidad.executeQuery();
							if (!existeEntidadrs.next()) {
								insertEntidad.setLong(1, entidadObj.getId());
								insertEntidad.setLong(2, ciudadObj.getId());
								insertEntidad.setString(3, entidadObj.getQuery());
								insertEntidad.setString(4, entidadObj.getEntidad());
								insertEntidad.executeUpdate();
								String key = ProcesosUtil.padString(entidadObj.getId() + "", "0", 4) + "-"
										+ ProcesosUtil.padString(ciudadObj.getId() + "", "0", 5);
								parametros.put(key, entidadObj.getQuery());
								// nueva entidad
								System.out.println("[" + ProcesosUtil.getCurrentDateTime() + "] [NOTIFICACION-04] "
										+ ciudadObj.getId() + "," + entidadObj.getId());
							}
						}
					}
					refresh = false;
				}
			}
			try {
				DatosProceso proceso = http.consultarProceso(radicadoParam, ciudadParam, entidadParam);
				Radicado radicado = ProcesosUtil.getRadicado(radicadoParam);
				// radicado=? and despacho=? and ponente=? and tipo=? and
				// clase=? and recurso=? and demandante=? and demandado=?
				existProceso.setString(1, proceso.getRadicado());
				existProceso.setString(2, proceso.getDespacho());
				existProceso.setString(3, proceso.getPonente());
				existProceso.setString(4, proceso.getTipo());
				existProceso.setString(5, proceso.getClase());
				existProceso.setString(6, proceso.getRecurso());
				existProceso.setString(7, proceso.getDemandante());
				existProceso.setString(8, proceso.getDemandado());
				ResultSet existProcesoRs = existProceso.executeQuery();
				long proceso_id = 0;
				long ciudad = 0;
				long entidad = 0;
				Date ultima_actualizacion = null;
				if (existProcesoRs.next()) {
					proceso_id = existProcesoRs.getLong("proceso_id");
					ultima_actualizacion = existProcesoRs.getDate("ultima_actualizacion");
					ciudad = existProcesoRs.getLong("ciudad");
					entidad = existProcesoRs.getLong("entidad");
				} else {
					// radicado,despacho,ponente,tipo,clase,recurso,ubicacion_expediente,demandante,demandado,ciudad,entidad,r_municipio,r_juzgado,r_especialidad,r_consecutivo_despacho,r_año,r_consecutivo_radicado,r_consecutivo_recurso
					insertProceso.setString(1, proceso.getRadicado());
					insertProceso.setString(2, proceso.getDespacho());
					insertProceso.setString(3, proceso.getPonente());
					insertProceso.setString(4, proceso.getTipo());
					insertProceso.setString(5, proceso.getClase());
					insertProceso.setString(6, proceso.getRecurso());
					insertProceso.setString(7, proceso.getUbicacionExpediente());
					insertProceso.setString(8, proceso.getDemandante());
					insertProceso.setString(9, proceso.getDemandado());
					insertProceso.setLong(10, ciudadParamLong);
					insertProceso.setLong(11, entidadParamLong);

					insertProceso.setLong(12, radicado.getIdmunicipio());
					insertProceso.setLong(13, radicado.getIdjuzgado());
					insertProceso.setLong(14, radicado.getIdespecialidad());
					insertProceso.setLong(15, radicado.getConsecutivo_despacho());
					insertProceso.setLong(16, radicado.getAño());
					insertProceso.setLong(17, radicado.getConsecutivo_radicado());
					insertProceso.setLong(18, radicado.getConsecutivo_recurso());

					insertProceso.executeUpdate();
					existProcesoRs = existProceso.executeQuery();
					if (existProcesoRs.next()) {
						proceso_id = existProcesoRs.getLong("proceso_id");
						ciudad = existProcesoRs.getLong("ciudad");
						entidad = existProcesoRs.getLong("entidad");

						if (radicado.getConsecutivo_recurso() > 0) {
							// nuevo recurso para proceso
							System.out.println(
									"[" + ProcesosUtil.getCurrentDateTime() + "] [NOTIFICACION-05] " + proceso_id);
						} else {
							// nuevo proceso
							System.out.println(
									"[" + ProcesosUtil.getCurrentDateTime() + "] [NOTIFICACION-00] " + proceso_id);
						}
					}
				}
				Iterator<Actuacion> actuaciones = proceso.getActuaciones().iterator();
				boolean firstAct = true;
				while (actuaciones.hasNext()) {
					try {
						Actuacion actuacion = (Actuacion) actuaciones.next();
						// select count(actuacion_id) from sgl.proceso where
						// proceso_id=? and actuacion=? and anotacion=? and
						// fecha_registro=?;

						existActuacion.setLong(1, proceso_id);
						existActuacion.setString(2, actuacion.getActuacion());
						existActuacion.setString(3, actuacion.getAnotacion());
						existActuacion.setDate(4, ultima_actualizacion);
						// la actuacion no ha sido almacenada para este proceso
						ResultSet rs = existActuacion.executeQuery();
						if (rs.next()) {
							break;
						}
						// insert into sgl.actuacion
						// (proceso_id,radicado,fecha_actuacion,actuacion,anotacion,fecha_inicia_termino,fecha_finaliza_termino,fecha_registro)
						// values (?,?,?,?,?,?,?,?)

						insertActuacion.setLong(1, proceso_id);
						insertActuacion.setString(2, proceso.getRadicado());
						insertActuacion.setDate(3, ProcesosUtil.getDate(actuacion.getFechaActuacion()));
						insertActuacion.setString(4, actuacion.getActuacion());
						insertActuacion.setString(5, actuacion.getAnotacion());
						insertActuacion.setDate(6, ProcesosUtil.getDate(actuacion.getFechaIniciaTermino()));
						insertActuacion.setDate(7, ProcesosUtil.getDate(actuacion.getFechaFinalizaTermino()));
						ultima_actualizacion = ProcesosUtil.getDate(actuacion.getFechaRegistro());
						insertActuacion.setDate(8, ultima_actualizacion);
						insertActuacion.executeUpdate();

						existActuacion.setLong(1, proceso_id);
						existActuacion.setString(2, actuacion.getActuacion());
						existActuacion.setString(3, actuacion.getAnotacion());
						existActuacion.setDate(4, ultima_actualizacion);
						rs = existActuacion.executeQuery();
						long actuacion_id = 0;
						if (rs.next()) {
							actuacion_id = rs.getLong("actuacion_id");
							// nueva actuación del proceso
							System.out.println("[" + ProcesosUtil.getCurrentDateTime() + "] [NOTIFICACION-01] "
									+ proceso_id + "," + actuacion_id);
						}

						if (firstAct) {
							try {
								updateLastActProceso.setDate(1, ultima_actualizacion);
								if (ciudadParamLong != ciudad || entidadParamLong != entidad) {
									// nueva ubicación del proceso
									System.out.println("[" + ProcesosUtil.getCurrentDateTime() + "] [NOTIFICACION-02] "
											+ proceso_id + "," + entidadParamLong + "," + ciudadParamLong);
								}
								updateLastActProceso.setLong(2, ciudadParamLong);
								updateLastActProceso.setLong(3, entidadParamLong);
								updateLastActProceso.setLong(4, proceso_id);
								updateLastActProceso.executeUpdate();
								firstAct = false;
							} catch (Exception e) {
								System.out.println("[" + ProcesosUtil.getCurrentDateTime()
										+ "] [ERROR-04] Error actualizanddo el proceso: " + proceso_id);
								e.printStackTrace();
							}
						}
					} catch (Exception e) {
						System.out.println("[" + ProcesosUtil.getCurrentDateTime()
								+ "] [ERROR-02] Error insertando actuaciones del proceso" + proceso_id);
						e.printStackTrace();
					}
				}
				time = System.currentTimeMillis() - time;
				System.out.println("[" + ProcesosUtil.getCurrentDateTime() + "] [OK] resolución " + radicadoParam
						+ " procesada en " + (time / 1000) + " segundos.");
			} catch (Exception e) {
				System.out.println("[" + ProcesosUtil.getCurrentDateTime()
						+ "] [ERROR-01] error procesando la resolución " + radicadoParam);
				e.printStackTrace();
			}
		}

		try {
			existeCiudad.close();
		} catch (Exception e) {
		}
		try {
			insertCiudad.close();
		} catch (Exception e) {
		}
		try {
			existeEntidad.close();
		} catch (Exception e) {
		}
		try {
			insertEntidad.close();
		} catch (Exception e) {
		}
		try {
			insertProceso.close();
		} catch (Exception e) {
		}
		try {
			updateLastActProceso.close();
		} catch (Exception e) {
		}
		try {
			insertActuacion.close();
		} catch (Exception e) {
		}
		try {
			existProceso.close();
		} catch (Exception e) {
		
		}
		try {
			existActuacion.close();
		} catch (Exception e) {
		
		}
		try {
			con.close();
		} catch (Exception e) {
		
		}

	}

	private static Connection getConnection() {
		try {
			if (connection == null) {
				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection("jdbc:postgresql://" + IP_BD + ":5432/sgl", "postgres",
						"postgres");
			}
			return connection;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private Ciudades getCiudades() throws Exception {
		String url = "http://procesos.ramajudicial.gov.co/consultaprocesos/";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes("");
		wr.flush();
		wr.close();
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		Document doc = Jsoup.parse(response.toString());
		Elements select = doc.select("select");
		Ciudades ciudades = ParseCiudades.parse(select.first());

		List<Ciudad> listaCiudades = ciudades.getCiudades();
		for (Ciudad ciudad : listaCiudades) {
			con = (HttpURLConnection) obj.openConnection();
			con.setRequestProperty("User-Agent", USER_AGENT);
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			con.setDoOutput(true);
			wr = new DataOutputStream(con.getOutputStream());
			String padded = ProcesosUtil.padString(ciudad.getId() + "", "0", 5);
			String urlParameters = ProcessRequest.processEntidades(padded);
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();
			in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			doc = Jsoup.parse(response.toString());
			select = doc.select("select");
			Entidades entidades = ParseEntidades.parse(select.get(1));
			ciudad.setEntidades(entidades);

		}
		return ciudades;
	}

	private DatosProceso consultarProceso(String radicado, String ciudad, String entidadStr) throws Exception {

		String url = "http://procesos.ramajudicial.gov.co/consultaprocesos/";
		URL obj = new URL(url);
		String entidad = parametros.get(entidadStr + "-" + ciudad);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		String urlParameters = ProcessRequest.processRadicado(radicado, ciudad, entidad, entidadStr);
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		Document doc = Jsoup.parse(response.toString());
		Elements tables = doc.select("table");
		try {
			DatosProceso datosProcesos = ParseDatosProceso.parse(tables);
			return datosProcesos;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

}