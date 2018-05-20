<?php
$jobId = $_POST["jobId"];
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
$interview = $_POST["interview"];
$orderTaker = $_POST["orderTaker"];
$counselorUltimatePlacement = $_POST["counselorUltimatePlacement"];
if (strlen($salary) == 0)
  $salary = 0;
if (strlen($placementFee) == 0)
  $placementFee = 0;
require_once('connection.php');
$con->set_charset("utf8");
$sql = "UPDATE jobOrders set contact_name='".$contactName."', department='".$department."', billing_contact='".$billingContact."', phone='".$phone."', salary=".$salary.", starting_date='".$startingDate."', 
                             address='".$address."', business_type='".$businessType."', educational_requirements='".$educationalRequirements."',
                               placement_fee=".$placementFee.", placement_date='".$placementDate."', actual_starting_date='".$actualStartingDate."', new_position='".$newPosition."', 
                               experience_requirements='".$experienceRequirements."', duties='".$duties."', bonuses='".$bonuses."',
                               travel_requirements='".$travelRequirements."', car='".$car."', career_opportunities='".$careerOpportunities."', 
                               interview='".$interview."', order_taker='".$orderTaker."', counselor_ultimate='".$counselorUltimatePlacement."' where id=".$jobId;
if ($con->query($sql) === TRUE) {
    echo "Your job request has been updated successfully!";
} else {
    echo "An error occured!";
}
$con->close();
?>