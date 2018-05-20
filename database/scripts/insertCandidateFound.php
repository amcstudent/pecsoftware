<?php
$clientId = $_POST["clientId"];
$candidateId = $_POST["candidateId"];
require_once('connection.php');
$con->set_charset("utf8");
$sql = "INSERT INTO candidatesFound (candidateId, clientId) VALUES (" . $candidateId . ", " . $clientId . ")";
if ($con->query($sql) === TRUE) {
    echo "Ok";
} else {
    echo "An error occured!";
}
$con->close();
?>