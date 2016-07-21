<?php

if ($vista->page == "tc") {
	include 'pages/tablero/tablero_control.php';
}

if ($vista->page == "cc") {
	include 'pages/cartera/cargar_cartera.php';
}

if ($vista->page == "ex") {
	include 'pages/expediente/expediente.php';
}


if ($vista->page == "500") {
	include 'pages/error/500.php';
}

?>