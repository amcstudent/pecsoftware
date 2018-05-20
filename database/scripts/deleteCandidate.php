<?php
$candidateId = $_POST["candidateId"];
require_once('connection.php');
$con->set_charset("utf8");
$sql = "delete from candidates where id = ".$candidateId;
if ($con->query($sql) === TRUE) {
$sql = "delete from resumes where candidateId = ".$candidateId;
     if ($con->query($sql) === TRUE) {
     $sql = "delete from candidatesFoundHistory where candidateId = ".$candidateId;
          if ($con->query($sql) === TRUE) {
     echo "The candidate has been deleted successfully!";
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