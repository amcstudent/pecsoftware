<?php
$candidateId = $_POST["candidateId"];
$jobOrderId = $_POST["jobOrderId"];
$clientId = $_POST["clientId"];
$exists = 0;
require_once('connection.php');
$con->set_charset("utf8");
$sql = "delete from candidatesFound where candidateId = " . $candidateId;
if ($con->query($sql) === TRUE) {  
    $sql = "insert into candidatesFoundHistory (candidateId, clientId) values (" . $candidateId . ", " . $clientId . ")";
                if ($con->query($sql) === TRUE) {
        $result = mysqli_query($con,"SELECT 1 FROM currentJobs where jobOrderId= " . $jobOrderId);
        if(mysqli_num_rows($result) > 0) {
        $exists = 1;
         $sql = "delete from currentJobs where jobOrderId = " . $jobOrderId;
         $con->query($sql);
        }
        $result = mysqli_query($con,"SELECT 1 FROM pendingJobs where jobOrderId= " . $jobOrderId);
        if(mysqli_num_rows($result) > 0) {
        $exists = 1;
         $sql = "delete from pendingJobs where jobOrderId = " . $jobOrderId;
         $con->query($sql);
        }
        if ($exists == 1)
        {
           $sql = "delete from jobOrders where id = " . $jobOrderId;
           $con->query($sql);
        }
        else
        {
          $sql = "update jobOrders set status = 'found', candidateId = " . $candidateId . " where id= " . $jobOrderId;
          $con->query($sql);
        }
            echo "The candidate has checked successfully!";
        } else {
            echo "An error occured!";
        }
    } else {
        echo "An error occured!";
    }
$con->close();
?>