package co.com.icono.ramajudicial.parse;

import org.jsoup.nodes.Element;

import co.com.icono.ramajudicial.objects.Actuacion;

public class ParseActuacion {

	public static Actuacion parse(Element registro, int id) {

		Actuacion actuacion = new Actuacion();

		actuacion.setFechaActuacion(
				registro.getElementById("rptActuaciones_lblFechaActuacion_" + id).getElementsByIndexEquals(0).text());
		actuacion.setActuacion(
				registro.getElementById("rptActuaciones_lblActuacion_" + id).getElementsByIndexEquals(0).text());
		actuacion.setAnotacion(
				registro.getElementById("rptActuaciones_lblAnotacion_" + id).getElementsByIndexEquals(0).text());
		actuacion.setFechaIniciaTermino(
				registro.getElementById("rptActuaciones_lblFechaInicio_" + id).getElementsByIndexEquals(0).text());
		actuacion.setFechaFinalizaTermino(
				registro.getElementById("rptActuaciones_lblFechaFin_" + id).getElementsByIndexEquals(0).text());
		actuacion.setFechaRegistro(
				registro.getElementById("rptActuaciones_lblFechaRegistro_" + id).getElementsByIndexEquals(0).text());

		return actuacion;
	}

}
