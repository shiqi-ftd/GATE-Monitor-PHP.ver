<?php
error_reporting ( 0 );
session_start ();
if (strcmp ( $_SESSION ['login'], 'in' ) != 0) {
	header ( "Location: result.php" );
}

# Update the simulation conf files here.
$cmd = $_POST["command"];
# Set up the PATH to the GATE-Monitor (Not the jar file.)
$path = "/Users/ShiqiZhong/Desktop/GATE-Interactive-Monitor";

// ####
// ！Need to give the execution permission to php via /etc/sudoers
// ###
if (!empty($cmd)) {
	exec('java -jar path_to_set_param.jar $cmd $path');
}
header ( "Location: simulation.php" );
?>
