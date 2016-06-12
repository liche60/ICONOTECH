package co.com.icono.ramajudicial.parse;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import co.com.icono.ramajudicial.objects.Ciudad;
import co.com.icono.ramajudicial.objects.Ciudades;

public class ParseCiudades {

	public static Ciudades parse(Element ciudadeTag) {
		Ciudades ciudades = new Ciudades();

		Elements registros = ciudadeTag.getElementsByTag("option");
		List<Ciudad> ciudadesList = new ArrayList<>();

		for (Element element : registros) {
			if (!element.val().equals("0")) {
				Ciudad ciudad = new Ciudad();
				ciudad.setId(Long.parseLong(element.val()));
				ciudad.setCiudad(element.text());
				ciudadesList.add(ciudad);
			}
		}
		ciudades.setCiudades(ciudadesList);
		return ciudades;
	}

}
