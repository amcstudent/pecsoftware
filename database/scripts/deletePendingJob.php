<?php
$id = $_POST["id"];
require_once('connection.php');
$con->set_charset("utf8");
$sql = "delete from pendingJobs where jobOrderId = ".$id;
if ($con->query($sql) === TRUE) {
   $sql = "delete from jobOrders where id = ".$id;
   if ($con->query($sql) === TRUE) {
       echo "Ok";
    } else {
       echo "An error occured!";
     }    
} else {
    echo "An error occured!";
}
$con->close();
?>