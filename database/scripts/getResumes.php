<?php
require_once('connection.php');
$con->set_charset("utf8");
$resource = $con->query('SELECT *from resumes');
while ($rows = $resource->fetch_assoc()) {
       echo "$".$rows["id"]."$".$rows["common_duties"]."$".$rows["career_opportunities"]."$".$rows["educational_requirements"]."$".$rows["salary_ranges"]
           ."$".$rows["experience_requirements"]."$".$rows["candidateId"];
}
$resource->free();
$con->close();
?>