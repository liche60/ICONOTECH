package co.com.icono.cobro.mandamiento;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import co.com.icono.cobro.mandamiento.objects.Dummy;
import co.com.icono.cobro.mandamiento.objects.MandamientoTransitoDataBean;
import co.com.icono.cobro.mandamiento.objects.MandamientoTransitoDetalleDataBean;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class MandamientoBuilder {

	public static void main(String[] args) {

	}

	private JasperReport jasperReport;
	private Map<String, Object> initialParams;
	private static MandamientoBuilder instance = null;
	private int cliente;

	private MandamientoBuilder(int cliente) throws JRException {
		this.cliente = cliente;
		jasperReport = JasperCompileManager
				.compileReport("plantillas/notificacion_mandamiento/" + cliente + "/NMC.jrxml");
		initialParams = new HashMap<String, Object>();
		initialParams.put("logo", "plantillas/notificacion_mandamiento/" + cliente + "/logo.jpg");
		initialParams.put("firma", "plantillas/notificacion_mandamiento/" + cliente + "/firma.jpg");
		String pathToRel = "plantillas/notificacion_mandamiento/" + cliente + "/NMD.jasper";
		initialParams.put("detalle", pathToRel);

	}

	public static MandamientoBuilder getInstance(int cliente) throws JRException {
		if (instance == null)
			instance = new MandamientoBuilder(cliente);
		return instance;
	}

	public void build(MandamientoTransitoDataBean mandamiento) throws JRException {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.putAll(initialParams);
		parameters.put("nombre", mandamiento.getNombre_deudor());
		parameters.put("id", mandamiento.getCedula_deudor());
		parameters.put("direccion", mandamiento.getDireccion_deudor());
		parameters.put("tipo_id", mandamiento.getTipo_id_deudor());
		parameters.put("ciudad", mandamiento.getCiudad_deudor());

		ArrayList<MandamientoTransitoDetalleDataBean> data = mandamiento.getDetalle();
		parameters.put("DATASET_DS", data);

		ArrayList<Dummy> dummy = new ArrayList<>();
		dummy.add(new Dummy("1"));
		JRBeanCollectionDataSource dummyDataSource = new JRBeanCollectionDataSource(dummy);
		parameters.put("COL_DS", dummyDataSource);

		JREmptyDataSource ds = new JREmptyDataSource();

		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, ds);

		JasperExportManager.exportReportToPdfFile(jasperPrint,
				"output/notificacion_mandamiento/" + cliente + "/" + mandamiento.getRadicado() + ".pdf");

	}

}
