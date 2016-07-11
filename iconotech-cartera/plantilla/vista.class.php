<?php
class Vista {
	
	var $page;
	var $breadcrumb;
	var $jquery_form_required = false;
	
	function Vista($page) {
		$this->page = $page;
		$this->buildBreadcrumb ();
	}
	private function buildBreadcrumb() {
		if ($this->page == "tc") {
			$this->buildBreadcrumParams ( "Tablero de Control", "Análisis de Cartera" );
		}
		if ($this->page == "cc") {
			$this->buildBreadcrumParams ( "Administraci&#243;n de Cartera", "Cargar Cartera" );
		}
	}
	private function buildBreadcrumParams($big, $small) {
		$this->breadcrumb = "<section class=\"content-header\">";
		$this->breadcrumb .= "<h1>";
		$this->breadcrumb .= "$big <small>$small</small>";
		$this->breadcrumb .= "</h1>";
		$this->breadcrumb .= "<ol class=\"breadcrumb\">";
		$this->breadcrumb .= "<li><a href=\"index.php\"><i class=\"fa fa-dashboard\"></i> Inicio</a></li>";
		$this->breadcrumb .= "<li class=\"active\">$small</li>";
		$this->breadcrumb .= "</ol>";
		$this->breadcrumb .= "</section>";
	}
}

?>
