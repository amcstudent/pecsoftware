<?php
$id = $_POST["id"];
$pass = $_POST["pass"];
require_once('connection.php');
$con->set_charset("utf8");
$sql = "update pecClients set pass = '".$pass."' where id = ".$id;
if ($con->query($sql) === TRUE) {
    echo "Your password has updated successfully!";  
} else {
    echo "An error occured!";
}
$con->close();
?>