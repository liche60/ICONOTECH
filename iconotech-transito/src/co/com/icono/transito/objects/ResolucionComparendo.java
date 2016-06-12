package co.com.icono.transito.objects;

import java.util.Date;

public class ResolucionComparendo {

	private String resolucion;
	private Date fecha_resolucion;
	private int tipo_resolucion;
	private String resolucion_anterior;
	private Date fecha_hasta;
	private String radicado_comparendo;
	private String comparendo;

	public void setComparendo(String comparendo) {
		this.comparendo = comparendo;
	}
	public String getComparendo() {
		return comparendo;
	}
	public String getResolucion() {
		return resolucion;
	}

	public void setResolucion(String resolucion) {
		this.resolucion = resolucion;
	}

	public Date getFecha_resolucion() {
		return fecha_resolucion;
	}

	public void setFecha_resolucion(Date fecha_resolucion) {
		this.fecha_resolucion = fecha_resolucion;
	}

	public int getTipo_resolucion() {
		return tipo_resolucion;
	}

	public void setTipo_resolucion(int tipo_resolucion) {
		this.tipo_resolucion = tipo_resolucion;
	}

	public String getResolucion_anterior() {
		return resolucion_anterior;
	}

	public void setResolucion_anterior(String resolucion_anterior) {
		this.resolucion_anterior = resolucion_anterior;
	}

	public Date getFecha_hasta() {
		return fecha_hasta;
	}

	public void setFecha_hasta(Date fecha_hasta) {
		this.fecha_hasta = fecha_hasta;
	}

	public String getRadicado_comparendo() {
		return radicado_comparendo;
	}

	public void setRadicado_comparendo(String radicado_comparendo) {
		this.radicado_comparendo = radicado_comparendo;
	}

	//
	//

}

