package co.com.icono.cobro.mandamiento.objects;

import java.util.ArrayList;

public class MandamientoTransitoDataBean {

	private String radicado;
	private String cedula_deudor;
	private int tipo_id_deudor;
	private String nombre_deudor;
	private String apellido_deudor;
	private String direccion_deudor;
	private String ciudad_deudor;
	private ArrayList<MandamientoTransitoDetalleDataBean> detalle;

	public ArrayList<MandamientoTransitoDetalleDataBean> getDetalle() {
		return detalle;
	}

	public String getRadicado() {
		return radicado;
	}

	public void setRadicado(String radicado) {
		this.radicado = radicado;
	}

	public void setDetalle(ArrayList<MandamientoTransitoDetalleDataBean> detalle) {
		this.detalle = detalle;
	}

	public String getCedula_deudor() {
		return cedula_deudor;
	}

	public void setCedula_deudor(String cedula_deudor) {
		this.cedula_deudor = cedula_deudor;
	}

	public int getTipo_id_deudor() {
		return tipo_id_deudor;
	}

	public void setTipo_id_deudor(int tipo_id_deudor) {
		this.tipo_id_deudor = tipo_id_deudor;
	}

	public String getNombre_deudor() {
		return nombre_deudor;
	}

	public void setNombre_deudor(String nombre_deudor) {
		this.nombre_deudor = nombre_deudor;
	}

	public String getApellido_deudor() {
		return apellido_deudor;
	}

	public void setApellido_deudor(String apellido_deudor) {
		this.apellido_deudor = apellido_deudor;
	}

	public String getDireccion_deudor() {
		return direccion_deudor;
	}

	public void setDireccion_deudor(String direccion_deudor) {
		this.direccion_deudor = direccion_deudor;
	}

	public String getCiudad_deudor() {
		return ciudad_deudor;
	}

	public void setCiudad_deudor(String ciudad_deudor) {
		this.ciudad_deudor = ciudad_deudor;
	}
	

}
