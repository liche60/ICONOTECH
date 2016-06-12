package co.com.icono.cobro.mandamiento.objects;

import java.util.Date;

public class Mandamiento {

	private String radicado;
	private Date fecha_radicado;
	private int cliente_id;
	private String identificacion_deudor;
	private int tipo_identificacion_deudor;
	private String id_lote_notificacion;

	public String getRadicado() {
		return radicado;
	}

	public void setRadicado(String radicado) {
		this.radicado = radicado;
	}

	public Date getFecha_radicado() {
		return fecha_radicado;
	}

	public void setFecha_radicado(Date fecha_radicado) {
		this.fecha_radicado = fecha_radicado;
	}

	public int getCliente_id() {
		return cliente_id;
	}

	public void setCliente_id(int cliente_id) {
		this.cliente_id = cliente_id;
	}

	public String getIdentificacion_deudor() {
		return identificacion_deudor;
	}

	public void setIdentificacion_deudor(String identificacion_deudor) {
		this.identificacion_deudor = identificacion_deudor;
	}

	public int getTipo_identificacion_deudor() {
		return tipo_identificacion_deudor;
	}

	public void setTipo_identificacion_deudor(int tipo_identificacion_deudor) {
		this.tipo_identificacion_deudor = tipo_identificacion_deudor;
	}

	public String getId_lote_notificacion() {
		return id_lote_notificacion;
	}

	public void setId_lote_notificacion(String id_lote_notificacion) {
		this.id_lote_notificacion = id_lote_notificacion;
	}

}
