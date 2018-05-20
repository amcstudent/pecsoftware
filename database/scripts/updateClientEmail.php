<?php
$id = $_POST["id"];
$email = $_POST["email"];
$msg = "";
require_once('connection.php');
$con->set_charset("utf8");
$result = mysqli_query($con,"SELECT 1 FROM pecClients WHERE email='".$email."'");
if(mysqli_num_rows($result) > 0) {
    $msg = "Email already exists!";
} else {
$sql = "update pecClients set email = '".$email."' where id = ".$id;
if ($con->query($sql) === TRUE) {
     $msg = "Your email has updated successfully!";   
} else {
    $msg = "An error occured!";
}
}
$con->close();
echo $msg;
?>