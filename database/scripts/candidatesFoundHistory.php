<?php
$id = $_GET["id"];
require_once('connection.php');
$con->set_charset("utf8");
$resource = $con->query("SELECT id, name from candidates as c where c.id NOT IN (select candidateId FROM candidatesFoundHistory where clientId = ".$id.") and c.id NOT IN (select candidateId FROM candidatesFound where clientId = ".$id.")
                         and c.id IN (select candidateId FROM resumes) order by ratings desc");
while ($rows = $resource->fetch_assoc()) {
       echo "$".$rows["id"]."$".$rows["name"];
}
$resource->free();
$con->close();
?>