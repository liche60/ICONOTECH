package co.com.icono.ramajudicial.parse;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import co.com.icono.ramajudicial.objects.Actuacion;
import co.com.icono.ramajudicial.objects.DatosProceso;

public class ParseDatosProceso {

	/**
	 * @param elements
	 * @return
	 */
	public static DatosProceso parse(Elements elements) {
		Element tabla = elements.select("table").first();
		// Informaci�n Radicaci�n del Proceso
		DatosProceso datosProceso = new DatosProceso();
		
		datosProceso.setDespacho(tabla.getElementById("lblJuzgadoActual").getElementsByIndexEquals(1).text());
		datosProceso.setRadicado((tabla.getElementById("hdfNumRadicaion").val()));
		datosProceso.setPonente(tabla.getElementById("lblPonente").getElementsByIndexEquals(0).text());

		// Clasificaci�n del Proceso
		datosProceso.setTipo(tabla.getElementById("lblTipo").getElementsByIndexEquals(0).text());
		datosProceso.setClase(tabla.getElementById("lblClase").getElementsByIndexEquals(0).text());
		datosProceso.setRecurso(tabla.getElementById("lblRecurso").getElementsByIndexEquals(0).text());
		datosProceso.setUbicacionExpediente(tabla.getElementById("lblUbicacion").getElementsByIndexEquals(0).text());

		// Contenido de Radicaci�n
		datosProceso.setDemandante(tabla.getElementById("lblNomDemandante").getElementsByIndexEquals(0).text());
		datosProceso.setDemandado(tabla.getElementById("lblNomDemandado").getElementsByIndexEquals(0).text());
		//System.out.println(elements.select("table"));
		Element tablaActuaciones = elements.select("table").get(6);


		//RECORDAR IMPLEMENTAR LA GESTI�N DE DOCUMENTOS DEL PROCESO EN LA TABLA26 CUANDO EXISTA}
		
		
		if (tablaActuaciones.getElementById("rptDocumentos_lbNombreDoc_0") != null) {
			tablaActuaciones = elements.select("table").get(8);
			//System.out.println(tablaActuaciones);
		}
		Elements registros = tablaActuaciones.getElementsByClass("tr_contenido");
		int id = 0;
		List<Actuacion> actuaciones = new ArrayList<>();
		
		for (Element element : registros) {
			actuaciones.add(ParseActuacion.parse(element, id));
			id++;
		}
		datosProceso.setActuaciones(actuaciones);

		return datosProceso;
	}

}
