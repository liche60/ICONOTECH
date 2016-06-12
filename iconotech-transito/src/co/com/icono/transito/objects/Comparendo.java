package co.com.icono.transito.objects;

import java.util.Date;

public class Comparendo {

	//Radicado:
	private int cliente;
	private int tipo_proceso = 1;
	
	
	private String comparendo;
	private Date fecha_comparendo;
	private Date hora_comparendo;
	private String direccion_comparendo;
	private int divipo_comparendo;
	private String localizacion;
	private String placa_vehiculo;
	private String divipo_matricula;
	private String tipo_vehículo;
	private String tipo_servicio;
	private String radio_acción;
	private String modalidad;
	private String pasajeros;
	private String identificacion_infractor;
	private int tipo_identificacion_infractor;
	private String edad_infractor;
	private String licencia_infractor;
	private String categoria_licencia_infractor;
	private int secretaria_licencia_infractor;
	private Date fecha_vencimiento_licencia;
	private int tipo_infractor;
	private String licencia_transito_infractor;
	private int divipo_licencia_transito;
	private String identificacion_propietario;
	private int tipo_identificacion_propietario;
	private String nombre_propietario;
	private String empresa_propietario;
	private String nit_propietario;
	private String tarjeta_operacion;
	private String placa_agente;
	private String observaciones_agente;
	private String fuga;
	private String accidente;
	private String inmovilizacion;
	private String nombre_patio;
	private String direccion_patio;
	private String numero_grua;
	private String placa_grua;
	private String consecutivo_inmovilizacion;
	private String identificacion_testigo;
	private String nombre_testigo;
	private String direccion_testigo;
	private String telefono_testigo;
	private int valor;
	private int valor_adicional;
	private int secretaria;
	private int estado_simit;
	private String polca;
	private String codigo_infraccion;
	private int valor_infraccion;
	private String grado_alcoholemia;
	private String fotomulta;
	private Date fecha_notificacion;

