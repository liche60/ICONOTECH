package co.com.icono.cienaga.integracion;

import java.io.StringReader;
import java.nio.charset.Charset;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import co.com.icono.cienaga.integracion.comparendosws.ComparendosSoapProxy;

public class IntegracionComparendosCienaga {
	
	public static void main(String[] args) throws RemoteException {
		ComparendosSoapProxy p = new ComparendosSoapProxy();
		String res = p.getCompFecha("SAP", "12/5/2016");
		System.out.println(res);
	}

	public static void revisarActualizacionesCienaga(String fecha, Connection con) throws Exception {
		ComparendosSoapProxy p = new ComparendosSoapProxy();

		String res = p.getCompFecha("SAP", fecha);
		// System.out.println(res);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		PreparedStatement comparendoStmt = con.prepareStatement(
				"insert into simit.envio_simit_comparendos values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		try {
			builder = factory.newDocumentBuilder();
			Document document = builder.parse(new InputSource(new StringReader(res)));
			NodeList lista = document.getChildNodes();
			NodeList comparendos = lista.item(0).getChildNodes();
			for (int i = 2; i < comparendos.getLength(); i++) {
				String comparendo = comparendos.item(i).getTextContent().trim();
				try {
				comparendo = new String(comparendo.getBytes(), Charset.forName("ISO-8859-15"));
				} catch (Exception e) {
					comparendo  = "";
				}
				if (comparendo.length() > 0) {
					guardarNovedad(comparendo, comparendoStmt);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			comparendoStmt.close();
		} catch (Exception e) {
		}

	}

	private static void guardarNovedad(String comparendo, PreparedStatement comparendoStmt) throws SQLException {

		String[] campos = comparendo.split(",");
		for (int i = 1; i < campos.length; i++) {
			String campo = campos[i];
			System.out.println("campo" + i + ": " + campo);
			comparendoStmt.setString(i, campo);
		}

		comparendoStmt.executeUpdate();

	}

}
