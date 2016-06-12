package co.com.icono.simit.client;

import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.map.HashedMap;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.google.gson.Gson;

import co.com.icono.simit.client.objectos.AcuerdoPagoFromSimit;
import co.com.icono.simit.client.objectos.ComparendoFromSimit;
import co.com.icono.simit.client.objectos.ResolucionFromSimit;
import co.com.icono.simit.client.objectos.SimitQuery;

public class SimitClient {

	private WebDriver driver;
	private String baseUrl;
	private StringBuffer verificationErrors = new StringBuffer();

	static BufferedWriter writecomparendos;
	static BufferedWriter writeacuerdos;
	static BufferedWriter writeresoluciones;


	private static String getTipoFromId(int ti) {
		String tipo = "Cedula";
		if (ti == 2) {
			tipo = "Tarjeta Identidad";
		}
		if (ti == 3) {
			tipo = "Cedula Extranjeria";
		}
		if (ti == 4) {
			tipo = "Nit";
		}
		if (ti == 6) {
			tipo = "Pasaporte";
		}
		if (ti == 7) {
			tipo = "Carne Diplomatico";
		}
		if (ti == 9) {
			tipo = "Cédula Venezolana";
		}
		if (ti == 10) {
			tipo = "Cedula Ecuatoriana";
		}
		return tipo;
	}
	
	
	public SimitQuery querySimit(String id, int tipo) throws Exception {
		SimitClient service = new SimitClient();
		service.setUp();
		String t = getTipoFromId(tipo);
		SimitQuery obj = service.consultaCedula(id, t);
		service.tearDown();
		return obj;
	}

	public SimitQuery querySimit(String id, String tipo) throws Exception {
		SimitClient service = new SimitClient();
		service.setUp();
		SimitQuery obj = service.consultaCedula(id, tipo);
		service.tearDown();
		return obj;
	}

