<?php
error_reporting ( 0 );
session_start ();
if (strcmp ( $_SESSION ['login'], 'in' ) != 0) {
	header ( "Location: index.php" );
}
?>

<!DOCTYPE html>
<html>
<head>

<link type="text/css" rel="stylesheet" href="css/gate.css">
<title>GATE</title>
<meta name="author" content="Shiqi Zhong">

</head>
<body>
	<div id="header">
		<img id="logo" src="./images/logo.gif" align="left">

		<p id="name"></p>
		<a href="mailto:szhong4@vols.utk.edu" id="email">Mail to Shiqi Zhong</a>
		<p id="info">
		<?php
		echo "IP Address:{$_SERVER["REMOTE_ADDR"]}";
		?>			
		<br> Today's date: 
		<?php echo date('l jS \of F Y h:i:s A');?>
		
		</p>
		<br> <br>

	</div>

	<div class="left">
		<h3>
			<a href="./intro.php"><strong>Introduction</strong></a>
		</h3>
		<h3>
			<a href="./simulation.php"><strong>Run Simulation</strong></a>
		</h3>
		<h3>
			<a href="./result.php"><strong>View Result</strong></a>
		</h3>
		<h3>
			<a href="./source.php"><font color="red"><em>Source Code</em></font></a>
		</h3>
	</div>

	<div class="right">
		<h4>Source Code</h4>
		<br>
		<p>
			This is an open source ongoing project written with HTML, CSS, AJAX,
			JavaScript, PHP, Perl, Shell Script and C. <br> <br>View
			the source code: <a
				href="https://github.com/szhong4/GATE-Interactive-Monitor">GitHub</a>
		</p>
		<br>
		<p>
			Learn more about <a
				href="http://wiki.opengatecollaboration.org/index.php/Users_Guide_V7.1">GATE</a>
		</p>
		<br>
		<p>
			Learn more about <a href="https://root.cern.ch/drupal/">ROOT</a>
		</p>

	</div>

	<div id="footer">
		<p>&#169 GATE Interactive Monitor, 2015</p>
	</div>

	<script type="text/javascript" src="scripts/jquery-1.9.1.js"></script>
	<script type="text/javascript" src="scripts/bootstrap.min.js"></script>
	<script type="text/javascript" src="scripts/d3.v3.min.js"></script>
	<script type="text/javascript" src="shiqi.js"></script>
</body>
</html>