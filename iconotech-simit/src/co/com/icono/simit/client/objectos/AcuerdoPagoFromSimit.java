package co.com.icono.simit.client.objectos;


public class AcuerdoPagoFromSimit {

	// Resolución Fecha Resolución Comparendo Fecha Comparendo Secretaía
	// Nombre Infractor Estado Infracción Valor Multa Valor Adicional Total
	// Saldo Valor A Pagar
	private String identificacion;
	private String tipo;
	private String resolucion;
	private String fechaResolucion;
	private String comparendo;
	private String fechaComparendo;
	private String secretaria;
	private String nombreInfractor;
	private String estado;
	private String infraccion;
	private double valorMulta;
	private double total;
	private double valorAdicional;
	private double saldo;
	private double valorPagar;

	public String getResolucion() {
		return resolucion;
	}

	public void setResolucion(String resolucion) {
		this.resolucion = resolucion;
	}

	public String getFechaResolucion() {
		return fechaResolucion;
	}

	public void setFechaResolucion(String fechaResolucion) {
		this.fechaResolucion = fechaResolucion;
	}

	public String getComparendo() {
		return comparendo;
	}

	public void setComparendo(String comparendo) {
		this.comparendo = comparendo;
	}

	public String getFechaComparendo() {
		return fechaComparendo;
	}

	public void setFechaComparendo(String fechaComparendo) {
		this.fechaComparendo = fechaComparendo;
	}

	public String getSecretaria() {
		return secretaria;
	}

	public void setSecretaria(String secretaria) {
		this.secretaria = secretaria;
	}

	public String getNombreInfractor() {
		return nombreInfractor;
	}

	public void setNombreInfractor(String nombreInfractor) {
		this.nombreInfractor = nombreInfractor;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getInfraccion() {
		return infraccion;
	}

	public void setInfraccion(String infraccion) {
		this.infraccion = infraccion;
	}

	public double getValorMulta() {
		return valorMulta;
	}

	public void setValorMulta(double valorMulta) {
		this.valorMulta = valorMulta;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public double getValorAdicional() {
		return valorAdicional;
	}

	public void setValorAdicional(double valorAdicional) {
		this.valorAdicional = valorAdicional;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public double getValorPagar() {
		return valorPagar;
	}

	public void setValorPagar(double valorPagar) {
		this.valorPagar = valorPagar;
	}

	public String getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
