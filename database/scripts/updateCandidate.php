<?php
$candidateId = $_POST["candidateId"];
$name = $_POST["name"];
$address = $_POST["address"];
$birthdate = $_POST["birthdate"];
$email = $_POST["email"];
$residenceNumber = $_POST["residenceNumber"];
$businessNumber = $_POST["businessNumber"];
$driverInformation = $_POST["driverInformation"];
$degree = $_POST["degree"];
$positionDesired = $_POST["positionDesired"];
$geographicPreference = $_POST["geographicPreference"];
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
$candidateHired = $_POST["candidateHired"];
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
$sql = "UPDATE candidates set name='".$name."', address='".$address."', birthdate='".$birthdate."', email='".$email."', residence_number='".$residenceNumber."', business_number='".$businessNumber."', 
                             driver_information='".$driverInformation."', degree='".$degree."', position_desired='".$positionDesired."', geographic_preference='".$geographicPreference."',
                               salary_desired=".$salaryDesired.", current_position_salary=".$currentPositionSalary.", one_previous_position_salary=".$onePreviousPositionSalary.", 
                               two_previous_position_salary=".$twoPreviousPositionSalary.", three_previous_position_salary=".$threePreviousPositionSalary.", leaving_reason='".$leavingReason."', 
                               interview_impressions='".$interviewImpressions."', consultant_initials='".$consultantInitials."', ratings=".$ratings.", tenure_responsibilities='".$tenureResponsibilities."',
                               marital_status='".$maritalStatus."', travel_preference='".$travelPreference."' where id=".$candidateId;
if ($con->query($sql) === TRUE) {
    echo "The candidate has been updated successfully!";
} else {
    echo "An error occured!";
}
$con->close();
?>