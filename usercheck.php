<?php
	session_start();
	if($_POST['username'] =="gateadmin" && $_POST['password'] =="utk2015"){
		$_SESSION['login'] = "in";
		header("Location: intro.php");	
	} else{
		header("Location: index.php");
		$_SESSION['login'] = "Wrong username or password!";
	}
?>