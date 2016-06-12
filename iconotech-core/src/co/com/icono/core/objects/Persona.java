package co.com.icono.core.objects;

import java.util.Date;

public class Persona {
	private Date fecha_registro;
	private String identificacion;
	private int tipoIdentificacion;
	private String nombres;
	private String apellidos;
	private String numeroContacto;
	private String direccion;
	private int ciudadID;
	private String ciudad_direccion;
	private String email;
	
	public String getCiudad_direccion() {
		return ciudad_direccion;
	}
	public void setCiudad_direccion(String ciudad_direccion) {
		this.ciudad_direccion = ciudad_direccion;
	}
	public String getIdentificacion() {
		return identificacion;
	}
	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}
	public int getTipoIdentificacion() {
		return tipoIdentificacion;
	}
	public void setTipoIdentificacion(int tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}
	public String getNombres() {
		return nombres;
	}
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getNumeroContacto() {
		return numeroContacto;
	}
	public void setNumeroContacto(String numeroContacto) {
		this.numeroContacto = numeroContacto;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public int getCiudadID() {
		return ciudadID;
	}
	public void setCiudadID(int ciudadID) {
		this.ciudadID = ciudadID;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getFecha_registro() {
		return fecha_registro;
	}
	public void setFecha_registro(Date fecha_registro) {
		this.fecha_registro = fecha_registro;
	}
	
	
	
}
