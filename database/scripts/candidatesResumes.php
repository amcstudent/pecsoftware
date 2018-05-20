<?php
$id = $_GET["id"];
require_once('connection.php');
$con->set_charset("utf8");
$resource = $con->query("SELECT *from resumes as r where r.candidateId IN (select candidateId from candidatesFound where clientId = ".$id.")");
while ($rows = $resource->fetch_assoc()) {
       echo "$".$rows["id"]."$".$rows["common_duties"]."$".$rows["career_opportunities"]."$".$rows["educational_requirements"]."$".$rows["salary_ranges"]
           ."$".$rows["experience_requirements"]."$".$rows["candidateId"];
}
$resource->free();
$con->close();
?>