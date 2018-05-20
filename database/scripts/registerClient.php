<?php
$company = $_REQUEST["company"];
$position = $_REQUEST["position"];
$user = $_REQUEST["user"];
$pass = $_REQUEST["pass"];
$email = $_REQUEST["email"];
$msg = "";
require_once('connection.php');
$con->set_charset("utf8");
$sql = "INSERT INTO pecClients (company, position, user, pass, email) VALUES ('".$company."', '".$position."', '".$user."', '".$pass."', '".$email."')";
if ($con->query($sql) === TRUE) {
    $msg = "Activated Successfully!";
} else {
    $msg = "An error occured!";
}
$con->close();
echo '<script>';
echo 'alert("'.$msg.'")';
echo '</script>';
?>