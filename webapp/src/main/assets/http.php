<?php
	//header("");
	$arr = $_SERVER;
	foreach ($arr as $key => $value) {
		echo $key.": ".$value."</br>";
	}
	echo "你的ip： ".$_SERVER['REMOTE_ADDR'];
?>

/*
 <?php
	foreach($_SERVER as $key => $value){
		echo $key.": ".$value."</br>";
	};
	echo $_SERVER['REMOTE_ADDR'];
 ?>
 */