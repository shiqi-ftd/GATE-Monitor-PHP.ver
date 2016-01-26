<?php
error_reporting ( 0 );
session_start ();
if (strcmp ( $_SESSION ['login'], 'in' ) != 0) {
	header ( "Location: index.php" );
}
// Execute shell script
// Need to give permission to the shell script
$cmd = $_POST["simutype"];
if($cmd === "SPECT"){
	exec('/bin/sh path/to/bash');
}
header ( "Location: simulation.php" );

?>
