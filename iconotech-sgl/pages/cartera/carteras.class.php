<?php
class ProcesosCartera {
	
	
	function ProcesosCartera() {
		
	}

	function printFiles($json){
		$obj = json_decode($json);
		$consecutivo = 1;
		foreach ($obj as $entry){
			echo "<tr>";
			echo 	"<td>$consecutivo</td>";
			echo 	"<td>$entry->fecha</td>";
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
			$consecutivo = $consecutivo+1;
		}
		
	}
}

?>