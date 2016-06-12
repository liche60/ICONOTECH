package co.com.icono.ramajudicial;

import java.io.Serializable;
import java.util.Date;

public class SGLMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;

	private Date fecha;

	private String message;

	private long idradicado;

	private long idactuacion;

	private long entidad;

	private long ciudad;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getIdradicado() {
		return idradicado;
	}

	public void setIdradicado(long idradicado) {
		this.idradicado = idradicado;
	}

	public long getIdactuacion() {
		return idactuacion;
	}

	public void setIdactuacion(long idactuacion) {
		this.idactuacion = idactuacion;
	}

	public long getEntidad() {
		return entidad;
	}

	public void setEntidad(long entidad) {
		this.entidad = entidad;
	}

	public long getCiudad() {
		return ciudad;
	}

	public void setCiudad(long ciudad) {
		this.ciudad = ciudad;
	}

}
