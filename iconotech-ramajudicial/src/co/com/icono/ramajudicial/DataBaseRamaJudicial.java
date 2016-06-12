package co.com.icono.ramajudicial;

import static org.junit.Assert.fail;

import java.io.PrintStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.gson.Gson;

import co.com.icono.ramajudicial.objects.DatosProceso;
import co.com.icono.ramajudicial.parse.ParseDatosProceso;

public class DataBaseRamaJudicial {

	private WebDriver driver;
	private String baseUrl;
	private StringBuffer verificationErrors = new StringBuffer();
	private Gson gson;
	private static String CIUDAD = "MEDELLIN";
	private static String CODIGO_CIUDAD = "05001";
	private static String JUZGADO = "JUZGADOS CIVILES DEL CIRCUITO DE MEDELLIN";
	private static String RAZON_SOCIAL = "MUNICIPIO DE MEDELLIN";
	private static String CODIGO_JUZGADO = "";
	private static Options options = new Options();

	public static void main2(String[] args) {
		DataBaseRamaJudicial rama = new DataBaseRamaJudicial();
		try {
			rama.setUp();
			rama.consultaResoluciones();
			rama.extraccionRadicados(0, 10);
			rama.tearDown();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void help() {
		HelpFormatter formater = new HelpFormatter();
		formater.printHelp("Main", options);
		System.out.println();
		System.exit(0);
	}

	public static void main(String[] args) {
		CommandLineParser parser = new DefaultParser();

		options.addOption("h", "help", false, "Ver ayuda.");
		options.addOption("m", "masivo", false, "Procesamiento masivo.");
		options.addOption("d", "diferencial", false, "Procesamiento diferencial.");
		options.addOption("i", "inicio", true, "Pagina inicial.");
		options.addOption("f", "final", true, "Pagina final.");

		options.addOption("j", "juzgado", true, "Juzgado a consultar.");
		options.addOption("x", "codigo-juzgado", true, "Codigo del Juzgado a consultar.");
		options.addOption("q", "codigo-ciudad", true, "Codigo de la ciudad.");
		options.addOption("c", "ciudad", true, "Nombre de la ciudad.");
		options.addOption("r", "razon-social", true, "Razon social del demandado.");

		try {
			// parse the command line arguments

			CommandLine cmd = parser.parse(options, args);
			if (cmd.hasOption("h")) {
				help();
			}
			int initPage = 0;
			int endPage = 0;
			if (cmd.hasOption("i") && cmd.hasOption("f")) {
				try {
					initPage = Integer.parseInt(cmd.getOptionValue("i"));
					endPage = Integer.parseInt(cmd.getOptionValue("f"));
					if (endPage < initPage) {
						throw new Exception("Pagina final no puede ser menor a la pagina inicial");
					}
				} catch (Exception e) {
					throw new Exception("Los parametros i y f deben ser numericos");
				}
			} else {
				throw new Exception("Es necesaria la opción i y f");
			}

			if (cmd.hasOption("d") || cmd.hasOption("m")) {
				DataBaseRamaJudicial rama = new DataBaseRamaJudicial();
				rama.setUp();
				if(cmd.hasOption("x")){
					CODIGO_JUZGADO = cmd.getOptionValue("x");
				}
				if (cmd.hasOption("c")) {
					CIUDAD = cmd.getOptionValue("c");
				}
				if (cmd.hasOption("q")) {
					CODIGO_CIUDAD = cmd.getOptionValue("q");
				}
				if (cmd.hasOption("j")) {
					JUZGADO = cmd.getOptionValue("j");
				}
				if (cmd.hasOption("r")) {
					RAZON_SOCIAL = cmd.getOptionValue("r");
				}
				System.out.println(JUZGADO);
				if (cmd.hasOption("m")) {
					rama.consultaResoluciones();
				}
				if (cmd.hasOption("d")) {
					rama.consultaResolucionesDiferencial();
				}
				rama.extraccionRadicados(initPage, endPage);
			} else {
				throw new Exception("Es necesaria la opción m o d");
			}

		} catch (Exception exp) {
			System.err.println("Parsing failed.  Reason: " + exp.getMessage());
			exp.printStackTrace();
			help();
		}
	} 

	public static void main3(String[] args) {
		try {
			DataBaseRamaJudicial rama = new DataBaseRamaJudicial();
			System.setOut(new PrintStream(System.out, true, "UTF-8"));
			if (args.length == 3) {
				String opt = args[0];
				switch (opt) {
				case "0":
					System.out.println("Iniciando carga completa");
					rama.setUp();
					rama.consultaResoluciones();
					break;
				case "1":
					System.out.println("Iniciando carga diferencial");
					rama.setUp();
					rama.consultaResolucionesDiferencial();
					break;
				default:
					throw new Exception("La opción de carga no es 0 (masivo) o 1 (diferencial)");
				}

				try {
					int initPage = Integer.parseInt(args[1]);
					int endPage = Integer.parseInt(args[2]);

					if (endPage < initPage) {
						throw new Exception("Pagina final no puede ser menor a la pagina inicial");
					}

					System.out.println("Pagina Inicial: " + initPage);
					if (endPage == 0) {
						System.out.println("Se procesarán todas las páginas");
					} else {
						System.out.println("Pagina Final: " + endPage);
					}

					rama.extraccionMasivaActuaciones(initPage, endPage);
				} catch (Exception e) {
					throw new Exception("Error en los parametros");
				}

			} else {
				throw new Exception("Los parámetros son insuficientes");
			}

			rama.tearDown();

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	public void setUp() throws Exception {

		gson = new Gson();
		driver = new HtmlUnitDriver(true);
		// driver = new FirefoxDriver();
		baseUrl = "http://procesos.ramajudicial.gov.co/";
		// Actions builder = new Actions(driver);
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS);

	}

	private void consultaResolucionesDiferencial() throws InterruptedException {

		driver.get(baseUrl + "/ConsultaProcesos/");
		new Select(driver.findElement(By.id("ddlCiudad"))).selectByVisibleText(CIUDAD);
		Thread.sleep(2000);
		driver.findElement(By.cssSelector("option[value=\"" + CODIGO_CIUDAD + "\"]")).click();
		Thread.sleep(2000);
		// System.out.println(driver.getPageSource());
		new Select(driver.findElement(By.id("ddlEntidadEspecialidad"))).selectByVisibleText(JUZGADO);
		Thread.sleep(2000);

		new Select(driver.findElement(By.id("ddlEntidadEspecialidad"))).selectByVisibleText(JUZGADO);
		new Select(driver.findElement(By.id("rblConsulta")))
				.selectByVisibleText("Últimas Actuaciones por Nombre o Razón Social");
		new Select(driver.findElement(By.id("ddlTipoSujeto2"))).selectByVisibleText("Demandado");
		new Select(driver.findElement(By.id("ddlTipoPersona2"))).selectByVisibleText("Juridica");
		driver.findElement(By.id("txtNombre")).clear();
		driver.findElement(By.id("txtNombre")).sendKeys(RAZON_SOCIAL);
		((JavascriptExecutor) driver)
				.executeScript("document.getElementsByName('btnCosultaActFecha')[0].removeAttribute('disabled');​​​​");
		// System.out.println(driver.getPageSource());

		driver.findElement(By.id("btnCosultaActFecha")).click();
	}

	private void consultaResoluciones() throws InterruptedException {

		driver.get(baseUrl + "/ConsultaProcesos/");
		new Select(driver.findElement(By.id("ddlCiudad"))).selectByVisibleText(CIUDAD);
		Thread.sleep(2000);
		driver.findElement(By.cssSelector("option[value=\"" + CODIGO_CIUDAD + "\"]")).click();
		Thread.sleep(2000);
		// System.out.println(driver.getPageSource());
		new Select(driver.findElement(By.id("ddlEntidadEspecialidad"))).selectByVisibleText(JUZGADO);
		Thread.sleep(2000);

		new Select(driver.findElement(By.id("ddlEntidadEspecialidad"))).selectByVisibleText(JUZGADO);
		new Select(driver.findElement(By.id("rblConsulta"))).selectByVisibleText("Consulta por Nombre o Razón social");
		new Select(driver.findElement(By.id("ddlTipoSujeto"))).selectByVisibleText("Demandado");
		new Select(driver.findElement(By.id("ddlTipoPersona"))).selectByVisibleText("Juridica");
		driver.findElement(By.id("txtNatural")).clear();
		driver.findElement(By.id("txtNatural")).sendKeys(RAZON_SOCIAL);
		((JavascriptExecutor) driver)
				.executeScript("document.getElementsByName('btnConsultaNom')[0].removeAttribute('disabled');​​​​");
		// System.out.println(driver.getPageSource());

		driver.findElement(By.id("btnConsultaNom")).click();
	}

	private void extraccionRadicados(int initPage, int endPage) throws Exception {

		int resoluciones = 0;
		int paginas = 1;
		int bloquePaginas = 0;

		try {

			while (true) {

				// si la pagina actual del ciclo es mayor o igual a la definida
				// como paranetro comenzaría a procesar sino, seguiria con la
				// otra pagina

				if (paginas >= initPage) {

					Thread.sleep(5000);

					List<WebElement> actuaciones = driver.findElements(By.tagName("a"));
					for (int i = 0; i < actuaciones.size(); i++) {
						if (actuaciones.get(i).getAttribute("href").contains("gvResultadosNum$ctl")) {
							System.out.println(CODIGO_CIUDAD+"|"+CODIGO_JUZGADO+"|"+actuaciones.get(i).getText());
							resoluciones++;
						}
					}
				}

				paginas++;

				if (endPage > 0) {
					if (endPage < paginas) {
						// si la pagina actual es igual a la ultima definida, el
						// proceso termina
						System.out
								.println("Fin, se procesaron: " + resoluciones + " en: " + (paginas - 1) + " páginas");
						break;
					}
				}

				boolean ok = false;
				if (bloquePaginas < 9) {
					bloquePaginas++;

					if (paginas > 1) {
						try {
							driver.findElement(By.linkText("" + (paginas))).click();
							Thread.sleep(3000);
							System.out.println("página " + (paginas - 1) + " procesada");
							ok = true;
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} else {
					bloquePaginas = 0;
					if (paginas == 11) {
						try {
							driver.findElement(By.linkText("...")).click();
							Thread.sleep(3000);
							System.out.println("página " + (paginas - 1) + " procesada");
							System.out.println("pasa al siguiente bloque ...");
							ok = true;
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						try {
							driver.findElement(By.xpath("(//a[contains(text(),'...')])[2]")).click();
							Thread.sleep(3000);
							System.out.println("página " + (paginas - 1) + " procesada");
							System.out.println("pasa al siguiente bloque ... (2)");
							ok = true;
						} catch (Exception e) {
							e.printStackTrace();
						}

					}

				}

				if (!ok) {
					System.out.println("Fin, se procesaron: " + resoluciones + " en: " + (paginas - 1) + " páginas");
					break;
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void extraccionMasivaActuaciones(int initPage, int endPage) throws Exception {

		int resoluciones = 0;
		int paginas = 1;
		int bloquePaginas = 0;

		try {

			while (true) {

				// si la pagina actual del ciclo es mayor o igual a la definida
				// como paranetro comenzaría a procesar sino, seguiria con la
				// otra pagina

				if (paginas >= initPage) {

					Thread.sleep(5000);

					List<WebElement> actuaciones = driver.findElements(By.tagName("a"));
					for (int i = 0; i < actuaciones.size(); i++) {
						if (actuaciones.get(i).getAttribute("href").contains("gvResultadosNum$ctl")) {
							resoluciones++;
							// if (resoluciones > 4) {
							WebElement linkActuaciones = (new WebDriverWait(driver, 60))
									.until(ExpectedConditions.elementToBeClickable(actuaciones.get(i)));
							linkActuaciones.click();

							boolean ok = false;
							try {
								if (true) {
									WebElement lbtnConsultaAnterior = (new WebDriverWait(driver, 60)).until(
											ExpectedConditions.elementToBeClickable(By.id("lbtnConsultaAnterior")));
									System.out.println("resolución " + resoluciones + " procesada => ");
									System.out.println(gson.toJson(getDatosProcesoFromSource(driver.getPageSource())));
									lbtnConsultaAnterior.click();
									ok = true;
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							if (!ok) {
								System.out.println("resolución número: " + resoluciones + " no es visible");
							}
							// }
						}
					}
				}

				paginas++;

				if (endPage > 0) {
					if (endPage < paginas) {
						// si la pagina actual es igual a la ultima definida, el
						// proceso termina
						System.out
								.println("Fin, se procesaron: " + resoluciones + " en: " + (paginas - 1) + " páginas");
						break;
					}
				}

				boolean ok = false;
				if (bloquePaginas < 9) {
					bloquePaginas++;

					if (paginas > 1) {
						try {
							driver.findElement(By.linkText("" + (paginas))).click();
							Thread.sleep(3000);
							System.out.println("página " + (paginas - 1) + " procesada");
							ok = true;
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} else {
					bloquePaginas = 0;
					if (paginas == 11) {
						try {
							driver.findElement(By.linkText("...")).click();
							Thread.sleep(3000);
							System.out.println("página " + (paginas - 1) + " procesada");
							System.out.println("pasa al siguiente bloque ...");
							ok = true;
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						try {
							driver.findElement(By.xpath("(//a[contains(text(),'...')])[2]")).click();
							Thread.sleep(3000);
							System.out.println("página " + (paginas - 1) + " procesada");
							System.out.println("pasa al siguiente bloque ... (2)");
							ok = true;
						} catch (Exception e) {
							e.printStackTrace();
						}

					}

				}

				if (!ok) {
					System.out.println("Fin, se procesaron: " + resoluciones + " en: " + (paginas - 1) + " páginas");
					break;
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private DatosProceso getDatosProcesoFromSource(String source) {
		Document doc = Jsoup.parse(source);
		Elements elements = doc.getAllElements();
		DatosProceso datosProcesos = ParseDatosProceso.parse(elements);
		return datosProcesos;
	}

	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

}
