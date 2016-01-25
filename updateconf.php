<?php
error_reporting ( 0 );
session_start ();
if (strcmp ( $_SESSION ['login'], 'in' ) != 0) {
	header ( "Location: result.php" );
}

# Update the simulation conf files here.
?>
