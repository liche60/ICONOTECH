<?php
include 'plugins/httpful/httpful.phar';
$url = "http://localhost:8080/iconotech-api/rest/cartera/dashboard";
$valor = \Httpful\Request::post($url)->body("1")->send();
?>
<!-- Small boxes (Stat box) -->
<div class="row">
	<div class="col-lg-3 col-xs-6">
		<!-- small box -->
		<div class="small-box bg-aqua">
			<div class="inner">
				<h3>$ <?php echo  $valor;  ?></h3>

				<p>Valor Cartera</p>
			</div>
			<div class="icon">
				<i class="ion ion-bag"></i>
			</div>
			 <a href="#" class="small-box-footer">Mas informacion <i
				class="fa fa-arrow-circle-right"></i></a> 
		</div>
	</div>
	<!-- ./col -->
	<div class="col-lg-3 col-xs-6">
		<!-- small box -->
		<div class="small-box bg-green">
			<div class="inner">
				<h3>
					0
				</h3>

				<p>Registros de Cartera</p>
			</div>
			<div class="icon">
				<i class="ion ion-stats-bars"></i>
			</div>
			<a href="#" class="small-box-footer">Mas informacion <i
				class="fa fa-arrow-circle-right"></i></a>
		</div>
	</div>
	<!-- ./col -->
	<div class="col-lg-3 col-xs-6">
		<!-- small box -->
		<div class="small-box bg-yellow">
			<div class="inner">
				<h3>0</h3>

				<p>Deudores</p>
			</div>
			<div class="icon">
				<i class="ion ion-person-add"></i>
			</div>
			<a href="#" class="small-box-footer">Mas informacion <i
				class="fa fa-arrow-circle-right"></i></a>
		</div>
	</div>
	<!-- ./col -->
	<div class="col-lg-3 col-xs-6">
		<!-- small box -->
		<div class="small-box bg-red">
			<div class="inner">
				<h3>0</h3>

				<p>Errores de Cartera</p>
			</div>
			<div class="icon">
				<i class="ion ion-pie-graph"></i>
			</div>
			<a href="#" class="small-box-footer">Mas informacion <i
				class="fa fa-arrow-circle-right"></i></a>
		</div>
	</div>
	<!-- ./col -->
</div>
<!-- /.row -->
