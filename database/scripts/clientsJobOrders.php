<?php
$id = $_GET["id"];
require_once('connection.php');
$con->set_charset("utf8");
$resource = $con->query("SELECT j.id, position, date_inserted, firm, department, contact_name, address, billing_contact, business_type, phone, 
                         educational_requirements, salary, starting_date, experience_requirements, new_position, duties, bonuses, travel_requirements,
                         car, career_opportunities, interview, order_taker, placement_fee, counselor_ultimate, invoiceNo,
                         placement_date, actual_starting_date from jobOrders as j where j.clientId = ".$id." and j.status != 'found' and j.id NOT IN (select jobOrderId FROM cancelledJobs) order by j.id desc");
while ($rows = $resource->fetch_assoc()) {
       echo "$".$rows["id"]."$".$rows["position"]."$".$rows["date_inserted"]."$".$rows["firm"]."$".$rows["department"]."$".$rows["contact_name"]."$".$rows["address"]."$".$rows["billing_contact"]
       ."$".$rows["business_type"]."$".$rows["phone"]."$".$rows["educational_requirements"]."$".$rows["salary"]."$".$rows["starting_date"]
       ."$".$rows["experience_requirements"]."$".$rows["new_position"]."$".$rows["duties"]."$".$rows["bonuses"]."$".$rows["travel_requirements"]
       ."$".$rows["car"]."$".$rows["career_opportunities"]."$".$rows["interview"]."$".$rows["order_taker"]
       ."$".$rows["placement_fee"]."$".$rows["counselor_ultimate"]."$".$rows["invoiceNo"]."$".$rows["placement_date"]."$".$rows["actual_starting_date"];
}
$resource->free();
$con->close();
?>