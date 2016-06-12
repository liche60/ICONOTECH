<%@page import="com.liferay.portal.util.PortalUtil"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui"%>

<portlet:defineObjects />
<!DOCTYPE html>
<html>
<head>
<script src="<%=request.getContextPath()%>/js/jquery.js"
	type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/jquery.validVal.js"
	type="text/javascript"></script>
<link href="<%=request.getContextPath()%>/css/jquery-ui.css"
	rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath()%>/js/jquery-ui.js"
	type="text/javascript"></script>
</head>
<body>


	<div id="tabs">
		<ul class="tabs-left">
			<li><a href="#tabs-1"><i class="icon-user icon-white"></i>
					Información Personal</a></li>
			<li><a id="dos" href="#tabs-2"><i
					class="icon-map-marker icon-white"></i> Información de Ubicación</a></li>
			<li><a href="#tabs-3"><i class="icon-file icon-white"></i>
					Información del Título</a></li>
			<li><a href="#tabs-4"><i class="icon-save icon-white"></i>
					Completado</a></li>
		</ul>
		<div id="tabs-1">
			<form id="frmInfoPersonal" class="form-wrapper" method="post">
				<div>
					<label for="select_tipo">Tipo Documento</label> <select
						id="select_tipo" class="placeholder" name="select_tipo" size="1"
						onchange="checkOption()">
						<option value="1">Cedula</option>
						<option value="2">Nit</option>
						<option value="3">Cedula Extranjeria</option>
					</select>
				</div>
				<div>
					<label for="req_doc">Documento</label> <input id="req_doc"
						class="required" type="number" name="req_doc" min="10000"
						max="9999999999" required="required" />
				</div>
				<div>
					<label for="req_name">Nombre</label> <input id="req_name"
						class="required" type="text" name="req_name" value="" size="50"
						required="required" />
				</div>
				<div>
					<label for="lastname" id="lbl_lastname">Apellidos</label> <input
						id="lastname" class="required" type="text" name="lastname"
						value="" size="50" required="required" />
				</div>

				<div>
					<input type="hidden" name="formState" value="1-complete" />
					<button class="btn btn-primary">
						<i class="icon-user icon-white"></i> Siguiente
					</button>
				</div>
			</form>
		</div>
		<div id="tabs-2">
			<form id="frmInfoUbic" class="form-wrapper" action="#ex1"
				method="post">
				<div>
					<label for="select_depto">Departamento</label> <select
						id="select_depto" class="small" name="select_depto" size="1"
						onchange="checkOption()">
						<option value="05">Antioquia</option>
					</select>
				</div>
				<div>
					<label for="select_ciudad">Ciudad</label> <select
						id="select_ciudad" class="small" name="select_ciudad" size="1"
						onchange="checkOption()">
						<option value="05001">Medellin</option>
					</select>
				</div>
				<div>
					<label for="req_dir">Dirección</label> <input id="req_dir"
						class="required" type="text" name="req_dir" maxlength="100"
						required="required" />
				</div>
				<div>
					<label for="req_tel">Telefono Fijo 1</label> <input id="req_tel"
						class="required" type="tel" name="req_tel" required="required"
						pattern='[\+]\d{2}[\(]\d{1,2}[\)]\d{7}'
						title='Número Teléfono (Formato: +57(99)1234567)' />
				</div>
				<div>
					<label for="req_tel2">Telefono Fijo 2</label> <input id="req_tel2"
						class="required" type="tel" name="req_tel2" required="required"
						pattern='[\+]\d{2}[\(]\d{1,2}[\)]\d{7}'
						title='Número Teléfono (Formato: +57(99)1234567)' />
				</div>
				<div>
					<label for="req_cel">Celular 1</label> <input id="req_cel" class=""
						type="tel" name="req_cel" required=""
						pattern='[\+]\d{2}[\(]\d{1,2}[\)]\d{7}'
						title='Número Teléfono (Formato: +57(99)1234567)' />
				</div>
				<div>
					<label for="req_cel2">Celular 2</label> <input id="req_cel2"
						class="" type="tel" name="req_cel2" required=""
						pattern='[\+]\d{2}[\(]\d{1,2}[\)]\d{7}'
						title='Número Teléfono (Formato: +57(99)1234567)' />
				</div>
				<div>
					<label for="req_email">Correo Electr&oacute;nico</label> <input
						id="req_email" class="" type="tel" name="req_email" required=""
						pattern='[\+]\d{2}[\(]\d{1,2}[\)]\d{7}'
						title='Número Teléfono (Formato: +57(99)1234567)' />
				</div>
				<div>
					<input type="hidden" name="formState" value="2-complete" />
					<button class="btn btn-primary">
						<i class="icon-map-marker icon-white"></i> Siguiente
					</button>
				</div>
			</form>
		</div>
		<div id="tabs-3">
			<form id="frmInfoTitulo" class="form-wrapper" action="#ex1"
				method="post">
				<div>
					<label for="req_resol">Resoluci&oacute;n</label> <input
						id="req_resol" class="required" type="text" name="req_resol"
						required="required" />
				</div>
				<div>
					<label for="req_comparendo">Comparendo</label> <input
						id="req_comparendo" class="required" type="text"
						name="req_comparendo" required="required" />
				</div>
				<div>
					<input type="hidden" name="formState" value="3-complete" />
					<button class="btn btn-primary">
						<i class="icon-save icon-white"></i> Guardar
					</button>
				</div>
			</form>
		</div>

		<div id="tabs-4">
			<div class="alert alert-success">
				<img src="<%=request.getContextPath()%>/css/images/ok.png" /><strong>
					Existoso!</strong> El registro ha sido almacenado exitosamente.
			</div>
		</div>
	</div>

	<script>
		$(function() {
			$("#tabs").tabs();
		});
	</script>


	<script>
		function checkOption() {
			//This will fire on changing of the value of "requests"
			var dropDown = document.getElementById("select_tipo");
			var textBox = document.getElementById("lastname");
			var label = document.getElementById("lbl_lastname");

			// En caso de ser NIT, oculta el apellido
			if (dropDown.value == "2") {
				textBox.style.display = 'none';
				textBox.required = '';
			} else {
				textBox.style.display = '';
				textBox.required = "required";
			}

			// Asigna el valor del campo a label
			label.style.display = textBox.style.display;

		}
	</script>

	<%
		String param = PortalUtil.getOriginalServletRequest(request).getParameter("formState");
		if (param != null) {
			param = param.trim();
			if (param.equals("1-complete")) {
	%>
	<script>
		$("#tabs").tabs({
			active : 1
		});
		$("#tabs").tabs({
			disabled : [ 0, 2, 3 ]
		});
	</script>
	<%
		}
			if (param.equals("2-complete")) {
	%>
	<script>
		$("#tabs").tabs({
			active : 2
		});
		$("#tabs").tabs({
			disabled : [ 0, 1, 3 ]
		});
	</script>
	<%
		}
			if (param.equals("3-complete")) {
	%>
	<script>
		$("#tabs").tabs({
			active : 3
		});
		$("#tabs").tabs({
			disabled : [ 0, 1, 2 ]
		});
	</script>
	<%
		}

		} else {
	%>
	<script>
		$("#tabs").tabs({
			disabled : [ 1, 2, 3 ]
		});
	</script>
	<%
		}
	%>



</body>
</html>