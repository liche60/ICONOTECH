package co.com.icono.cobro.mandamiento.objects;

public class MandamientoTransitoDetalleDataBean {

	private String numero_comparendo;
	private String fecha_comparendo;
	
	public MandamientoTransitoDetalleDataBean(String numero_comparendo, String fecha_comparendo){
		this.numero_comparendo = numero_comparendo;
		this.fecha_comparendo = fecha_comparendo;
	}

	public String getNumero_comparendo() {
		return numero_comparendo;
	}

	public void setNumero_comparendo(String numero_comparendo) {
		this.numero_comparendo = numero_comparendo;
	}

	public String getFecha_comparendo() {
		return fecha_comparendo;
	}

	public void setFecha_comparendo(String fecha_comparendo) {
		this.fecha_comparendo = fecha_comparendo;
	}

	
}