	public String query(String id, String tipo) {
		try {
			StringBuffer result = new StringBuffer();
			SimitClient service = new SimitClient();

			StringTokenizer ids = new StringTokenizer(id, ",");
			StringTokenizer tipos = new StringTokenizer(tipo, ",");
			if (ids.countTokens() == tipos.countTokens()) {
				while (ids.hasMoreElements()) {
					service.setUp();
					String idtk = (String) ids.nextElement();
					String tipostk = (String) tipos.nextElement();
					try {
						Gson gson = new Gson();
						String tmp = gson.toJson(service.consultaCedula(idtk, tipostk));

						if (tmp != null) {
							result.append(tmp);
						}
					} catch (Exception e) {
						System.err.println("Error procesando el registro id: " + idtk + " tipo: " + tipostk);
					}
					service.tearDown();
				}
			} else {
				return null;
			}

			return result.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	} 

	private void setUp() throws Exception {
		DesiredCapabilities dc = new DesiredCapabilities();
		dc.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
		driver = new HtmlUnitDriver(dc) {
			@Override
			protected WebClient newWebClient(BrowserVersion version) {
				WebClient webClient = super.newWebClient(version);

				webClient.getOptions().setThrowExceptionOnScriptError(false);
				webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
				return webClient;
			}

			@Override
			public void setJavascriptEnabled(boolean enableJavascript) {
				super.setJavascriptEnabled(true);
			}
		};

		baseUrl = "https://consulta.simit.org.co/Simit/verificar/contenido_verificar_pago_linea.jsp";

		driver.manage().timeouts().pageLoadTimeout(1000, TimeUnit.SECONDS);

	}

	private SimitQuery consultaCedula(String id, String tipo) throws InterruptedException {

		driver.get(baseUrl);

		String txtCaptcha = driver.findElement(By.id("txtCaptcha")).getAttribute("value");
		new Select(driver.findElement(By.name("tipoDocumento"))).selectByVisibleText(tipo);

		driver.findElement(By.name("identificacion")).sendKeys(id);
		driver.findElement(By.name("txtInput")).sendKeys(txtCaptcha);
		WebElement consultar = (new WebDriverWait(driver, 90))
				.until(ExpectedConditions.elementToBeClickable(By.name("consultar")));
		consultar.click();

		try {
			WebElement estadoCuenta1 = (new WebDriverWait(driver, 5))
					.until(ExpectedConditions.elementToBeClickable(By.id("estadoCuenta1")));
			estadoCuenta1.click();
		} catch (Exception e) {
			return null;
		}

		Iterator<String> it2 = driver.getWindowHandles().iterator();

		while (it2.hasNext()) {
			String string = (String) it2.next();
			WebDriver imp = driver.switchTo().window(string);
			try {
				driver.switchTo().alert().accept();
			} catch (Exception e) {
			}

			if (imp.getTitle().equals("Imprimir Estado Cuenta")) {
				String page = imp.getPageSource();
				Document doc = Jsoup.parse(page);
				Elements tr = doc.select("tr");
				Iterator<org.jsoup.nodes.Element> trs = tr.iterator();
				boolean resolucion = false;
				boolean acuerdoPago = false;
				boolean comparendo = false;
				int campo = 1;
				ResolucionFromSimit resolucionObj = null;
				AcuerdoPagoFromSimit acuerdoPagoObj = null;
				ComparendoFromSimit comparendoObj = null;
				ArrayList<ResolucionFromSimit> resolucionesList = new ArrayList<>();
				ArrayList<AcuerdoPagoFromSimit> acuerdoPagoList = new ArrayList<>();
				ArrayList<ComparendoFromSimit> comparendoList = new ArrayList<>();

				while (trs.hasNext()) {
					org.jsoup.nodes.Element element = (org.jsoup.nodes.Element) trs.next();

					Elements td = element.select("td");

					if (td.size() > 5 && td.size() <= 14) {
						Iterator<org.jsoup.nodes.Element> tds = td.iterator();
						boolean first = true;
						while (tds.hasNext()) {
							org.jsoup.nodes.Element eletd = (org.jsoup.nodes.Element) tds.next();
							Elements tabla = eletd.getElementsByClass("tabla");
							Elements tabla1 = eletd.getElementsByClass("tabla1");
							if (first) {
								if (!eletd.text().equals("") && !eletd.text().equals(" ")
										&& !eletd.text().equals("	")) {
									if (tabla.size() == 1) {
										if (resolucion) {
											resolucionObj.setIdentificacion(id);
											resolucionObj.setTipo(tipo);
											campo = setCampoResolucion(campo, eletd.text(), resolucionObj);

										}
										if (comparendo) {
											comparendoObj.setIdentificacion(id);
											comparendoObj.setTipo(tipo);
											campo = setCampoComparendo(campo, eletd.text(), comparendoObj);

										}
										if (acuerdoPago) {
											acuerdoPagoObj.setIdentificacion(id);
											acuerdoPagoObj.setTipo(tipo);
											campo = setCampoAcuerdoPago(campo, eletd.text(), acuerdoPagoObj);

										}
									}

								}
								first = false;
							} else {
								if (tabla.size() == 1) {
									if (resolucion) {
										resolucionObj.setIdentificacion(id);
										resolucionObj.setTipo(tipo);
										campo = setCampoResolucion(campo, eletd.text(), resolucionObj);

									}
									if (comparendo) {
										comparendoObj.setIdentificacion(id);
										comparendoObj.setTipo(tipo);
										campo = setCampoComparendo(campo, eletd.text(), comparendoObj);

									}
									if (acuerdoPago) {
										acuerdoPagoObj.setIdentificacion(id);
										acuerdoPagoObj.setTipo(tipo);
										campo = setCampoAcuerdoPago(campo, eletd.text(), acuerdoPagoObj);

									}
								}
								if (tabla1.size() == 1) {
									if (eletd.text().equals("Interes Mora")) {

										resolucion = true;
										acuerdoPago = false;
										comparendo = false;
										resolucionObj = new ResolucionFromSimit();
										acuerdoPagoObj = null;
										comparendoObj = null;
									} else {
										if (eletd.text().equals("Saldo")) {

											acuerdoPago = true;
											resolucion = false;
											comparendo = false;
											resolucionObj = null;
											acuerdoPagoObj = new AcuerdoPagoFromSimit();
											comparendoObj = null;
										} else {
											if (eletd.text().equals("F. Notificación")) {

												comparendo = true;
												resolucion = false;
												acuerdoPago = false;
												resolucionObj = null;
												acuerdoPagoObj = null;
												comparendoObj = new ComparendoFromSimit();
											}
										}
									}
								}

							}
						}
						if (resolucion) {
							resolucionesList.add(resolucionObj);
							resolucionObj = new ResolucionFromSimit();
						}
						if (comparendo) {
							comparendoList.add(comparendoObj);
							comparendoObj = new ComparendoFromSimit();
						}
						if (acuerdoPago) {
							acuerdoPagoList.add(acuerdoPagoObj);
							acuerdoPagoObj = new AcuerdoPagoFromSimit();
						}
						campo = 1;
						first = true;
					}
				}

				if (resolucionesList.size() > 0)
					resolucionesList.remove(0);
				if (comparendoList.size() > 0)
					comparendoList.remove(0);
				if (acuerdoPagoList.size() > 0)
					acuerdoPagoList.remove(0);

				SimitQuery query = new SimitQuery();
				query.setResolucionesList(resolucionesList);
				query.setComparendoList(comparendoList);
				query.setAcuerdoPagoList(acuerdoPagoList);
				return query;
			}
		}

		return null;
	}

	private int setCampoResolucion(int campo, String valor, ResolucionFromSimit resolucion) {

		if (campo == 1) {
			resolucion.setResolucion(valor);
			return campo + 1;
		}
		if (campo == 2) {
			resolucion.setFechaResolucion(valor);
			return campo + 1;
		}
		if (campo == 3) {
			resolucion.setComparendo(valor);
			return campo + 1;
		}
		if (campo == 4) {
			resolucion.setFechaComparendo(valor);
			return campo + 1;
		}
		if (campo == 5) {
			resolucion.setSecretaria(valor);
			return campo + 1;
		}
		if (campo == 6) {
			resolucion.setNombreInfractor(valor);
			return campo + 1;
		}
		if (campo == 7) {
			resolucion.setEstado(valor);
			return campo + 1;
		}
		if (campo == 8) {
			if (valor.contains(",") || valor.equals("0")) {
				try {
					valor = valor.replaceAll(",", "");
					resolucion.setValorMulta(Double.parseDouble(valor));
				} catch (Exception e) {
					e.printStackTrace();
				}
				return campo + 2;
			} else {
				resolucion.setInfraccion(valor);
			}
			return campo + 1;
		}
		if (campo == 9) {
			try {
				valor = valor.replaceAll(",", "");
				resolucion.setValorMulta(Double.parseDouble(valor));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return campo + 1;
		}
		if (campo == 10) {
			try {
				valor = valor.replaceAll(",", "");
				resolucion.setMora(Double.parseDouble(valor));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return campo + 1;
		}
		if (campo == 11) {
			try {
				valor = valor.replaceAll(",", "");
				resolucion.setValorAdicional(Double.parseDouble(valor));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return campo + 1;
		}
		if (campo == 12) {
			try {
				valor = valor.replaceAll(",", "");
				resolucion.setValorPagar(Double.parseDouble(valor));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return campo + 1;
		}
		return campo + 1;
	}

	private int setCampoAcuerdoPago(int campo, String valor, AcuerdoPagoFromSimit acuerdoPago) {

		if (campo == 1) {
			acuerdoPago.setResolucion(valor);
			return campo + 1;
		}
		if (campo == 2) {
			acuerdoPago.setFechaResolucion(valor);
			return campo + 1;
		}
		if (campo == 3) {
			acuerdoPago.setComparendo(valor);
			return campo + 1;
		}
		if (campo == 4) {
			acuerdoPago.setFechaComparendo(valor);
			return campo + 1;
		}
		if (campo == 5) {
			acuerdoPago.setSecretaria(valor);
			return campo + 1;
		}
		if (campo == 6) {
			acuerdoPago.setNombreInfractor(valor);
			return campo + 1;
		}
		if (campo == 7) {
			acuerdoPago.setEstado(valor);
			return campo + 1;
		}
		if (campo == 8) {
			if (valor.contains(",") || valor.equals("0")) {
				try {
					valor = valor.replaceAll(",", "");
					acuerdoPago.setValorMulta(Double.parseDouble(valor));
				} catch (Exception e) {
					e.printStackTrace();
				}
				return campo + 2;
			} else {
				acuerdoPago.setInfraccion(valor);
			}
			return campo + 1;
		}
		if (campo == 9) {
			try {
				valor = valor.replaceAll(",", "");
				acuerdoPago.setValorMulta(Double.parseDouble(valor));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return campo + 1;
		}
		if (campo == 10) {
			try {
				valor = valor.replaceAll(",", "");
				acuerdoPago.setValorAdicional(Double.parseDouble(valor));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return campo + 1;
		}
		if (campo == 11) {
			try {
				valor = valor.replaceAll(",", "");
				acuerdoPago.setTotal(Double.parseDouble(valor));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return campo + 1;
		}
		if (campo == 12) {
			try {
				valor = valor.replaceAll(",", "");
				acuerdoPago.setSaldo(Double.parseDouble(valor));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return campo + 1;
		}
		if (campo == 13) {
			try {
				valor = valor.replaceAll(",", "");
				acuerdoPago.setValorPagar(Double.parseDouble(valor));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return campo + 1;
		}
		return campo + 1;
	}

	private int setCampoComparendo(int campo, String valor, ComparendoFromSimit comparendo) {

		if (campo == 1) {
			comparendo.setComparendo(valor);
			return campo + 1;
		}
		if (campo == 2) {
			comparendo.setSecretaria(valor);
			return campo + 1;
		}
		if (campo == 3) {
			comparendo.setFechaComparendo(valor);
			return campo + 1;
		}
		if (campo == 4) {
			comparendo.setFechaNotificacion(valor);
			return campo + 1;
		}
		if (campo == 5) {
			comparendo.setNombreInfractor(valor);
			return campo + 1;
		}
		if (campo == 6) {
			comparendo.setEstado(valor);
			return campo + 1;
		}
		if (campo == 7) {
			if (valor.contains(",") || valor.equals("0")) {
				try {
					valor = valor.replaceAll(",", "");
					comparendo.setValorMulta(Double.parseDouble(valor));
				} catch (Exception e) {
					e.printStackTrace();
				}
				return campo + 2;
			} else {
				comparendo.setInfraccion(valor);
			}
			return campo + 1;
		}
		if (campo == 8) {
			try {
				valor = valor.replaceAll(",", "");
				comparendo.setValorMulta(Double.parseDouble(valor));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return campo + 1;
		}
		if (campo == 9) {
			try {
				valor = valor.replaceAll(",", "");
				comparendo.setValorAdicional(Double.parseDouble(valor));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return campo + 1;
		}
		if (campo == 10) {
			try {
				valor = valor.replaceAll(",", "");
				comparendo.setTotal(Double.parseDouble(valor));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return campo + 1;
		}
		if (campo == 11) {
			try {
				valor = valor.replaceAll(",", "");
				comparendo.setValorPagar(Double.parseDouble(valor));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return campo + 1;
		}
		return campo + 1;
	}

	private void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}
}