	public String getComparendo() {
		return comparendo;
	}
	public void setComparendo(String comparendo) {
		this.comparendo = comparendo;
	}
	public Date getFecha_comparendo() {
		return fecha_comparendo;
	}
	public void setFecha_comparendo(Date fecha_comparendo) {
		this.fecha_comparendo = fecha_comparendo;
	}
	public Date getHora_comparendo() {
		return hora_comparendo;
	}
	public void setHora_comparendo(Date hora_comparendo) {
		this.hora_comparendo = hora_comparendo;
	}
	public String getDireccion_comparendo() {
		return direccion_comparendo;
	}
	public void setDireccion_comparendo(String direccion_comparendo) {
		this.direccion_comparendo = direccion_comparendo;
	}
	public int getDivipo_comparendo() {
		return divipo_comparendo;
	}
	public void setDivipo_comparendo(int divipo_comparendo) {
		this.divipo_comparendo = divipo_comparendo;
	}
	public String getLocalizacion() {
		return localizacion;
	}
	public void setLocalizacion(String localizacion) {
		this.localizacion = localizacion;
	}
	public String getPlaca_vehiculo() {
		return placa_vehiculo;
	}
	public void setPlaca_vehiculo(String placa_vehiculo) {
		this.placa_vehiculo = placa_vehiculo;
	}
	public String getIdentificacion_infractor() {
		return identificacion_infractor;
	}
	public void setIdentificacion_infractor(String identificacion_infractor) {
		this.identificacion_infractor = identificacion_infractor;
	}
	public String getLicencia_infractor() {
		return licencia_infractor;
	}
	public void setLicencia_infractor(String licencia_infractor) {
		this.licencia_infractor = licencia_infractor;
	}
	public String getCategoria_licencia_infractor() {
		return categoria_licencia_infractor;
	}
	public void setCategoria_licencia_infractor(String categoria_licencia_infractor) {
		this.categoria_licencia_infractor = categoria_licencia_infractor;
	}
	public int getSecretaria_licencia_infractor() {
		return secretaria_licencia_infractor;
	}
	public void setSecretaria_licencia_infractor(int secretaria_licencia_infractor) {
		this.secretaria_licencia_infractor = secretaria_licencia_infractor;
	}
	public Date getFecha_vencimiento_licencia() {
		return fecha_vencimiento_licencia;
	}
	public void setFecha_vencimiento_licencia(Date fecha_vencimiento_licencia) {
		this.fecha_vencimiento_licencia = fecha_vencimiento_licencia;
	}
	public int getTipo_infractor() {
		return tipo_infractor;
	}
	public void setTipo_infractor(int tipo_infractor) {
		this.tipo_infractor = tipo_infractor;
	}
	public String getLicencia_transito_infractor() {
		return licencia_transito_infractor;
	}
	public void setLicencia_transito_infractor(String licencia_transito_infractor) {
		this.licencia_transito_infractor = licencia_transito_infractor;
	}
	public int getDivipo_licencia_transito() {
		return divipo_licencia_transito;
	}
	public void setDivipo_licencia_transito(int divipo_licencia_transito) {
		this.divipo_licencia_transito = divipo_licencia_transito;
	}
	public String getIdentificacion_propietario() {
		return identificacion_propietario;
	}
	public void setIdentificacion_propietario(String identificacion_propietario) {
		this.identificacion_propietario = identificacion_propietario;
	}
	public int getTipo_identificacion_propietario() {
		return tipo_identificacion_propietario;
	}
	public void setTipo_identificacion_propietario(int tipo_identificacion_propietario) {
		this.tipo_identificacion_propietario = tipo_identificacion_propietario;
	}
	public String getEmpresa_propietario() {
		return empresa_propietario;
	}
	public void setEmpresa_propietario(String empresa_propietario) {
		this.empresa_propietario = empresa_propietario;
	}
	public String getNit_propietario() {
		return nit_propietario;
	}
	public void setNit_propietario(String nit_propietario) {
		this.nit_propietario = nit_propietario;
	}
	public String getTarjeta_operacion() {
		return tarjeta_operacion;
	}
	public void setTarjeta_operacion(String tarjeta_operacion) {
		this.tarjeta_operacion = tarjeta_operacion;
	}
	public String getPlaca_agente() {
		return placa_agente;
	}
	public void setPlaca_agente(String placa_agente) {
		this.placa_agente = placa_agente;
	}
	public String getObservaciones_agente() {
		return observaciones_agente;
	}
	public void setObservaciones_agente(String observaciones_agente) {
		this.observaciones_agente = observaciones_agente;
	}
	public String getFuga() {
		return fuga;
	}
	public void setFuga(String fuga) {
		this.fuga = fuga;
	}
	public String getAccidente() {
		return accidente;
	}
	public void setAccidente(String accidente) {
		this.accidente = accidente;
	}
	public String getInmovilizacion() {
		return inmovilizacion;
	}
	public void setInmovilizacion(String inmovilizacion) {
		this.inmovilizacion = inmovilizacion;
	}
	public String getNombre_patio() {
		return nombre_patio;
	}
	public void setNombre_patio(String nombre_patio) {
		this.nombre_patio = nombre_patio;
	}
	public String getDireccion_patio() {
		return direccion_patio;
	}
	public void setDireccion_patio(String direccion_patio) {
		this.direccion_patio = direccion_patio;
	}
	public String getNumero_grua() {
		return numero_grua;
	}
	public void setNumero_grua(String numero_grua) {
		this.numero_grua = numero_grua;
	}
	public String getPlaca_grua() {
		return placa_grua;
	}
	public void setPlaca_grua(String placa_grua) {
		this.placa_grua = placa_grua;
	}
	public String getConsecutivo_inmovilizacion() {
		return consecutivo_inmovilizacion;
	}
	public void setConsecutivo_inmovilizacion(String consecutivo_inmovilizacion) {
		this.consecutivo_inmovilizacion = consecutivo_inmovilizacion;
	}
	public String getIdentificacion_testigo() {
		return identificacion_testigo;
	}
	public void setIdentificacion_testigo(String identificacion_testigo) {
		this.identificacion_testigo = identificacion_testigo;
	}
	public String getTelefono_testigo() {
		return telefono_testigo;
	}
	public void setTelefono_testigo(String telefono_testigo) {
		this.telefono_testigo = telefono_testigo;
	}
	public int getValor() {
		return valor;
	}
	public void setValor(int valor) {
		this.valor = valor;
	}
	public int getValor_adicional() {
		return valor_adicional;
	}
	public void setValor_adicional(int valor_adicional) {
		this.valor_adicional = valor_adicional;
	}
	public int getSecretaria() {
		return secretaria;
	}
	public void setSecretaria(int secretaria) {
		this.secretaria = secretaria;
	}
	public int getEstado_simit() {
		return estado_simit;
	}
	public void setEstado_simit(int estado_simit) {
		this.estado_simit = estado_simit;
	}
	public String getPolca() {
		return polca;
	}
	public void setPolca(String polca) {
		this.polca = polca;
	}
	public String getCodigo_infraccion() {
		return codigo_infraccion;
	}
	public void setCodigo_infraccion(String codigo_infraccion) {
		this.codigo_infraccion = codigo_infraccion;
	}
	public int getValor_infraccion() {
		return valor_infraccion;
	}
	public void setValor_infraccion(int valor_infraccion) {
		this.valor_infraccion = valor_infraccion;
	}
	public String getGrado_alcoholemia() {
		return grado_alcoholemia;
	}
	public void setGrado_alcoholemia(String grado_alcoholemia) {
		this.grado_alcoholemia = grado_alcoholemia;
	}
	public String getFotomulta() {
		return fotomulta;
	}
	public void setFotomulta(String fotomulta) {
		this.fotomulta = fotomulta;
	}
	public Date getFecha_notificacion() {
		return fecha_notificacion;
	}
	public void setFecha_notificacion(Date fecha_notificacion) {
		this.fecha_notificacion = fecha_notificacion;
	}
	public int getTipo_identificacion_infractor() {
		return tipo_identificacion_infractor;
	}
	public void setTipo_identificacion_infractor(int tipo_identificacion_infractor) {
		this.tipo_identificacion_infractor = tipo_identificacion_infractor;
	}
	public String getDivipo_matricula() {
		return divipo_matricula;
	}
	public void setDivipo_matricula(String divipo_matricula) {
		this.divipo_matricula = divipo_matricula;
	}
	public String getTipo_vehículo() {
		return tipo_vehículo;
	}
	public void setTipo_vehículo(String tipo_vehículo) {
		this.tipo_vehículo = tipo_vehículo;
	}
	public String getTipo_servicio() {
		return tipo_servicio;
	}
	public void setTipo_servicio(String tipo_servicio) {
		this.tipo_servicio = tipo_servicio;
	}
	public String getRadio_acción() {
		return radio_acción;
	}
	public void setRadio_acción(String radio_acción) {
		this.radio_acción = radio_acción;
	}
	public String getModalidad() {
		return modalidad;
	}
	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
	}
	public String getPasajeros() {
		return pasajeros;
	}
	public void setPasajeros(String pasajeros) {
		this.pasajeros = pasajeros;
	}
	public String getEdad_infractor() {
		return edad_infractor;
	}
	public void setEdad_infractor(String edad_infractor) {
		this.edad_infractor = edad_infractor;
	}
	public String getNombre_propietario() {
		return nombre_propietario;
	}
	public void setNombre_propietario(String nombre_propietario) {
		this.nombre_propietario = nombre_propietario;
	}
	public String getNombre_testigo() {
		return nombre_testigo;
	}
	public void setNombre_testigo(String nombre_testigo) {
		this.nombre_testigo = nombre_testigo;
	}
	public String getDireccion_testigo() {
		return direccion_testigo;
	}
	public void setDireccion_testigo(String direccion_testigo) {
		this.direccion_testigo = direccion_testigo;
	}
	public int getTipo_proceso() {
		return tipo_proceso;
	}
	public void setCliente(int cliente) {
		this.cliente = cliente;
	}
	public int getCliente() {
		return cliente;
	}
	
	


}

