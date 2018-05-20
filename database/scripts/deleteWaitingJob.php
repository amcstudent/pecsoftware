<?php
$id = $_POST["id"];
$candidateId = $_POST["candidateId"];
$found = 0;
require_once('connection.php');
$con->set_charset("utf8");
$sql = "delete from waitingJobs where jobOrderId = ".$id;
if ($con->query($sql) === TRUE) {
   $sql = "delete from jobOrders where id = ".$id;
   if ($con->query($sql) === TRUE) {
     $found = 0;
    } else {
    $found = 1;
     }    
} else {
$found = 1;
}   
if ($found == 0 && $candidateId > 0)
   {
      $sql = "delete from candidatesFoundHistory where candidateId = ".$candidateId;
   if ($con->query($sql) === TRUE) {
   $sql = "delete from candidates where id = ".$candidateId;
             if ($con->query($sql) === TRUE) {
             $sql = "delete from resumes where candidateId = ".$candidateId;
          if ($con->query($sql) === TRUE) {
       $found = 0;
    } else {
    $found = 1;
     }
    } else {
      $found = 1;
     }
    } else {
      $found = 1;
     }  
   }
   if ($found == 0)
     echo "Ok";
   else
     echo "An error occured!";
$con->close();
?>