package co.com.icono.ramajudicial.objects;

import java.util.List;

public class DatosProceso {

	private String despacho;
	private String radicado;
	private String ponente;
	private String tipo;
	private String clase;
	private String recurso;
	private String ubicacionExpediente;
	private String demandante;
	private String demandado;
	private List<Actuacion> actuaciones;

	public List<Actuacion> getActuaciones() {
		return actuaciones;
	}

	public void setActuaciones(List<Actuacion> actuaciones) {
		this.actuaciones = actuaciones;
	}

	public String getDespacho() {
		return despacho;
	}

	public void setDespacho(String despacho) {
		this.despacho = despacho;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getRadicado() {
		return radicado;
	}

	public void setRadicado(String radicado) {
		this.radicado = radicado;
	}

	public String getPonente() {
		return ponente;
	}

	public void setPonente(String ponente) {
		this.ponente = ponente;
	}

	public String getClase() {
		return clase;
	}

	public void setClase(String clase) {
		this.clase = clase;
	}

	public String getRecurso() {
		return recurso;
	}

	public void setRecurso(String recurso) {
		this.recurso = recurso;
	}

	public String getUbicacionExpediente() {
		return ubicacionExpediente;
	}

	public void setUbicacionExpediente(String ubicacionExpediente) {
		this.ubicacionExpediente = ubicacionExpediente;
	}

	public String getDemandante() {
		return demandante;
	}

	public void setDemandante(String demandante) {
		this.demandante = demandante;
	}

	public String getDemandado() {
		return demandado;
	}

	public void setDemandado(String demandado) {
		this.demandado = demandado;
	}

}
