<?php
require_once('connection.php');
$con->set_charset("utf8");
$resource = $con->query('SELECT *from candidates as c where c.id IN (select candidateId from candidatesFoundHistory) order by ratings desc');
while ($rows = $resource->fetch_assoc()) {
       echo "$".$rows["id"]."$".$rows["name"]."$".$rows["address"]."$".$rows["birthdate"]."$".$rows["email"]."$".$rows["residence_number"]."$".$rows["business_number"]."$".$rows["marital_status"]."$".$rows["driver_information"]
       ."$".$rows["degree"]."$".$rows["position_desired"]."$".$rows["salary_desired"]."$".$rows["geographic_preference"]."$".$rows["travel_preference"]
       ."$".$rows["current_position_salary"]."$".$rows["one_previous_position_salary"]."$".$rows["two_previous_position_salary"]."$".$rows["three_previous_position_salary"]."$".$rows["tenure_responsibilities"]
       ."$".$rows["leaving_reason"]."$".$rows["interview_impressions"]."$".$rows["ratings"]."$".$rows["consultant_initials"];
}
$resource->free();
$con->close();
?>