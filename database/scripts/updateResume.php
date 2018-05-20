<?php
$commomDuties = $_POST["commomDuties"];
$careerOpportunities = $_POST["careerOpportunities"];
$educationalRequirements = $_POST["educationalRequirements"];
$experienceRequirements = $_POST["experienceRequirements"];
$salaryRanges = $_POST["salaryRanges"];
$candidateId = $_POST["candidateId"];
require_once('connection.php');
$con->set_charset("utf8");
$sql = "UPDATE resumes set common_duties='".$commomDuties."', career_opportunities='".$careerOpportunities."', educational_requirements='".$educationalRequirements."', experience_requirements='".$experienceRequirements."', 
                             salary_ranges='".$salaryRanges."' where candidateId=".$candidateId;
if ($con->query($sql) === TRUE) {
    echo "The resume updated successfully!";
} else {
    echo "An error occured!";
}
$con->close();
?>