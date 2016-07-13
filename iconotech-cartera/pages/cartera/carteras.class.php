<?php
class ProcesosCartera {
	
	
	function ProcesosCartera() {
		
	}

	function printFiles($json){
		$obj = json_decode($json);
		foreach ($obj as $entry){
			echo "<tr>";
			echo 	"<td>$entry->id</td>";
			echo 	"<td>$entry->fecha_carga</td>";
			echo 	"<td>";
			
			if($entry->estado == "0"){
				echo "En cola";
			}else{
				echo 		"<div class=\"progress progress-xs progress-striped active\">";
				echo "<div class=\"progress-bar progress-bar-primary\" style=\"width: $entry->avance% \" ></div>";
				echo 		"</div>";
			}
			
			echo 	"</td>";
			
			echo 	"<td><span class=\"badge bg-light-blue\">$entry->avance%</span></td>";
			
			echo "</tr>";
		}
		
		
		
		/*if ($handle = opendir('pages/cartera/tmp/archivos_cartera/')) {
		
			while (false !== ($entry = readdir($handle))) {
		
				if ($entry != "." && $entry != "..") {
					echo "<tr>";
					echo 	"<td>1.</td>";
					echo 	"<td>$entry</td>";
					echo 	"<td>";
					echo 		"<div class=\"progress progress-xs progress-striped active\">";
					echo 			"<div class=\"progress-bar progress-bar-primary\" style=\"width: 30% \" ></div>";
					echo 		"</div>";
					echo 	"</td>";
					echo 	"<td><span class=\"badge bg-light-blue\">30%</span></td>";
					echo "</tr>";
				}
			}
		
			closedir($handle);
		}*/
	}
}

?>