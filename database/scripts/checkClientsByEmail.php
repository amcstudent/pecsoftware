<?php
$email = $_GET["email"];
$result = "";
require_once('connection.php');
$con->set_charset("utf8");
$resource = $con->query("SELECT user, pass from pecClients WHERE email='".$email."'");
while ($rows = $resource->fetch_assoc()) {
       $result = $rows["user"]."$".$rows["pass"];
}
echo $result;
$resource->free();
$con->close();
?>