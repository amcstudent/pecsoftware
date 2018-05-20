<?php
$position = $_POST["position"];
$dateInserted = $_POST["dateInserted"];
$contactName = $_POST["contactName"];
$department = $_POST["department"];
$billingContact = $_POST["billingContact"];
$phone = $_POST["phone"];
$salary = $_POST["salary"];
$startingDate = $_POST["startingDate"];
$address = $_POST["address"];
$businessType = $_POST["businessType"];
$educationalRequirements = $_POST["educationalRequirements"];
$placementFee = $_POST["placementFee"];
$placementDate = $_POST["placementDate"];
$actualStartingDate = $_POST["actualStartingDate"];
$newPosition = $_POST["newPosition"];
$experienceRequirements = $_POST["experienceRequirements"];
$duties = $_POST["duties"];
$bonuses = $_POST["bonuses"];
$travelRequirements = $_POST["travelRequirements"];
$car = $_POST["car"];
$careerOpportunities = $_POST["careerOpportunities"];
$candidateInformation = $_POST["candidateInformation"];
$interview = $_POST["interview"];
$orderTaker = $_POST["orderTaker"];
$counselorUltimatePlacement = $_POST["counselorUltimatePlacement"];
$invoiceNo = $_POST["invoiceNo"];
$firm = $_POST["firm"];
$clientId = $_POST["clientId"];
$insertedId = 0;
if (strlen($salary) == 0)
  $salary = 0;
if (strlen($placementFee) == 0)
  $placementFee = 0;
require_once('connection.php');
$con->set_charset("utf8");
$sql = "INSERT INTO jobOrders (position, date_inserted, contact_name, department, billing_contact, phone, salary, starting_date, address, business_type, educational_requirements,
                               placement_fee, placement_date, actual_starting_date, new_position, experience_requirements, duties, bonuses,
                               travel_requirements, car, career_opportunities, interview, order_taker, counselor_ultimate,
                               invoiceNo , firm, clientId) VALUES ('".$position."', '".$dateInserted."', '".$contactName."', '".$department."', '".$billingContact."', '".$phone."', ".$salary.", '".$startingDate."', '".$address."',
                               '".$businessType."', '".$educationalRequirements."', ".$placementFee.", '".$placementDate."', '".$actualStartingDate."', '".$newPosition."', '".$experienceRequirements."',
                               '".$duties."', '".$bonuses."', '".$travelRequirements."', '".$car."', '".$careerOpportunities."', '".$interview."', '".$orderTaker."',
                               '".$counselorUltimatePlacement."', ".$invoiceNo.", '".$firm."', ".$clientId.")";
if ($con->query($sql) === TRUE) {
    $insertedId = $con->insert_id;
$sql = "INSERT INTO currentJobs (jobOrderId) VALUES (".$insertedId.")";
   if ($con->query($sql) === TRUE) {
       $sql = "INSERT INTO invoices (jobOrderId) VALUES (".$insertedId.")";
       if ($con->query($sql) === TRUE) {
          echo $insertedId;
} else {
    echo "An error occured!";
}
} else {
    echo "An error occured!";
}
} else {
    echo "An error occured!";
}
$con->close();
?>