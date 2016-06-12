package co.com.icono.ramajudicial.parse;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import co.com.icono.ramajudicial.objects.Entidad;
import co.com.icono.ramajudicial.objects.Entidades;

public class ParseEntidades {
	public static Entidades parse(Element entidadesTag) {
		Entidades entidades = new Entidades();

		Elements registros = entidadesTag.getElementsByTag("option");
		List<Entidad> entidadesList = new ArrayList<>();

		for (Element element : registros) {
			String query = element.val();
			if (!query.equals("0")) {
				Entidad entidad = new Entidad();
				StringTokenizer tokens = new StringTokenizer(query, "-");
				tokens.nextToken();
				tokens.nextToken();
				String valor = tokens.nextToken();
				entidad.setQuery(query);
				entidad.setId(Long.parseLong(valor));
				entidad.setEntidad(element.text());
				entidadesList.add(entidad);
			}
		}
		entidades.setEntidades(entidadesList);

		return entidades;
	}
}
