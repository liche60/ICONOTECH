<?php
include_once 'pages/cartera/carteras.class.php';

$vista->jquery_form_required = true;
$procesosCartera = new ProcesosCartera ();

?>

<style>
#uploadForm {
	border-top: #F0F0F0 2px solid;
	background: #FAF8F8;
	padding: 10px;
}

#uploadForm label {
	margin: 2px;
	font-size: 1em;
	font-weight: bold;
}

.demoInputBox {
	padding: 5px;
	border: #F0F0F0 1px solid;
	border-radius: 4px;
	background-color: #FFF;
}

#progress-bar {
	background-color: #12CC1A;
	height: 20px;
	color: #FFFFFF;
	width: 0%;
	-webkit-transition: width .3s;
	-moz-transition: width .3s;
	transition: width .3s;
}

.btnSubmit {
	background-color: #09f;
	border: 0;
	padding: 10px 40px;
	color: #FFF;
	border: #F0F0F0 1px solid;
	border-radius: 4px;
}

#progress-div {
	border: #0FA015 1px solid;
	padding: 5px 0px;
	margin: 30px 0px;
	border-radius: 4px;
	text-align: center;
}

#targetLayer {
	width: 100%;
	text-align: center;
}
</style>

<script src="https://code.jquery.com/jquery-2.1.1.min.js"
	type="text/javascript"></script>


<script type="text/javascript">
$(document).ready(function() { 
	 $('#uploadForm').submit(function(e) {	
		if($('#userImage').val()) {
			e.preventDefault();
			$('#loader-icon').show();
			$(this).ajaxSubmit({ 
				target:   '#targetLayer', 
				beforeSubmit: function() {
				  $("#progress-bar").width('0%');
				},
				uploadProgress: function (event, position, total, percentComplete){	
					$("#progress-bar").width(percentComplete + '%');
					$("#progress-bar").html('<div id="progress-status">' + percentComplete +' %</div>')
				},
				success:function (){
					$('#loader-icon').hide();
				},
				resetForm: true 
			}); 
			return false; 
		}
	});
}); 
</script>


<div class="row">
	<!-- left column -->
	<div class="col-md-6">
		<!-- general form elements -->
		<div class="box box-primary">
			<div class="box-header with-border">
				<h3 class="box-title">Carga Masiva de Cartera</h3>
			</div>
			<!-- /.box-header -->
			<!-- form start -->
			<form role="form" id="uploadForm" action="pages/cartera/upload.php"
				method="post">
				<div class="box-body">
					<div class="form-group">
						<label>Tipo de Cartera</label> <select name="tipo_cartera" class="form-control">
							<option value="01">Cartera Contravencional de Transito</option>
						</select>
					</div>
					<div class="form-group">
						<label>Cliente</label> <select class="form-control" name="cliente" >
							<option value="01">Barranquilla</option>
						</select>
					</div>
					<div class="form-group">
						<label for="exampleInputFile">Subir Archivo</label> <input
							name="userImage" id="userImage" type="file" class="demoInputBox"
							required="required" />
						<p class="help-block">Archivo plano separado por comas con el
							formato del tipo de cartera seleccionado.</p>
					</div>
				</div>
				<!-- /.box-body -->
				<div class="box-footer">
					<input class="btn btn-primary" type="submit" id="btnSubmit"
						value="Cargar Cartera" class="btnSubmit" />
					<div id="progress-div">
						<div id="progress-bar"></div>
					</div>
					<div id="targetLayer"></div>
				</div>
			</form>
			<div id="loader-icon" style="display: none;">
				<img src="pages/cartera/LoaderIcon.gif" />
			</div>
		</div>
		<!-- /.box -->
		<!-- /.box-body -->
	</div>
	<!-- /.box -->
	<?php 
	include('rest/httpful.phar');
	
	$response = \Httpful\Request::get('http://google.com')->send();
	echo $response ;
	
	
	?>


	<div class="col-md-6">
		<!-- general form elements -->
		<div class="box">
			<div class="box-header">
				<h3 class="box-title">Radicacion de Cartera - Pendientes</h3>
			</div>
			<!-- /.box-header -->
			<div class="box-body no-padding">
				<table class="table table-striped">
					<tr>
						<th style="width: 10px">#</th>
						<th>Archivo</th>
						<th>Proceso</th>
						<th style="width: 40px">%</th>
					</tr>
					<?php
					$procesosCartera->printFiles ();
					?>
				</table>
			</div>
			<!-- /.box-body -->
		</div>
		<!-- /.box -->
		<!-- /.box-body -->
	</div>
</div>
