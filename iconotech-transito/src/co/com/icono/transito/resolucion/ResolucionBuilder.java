package co.com.icono.transito.resolucion;

import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

public class ResolucionBuilder {

	public static void main(String[] args) {
		
	}

	private JasperReport jasperReport;
	private Map<String, Object> initialParams;
	private static ResolucionBuilder instance = null;
	private int cliente;

	private ResolucionBuilder(int cliente) throws JRException {
		this.cliente = cliente;
		jasperReport = JasperCompileManager.compileReport("plantillas/resolucion/" + cliente + "/RSP1.jrxml");
		initialParams = new HashMap<String, Object>();
		initialParams.put("logo", "plantillas/resolucion/" + cliente + "/logo.jpg");
		initialParams.put("firma", "plantillas/resolucion/" + cliente + "/firma_inspector.jpg");

	}

	public static ResolucionBuilder getInstance(int cliente) throws JRException {
		if (instance == null)
			instance = new ResolucionBuilder(cliente);
		return instance;
	}

	public void build() throws JRException {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.putAll(initialParams);
		parameters.put("nombre", "CARLOS ANDRES LOPEZ MORALES");
		JREmptyDataSource ds = new JREmptyDataSource();
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, ds);
		JasperExportManager.exportReportToPdfFile(jasperPrint, "output/resolucion/" + cliente + "/RSexample.pdf");

	}

}
