<?php
$name = $_POST["name"];
$address = $_POST["address"];
$birthdate = $_POST["birthdate"];
$email = $_POST["email"];
$residenceNumber = $_POST["residenceNumber"];
$businessNumber = $_POST["businessNumber"];
$driverInformation = $_POST["driverInformation"];
$degree = $_POST["degree"];
$positionDesired = $_POST["positionDesired"];
$salaryDesired = $_POST["salaryDesired"];
$currentPositionSalary = $_POST["currentPositionSalary"];
$onePreviousPositionSalary = $_POST["onePreviousPositionSalary"];
$twoPreviousPositionSalary = $_POST["twoPreviousPositionSalary"];
$threePreviousPositionSalary = $_POST["threePreviousPositionSalary"];
$leavingReason = $_POST["leavingReason"];
$interviewImpressions = $_POST["interviewImpressions"];
$consultantInitials = $_POST["consultantInitials"];
$ratings = $_POST["ratings"];
$tenureResponsibilities = $_POST["tenureResponsibilities"];
$maritalStatus = $_POST["maritalStatus"];
$travelPreference = $_POST["travelPreference"];
$insertedId = 0;
if (strlen($salaryDesired) == 0)
  $salaryDesired = 0;
if (strlen($currentPositionSalary) == 0)
  $currentPositionSalary = 0;
if (strlen($onePreviousPositionSalary) == 0)
  $onePreviousPositionSalary = 0;
if (strlen($twoPreviousPositionSalary) == 0)
  $twoPreviousPositionSalary = 0;
if (strlen($threePreviousPositionSalary) == 0)
  $threePreviousPositionSalary = 0;
if (strlen($consultantInitials) == 0)
  $consultantInitials = '-';  
require_once('connection.php');
$con->set_charset("utf8");
$sql = "INSERT INTO candidates (name, address, birthdate, email, residence_number, business_number, driver_information, degree, position_desired, salary_desired, current_position_salary,
                               one_previous_position_salary, two_previous_position_salary, three_previous_position_salary, leaving_reason, interview_impressions, consultant_initials, ratings,
                               tenure_responsibilities, marital_status, travel_preference) VALUES ('".$name."', '".$address."', '".$birthdate."', '".$email."', '".$residenceNumber."', '".$businessNumber."', '".$driverInformation."', '".$degree."', '".$positionDesired."',
                               ".$salaryDesired.", ".$currentPositionSalary.", ".$onePreviousPositionSalary.", ".$twoPreviousPositionSalary.", ".$threePreviousPositionSalary.", '".$leavingReason."', '".$interviewImpressions."',
                               '".$consultantInitials."', ".$ratings.", '".$tenureResponsibilities."', '".$maritalStatus."', '".$travelPreference."')";
if ($con->query($sql) === TRUE) {
    echo $con->insert_id;
} else {
    echo "An error occured!";
}
$con->close();
?>