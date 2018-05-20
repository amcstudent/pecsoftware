<?php
require_once('connection.php');
$con->set_charset("utf8");
$resource = $con->query('SELECT w.jobOrderId, j.position, date_inserted, firm, department, contact_name, address, billing_contact, business_type, phone, 
                         educational_requirements, salary, starting_date, experience_requirements, new_position, duties, bonuses, travel_requirements,
                         car, career_opportunities, interview, order_taker, placement_fee, counselor_ultimate, invoiceNo,
                         placement_date, candidateId, actual_starting_date, status, pc.email as clientEmail, pc.id as clientId from jobOrders as j inner join waitingJobs as w on j.id = w.jobOrderId
                         inner join (select id, email from pecClients) as pc on j.clientId = pc.id order by j.id desc');
while ($rows = $resource->fetch_assoc()) {
       echo "$".$rows["jobOrderId"]."$".$rows["position"]."$".$rows["date_inserted"]."$".$rows["firm"]."$".$rows["department"]."$".$rows["contact_name"]."$".$rows["address"]."$".$rows["billing_contact"]
       ."$".$rows["business_type"]."$".$rows["phone"]."$".$rows["educational_requirements"]."$".$rows["salary"]."$".$rows["starting_date"]
       ."$".$rows["experience_requirements"]."$".$rows["new_position"]."$".$rows["duties"]."$".$rows["bonuses"]."$".$rows["travel_requirements"]
       ."$".$rows["car"]."$".$rows["career_opportunities"]."$".$rows["interview"]."$".$rows["order_taker"]
       ."$".$rows["placement_fee"]."$".$rows["counselor_ultimate"]."$".$rows["invoiceNo"]."$".$rows["placement_date"]."$".$rows["actual_starting_date"]."$".$rows["candidateId"]."$".$rows["status"]
       ."$".$rows["clientEmail"]."$".$rows["clientId"];
}
$resource->free();
$con->close();
?>