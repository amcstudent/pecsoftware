<?php
$candidateId = $_POST["candidateId"];
$clientId = $_POST["clientId"];
require_once('connection.php');
$con->set_charset("utf8");
$sql = "delete from candidatesFound where candidateId = " . $candidateId . " and clientId = ".$clientId;
if ($con->query($sql) === TRUE) {
         echo "The candidate has deleted successfully!";
} else {
         echo "An error occured!";
       }
$con->close();
?>