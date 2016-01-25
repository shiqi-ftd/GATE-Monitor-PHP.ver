<?php
session_start ();
?>

<!DOCTYPE html>
<html>
<head>
<link type="text/css" rel="stylesheet" href="css/login.css">
<title>Gate Interactive Monitor Login</title>
<meta name="author" content="Shiqi Zhong">
</head>

<body>
	<div id="header">
		<img id="logo" src="./images/logo.gif" align="left">

		<p id="name"></p>
		<a href="mailto:szhong4@vols.utk.edu" id="email">Mail to Shiqi Zhong</a>
		<p id="info">
		<?php
		error_reporting ( 0 );
		echo "IP Address:{$_SERVER["REMOTE_ADDR"]}";
		?>			
		<br> Today's date: 
		<?php echo date('l jS \of F Y h:i:s A');?>
		</p>
		<br>

	</div>

	<form id="login" action="usercheck.php" method="Post">
		<h1>Log In</h1>
		<fieldset id="inputs">
			<input name="username" id="username" type="text"
				placeholder="Username" autofocus required> <input name="password"
				id="password" type="password" placeholder="Password" autofocus
				required>
		</fieldset>
		<fieldset id="actions">
			<input type="submit" id="submit" value="Log in"> <a href="">Forgot
				your password?</a><a href="">Register</a>
		</fieldset>
		<fieldset>
		
		<?php
// 		echo $_SESSION ['login'];
// 		echo strcmp($_SESSION ['login'],'Wrong username or password!');
		if (strcmp($_SESSION ['login'],'Wrong username or password!')==0) {
			echo $_SESSION ['login'];
		}
		?>		
		</fieldset>

	</form>
	<script src="scripts/jquery-1.9.1.js"></script>


</body>
</html>
