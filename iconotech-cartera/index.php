<!DOCTYPE html>
<?php
include_once 'plantilla/vista.class.php';

if(isset($_GET["page"])){
	$page = $_GET["page"];
}else{
	$page = "tc";
}

$vista = new Vista ( $page );
?>
<html>
<?php
include 'plantilla/header.php';
?>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">
<?php
include 'plantilla/main_header.php';
include 'plantilla/menu_lateral.php';
/*
 * if( $_GET["page"]) {
 * $page = $_GET["page"];
 * }
 */
?>
		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">
			<!-- Content Header (Page header) -->
			
			<?php
			echo $vista->breadcrumb;
			?>

			<!-- Main content -->
			<section class="content">
			
				<?php 
					include 'controlador.php';
				?>
							
			</section>
			<!-- /.content -->
		</div>
<?php
include 'plantilla/footer.php';
?>
</body>
</html>
