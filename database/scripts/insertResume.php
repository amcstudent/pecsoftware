<?php
$commomDuties = $_POST["commomDuties"];
$careerOpportunities = $_POST["careerOpportunities"];
$educationalRequirements = $_POST["educationalRequirements"];
$experienceRequirements = $_POST["experienceRequirements"];
$salaryRanges = $_POST["salaryRanges"];
$candidateId = $_POST["candidateId"];
require_once('connection.php');
$con->set_charset("utf8");
$sql = "INSERT INTO resumes (common_duties, career_opportunities, educational_requirements, salary_ranges, experience_requirements, candidateId) 
        VALUES ('".$commomDuties."', '".$careerOpportunities."', '".$educationalRequirements."', '".$salaryRanges."', '".$experienceRequirements."', ".$candidateId.")";
if ($con->query($sql) === TRUE) {
    echo $con->insert_id;
} else {
    echo "An error occured!";
}
$con->close();
?>