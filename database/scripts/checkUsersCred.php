<?php
$user = $_GET["user"];
$pass = $_GET["pass"];
$id = 0;
require_once('connection.php');
$con->set_charset("utf8");
$resource = $con->query('SELECT id, user, pass from pecUsers');
while ($rows = $resource->fetch_assoc()) {
    if($rows["user"] == $user && $rows["pass"] == $pass)
    {
       $id = $rows["id"];
       break;
    }
}
$resource->free();
$con->close();
echo $id;
?>