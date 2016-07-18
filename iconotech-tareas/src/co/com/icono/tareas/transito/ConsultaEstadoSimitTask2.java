package co.com.icono.tareas.transito;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import co.com.icono.core.database.ICONOTECHConnection;
import co.com.icono.simit.client.SimitClient;
import co.com.icono.simit.client.objectos.ComparendoFromSimit;
import co.com.icono.simit.client.objectos.ResolucionFromSimit;
import co.com.icono.simit.client.objectos.SimitQuery;

public class ConsultaEstadoSimitTask2 {

	private ArrayList<Busqueda> busqueda = new ArrayList<>();

	public static void main(String[] args) {
		Connection con;

		try {
			con = ICONOTECHConnection.getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

	}

	public String readFile(String filename) throws IOException {
		String content = null;
		BufferedReader br;
		try {

			br = new BufferedReader(new FileReader(filename));

			try {
				String x;
				while ((x = br.readLine()) != null) {
					String[] campos = x.split("|");
					Busqueda b = new Busqueda();
					String comparendo = campos[0];
					String cedula = campos[23];
					String tstr = campos[0];
					int tipo = 1;
					if (tstr.equals("E")) {
						tipo = 3;
					}
					if (tstr.equals("N")) {
						tipo = 4;
					}
					b.setTipo_cedula(tipo);
					b.setCedula(cedula);
					b.setComparendo(comparendo);
					busqueda.add(b);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return content;
	}

	public void procesarComparendosSimit() {

		try {
			SimitClient client = new SimitClient();
			Connection con = ICONOTECHConnection.getConnection();

			PreparedStatement insertCompSimit = con.prepareStatement(
					"insert into simit.consulta_simit_comparendos values (?,?,?,?,?,?,?,?,?,?,?,?,?,now()) ");
			PreparedStatement insertResoSimit = con.prepareStatement(
					"insert into simit.consulta_simit_resoluciones values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,now()) ");

			PreparedStatement insertQuerySimit = con
					.prepareStatement("insert into simit.historico_procesados_simit values (?,?,now()) ");

			long current = 0;
			Iterator<Busqueda> it = busqueda.iterator();
			while (it.hasNext()) {
				Busqueda busqueda = it.next();
				current = System.currentTimeMillis();
				int tipo = busqueda.getTipo_cedula();
				String id = busqueda.getCedula();
				String compa = busqueda.getComparendo();
				try {
					SimitQuery query = client.querySimit(id, tipo);

					if (query != null) {
						Iterator<ComparendoFromSimit> itcomparendo = query.getComparendoList().iterator();
						try {
							while (itcomparendo.hasNext()) {
								ComparendoFromSimit comparendo = (ComparendoFromSimit) itcomparendo.next();
								if (comparendo.getComparendo().equals(compa)) {
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
									System.out.println("nuevo comparendo");
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						Iterator<ResolucionFromSimit> itresolucion = query.getResolucionesList().iterator();
						try {

							while (itresolucion.hasNext()) {
								ResolucionFromSimit resolucion = (ResolucionFromSimit) itresolucion.next();
								if (resolucion.getComparendo().equals(compa)) {
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
									System.out.println("nueva resolucion");
								}
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
				con.close();
			} catch (Exception e) {
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}

	}

	class Busqueda {

		String comparendo;
		int tipo_cedula;
		String cedula;

		public String getComparendo() {
			return comparendo;
		}

		public void setComparendo(String comparendo) {
			this.comparendo = comparendo;
		}

		public int getTipo_cedula() {
			return tipo_cedula;
		}

		public void setTipo_cedula(int tipo_cedula) {
			this.tipo_cedula = tipo_cedula;
		}

		public String getCedula() {
			return cedula;
		}

		public void setCedula(String cedula) {
			this.cedula = cedula;
		}

	}

}
