<?php
$company = strtolower($_GET["company"]);
$user = strtolower($_GET["user"]);
$email = strtolower($_GET["email"]);
$msg = "";
require_once('connection.php');
$con->set_charset("utf8");
$result = mysqli_query($con,"SELECT 1 FROM pecClients WHERE LOWER(company)='".$company."'");
if(mysqli_num_rows($result) > 0) {
    $msg = "Company already exists!";
} else {
    $result = mysqli_query($con,"SELECT 1 FROM pecClients WHERE LOWER(user)='".$user."'");
        if(mysqli_num_rows($result) > 0) {
            $msg = "Username already exists!";
        } else {
            $result = mysqli_query($con,"SELECT 1 FROM pecClients WHERE email='".$email."'");
                if(mysqli_num_rows($result) > 0) {
                     $msg = "Email already exists!";
                } else {
                      $msg = "Ok";
                    }
           }
  }
$con->close();
echo $msg;
?>