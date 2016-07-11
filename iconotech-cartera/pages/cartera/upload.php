<?php
include('rest/httpful.phar');
if (! empty ( $_FILES )) {
	if (is_uploaded_file ( $_FILES ['userImage'] ['tmp_name'] )) {
		$tc = $_POST["tipo_cartera"];
		$cl = $_POST["cliente"];
		$time = time ();
		$key = substr ( str_shuffle ( "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ" ), 0, 5);
		$hash = md5 ( $key . $time);
		$sourcePath = $_FILES ['userImage'] ['tmp_name'];
		$targetPath = "tmp/archivos_cartera/" .$tc."_".$cl."_".$hash. "_".$time;
		sleep(60);
		if (move_uploaded_file ( $sourcePath, $targetPath )) {
			
			?>
			<?php
		}
	}
} 
?>