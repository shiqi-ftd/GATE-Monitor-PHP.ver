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
			<a href="./result.php"><font color="red"><em>View Result</em></font></a>
		</h3>
		<h3>
			<a href="./source.php">Source Code</a>
		</h3>
	</div>

	<div class="right">
		<div id="rightcontent">
			<h4>Simulation Result</h4>


			<a href="#" class="myButton">Choose Simulation: <select
				id="simu_type">
					<option value="0"></option>

					<option value="1">SPECT</option>

					<option value="2">PET</option>

			</select>

			</a> <br> <br> <br> <a href="#" class="myButton"
				onclick="show_conf()">View macro file </a> <br> <br>

			<textarea name="content" cols="36" rows="8" id="content"
				style="border: 1 solid #888888; LINE-HEIGHT: 20px; padding: 10px;"></textarea>
			<br> <br> <br> <a href="#" class="myButton" onclick="view_result()">View
				Analysed Result: </a> <br> <br> <br> <a href="#" class="myButton"
				onclick="download_result()" id="downld">Download simulation result:
			</a>
		</div>
		<!--  			<a href="http://localhost:8080/GATE-Interactive-Monitor/conf/Cylindrical/Cylindrical.root"
			class="myButton" >Download simulation
			result: 
		</a>
 -->
		<div>

			<img id="resultpic" src="" style="display: none">

		</div>
	</div>


	<div id="footer">
		<p>&#169 GATE Interactive Monitor, 2015</p>
	</div>


	<script type="text/javascript" src="scripts/jquery-1.9.1.js"></script>
	<script type="text/javascript" src="scripts/bootstrap.min.js"></script>
	<script type="text/javascript" src="scripts/d3.v3.min.js"></script>
	<script>
		function show_conf() {
			var type = $("#simu_type option:selected").text();
			if (type === "SPECT") {
				type = "SPECT";
				loadXMLDoc("/gate/conf/SPECT/mac/inveonSPECT.mac")
			} else if (type === "Gamma Camera") {
				type = "Gamma";
				loadXMLDoc("http://localhost:8080/GATE-Interactive-Monitor/conf/Gamma/configuration.mac")
			}

		}
		function loadXMLDoc(url) {
			xmlhttp = null;
			if (window.XMLHttpRequest) {// code for Firefox, Opera, IE7, etc.
				xmlhttp = new XMLHttpRequest();
			} else if (window.ActiveXObject) {// code for IE6, IE5
				xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
			}
			if (xmlhttp != null) {
				xmlhttp.onreadystatechange = state_Change;
				xmlhttp.open("GET", url, true);
				xmlhttp.send(null);
			} else {
				alert("Your browser does not support XMLHTTP.");
			}
		}

		function state_Change() {
			if (xmlhttp.readyState == 4) {// 4 = "loaded"
				if (xmlhttp.status == 200) {// 200 = "OK"
					document.getElementById('content').innerHTML =
				"<?php echo date('l jS \of F Y h:i:s A');?>"
							+ '\n' + xmlhttp.responseText;
				} else {
					alert("Problem retrieving data:" + xmlhttp.statusText);
				}
			}
		}

		function view_result() {
			var type = $("#simu_type option:selected").text();
			if (type === "Cylindrical PET") {
				type = "Cylindrical";
				$('#resultpic').attr('src','http://localhost:8080/GATE-Interactive-Monitor/conf/Cylindrical/Cylindrical.gif');
			    $("#resultpic").toggle();


				} else if (type === "Gamma Camera") {
				$('#resultpic').attr('src','http://localhost:8080/GATE-Interactive-Monitor/conf/Gamma/Gamma.gif');
				$("#resultpic").toggle();
			}

		}
		
		
		function download_result() {
			var type = $("#simu_type option:selected").text();
			if (type === "Cylindrical PET") {
				type = "Cylindrical";
				//alert(type);
				
				$('#downld').attr('href','http://localhost:8080/GATE-Interactive-Monitor/conf/Cylindrical/Cylindrical.root');

				//window.location.href = 'http://localhost:8080/GATE-Interactive-Monitor/conf/Cylindrical/Cylindrical.root';
				} else if (type === "Gamma Camera") {
				type = "Gamma";
				$('#downld').attr('href','http://localhost:8080/GATE-Interactive-Monitor/conf/Gamma/Gamma.root');

				//window.location.href = 'http://localhost:8080/GATE-Interactive-Monitor/conf/Gamma/Gamma.root';
			}

		}

	</script>

</body>
</html>