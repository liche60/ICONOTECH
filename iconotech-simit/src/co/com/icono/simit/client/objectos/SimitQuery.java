package co.com.icono.simit.client.objectos;

import java.util.List;

public class SimitQuery {

	private List<ResolucionFromSimit> resolucionesList;
	private List<AcuerdoPagoFromSimit> acuerdoPagoList;
	private List<ComparendoFromSimit> comparendoList;

	public List<ResolucionFromSimit> getResolucionesList() {
		return resolucionesList;
	}

	public void setResolucionesList(List<ResolucionFromSimit> resolucionesList) {
		this.resolucionesList = resolucionesList;
	}

	public List<AcuerdoPagoFromSimit> getAcuerdoPagoList() {
		return acuerdoPagoList;
	}

	public void setAcuerdoPagoList(List<AcuerdoPagoFromSimit> acuerdoPagoList) {
		this.acuerdoPagoList = acuerdoPagoList;
	}

	public List<ComparendoFromSimit> getComparendoList() {
		return comparendoList;
	}

	public void setComparendoList(List<ComparendoFromSimit> comparendoList) {
		this.comparendoList = comparendoList;
	}

}
