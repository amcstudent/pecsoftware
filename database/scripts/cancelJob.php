<?php
$jobId = $_POST["jobId"];
require_once('connection.php');
$con->set_charset("utf8");
$sql = "insert into cancelledJobs (jobOrderId) values (".$jobId.")";
if ($con->query($sql) === TRUE) {
   $sql = "delete from currentJobs where jobOrderId=".$jobId;
if ($con->query($sql) === TRUE) {
$sql = "delete from pendingJobs where jobOrderId=".$jobId;
    if ($con->query($sql) === TRUE) {
    $sql = "delete from waitingJobs where jobOrderId=".$jobId;
        if ($con->query($sql) === TRUE) {
    $sql = "update jobOrders set status = '' where id=".$jobId;
            if ($con->query($sql) === TRUE) {
    echo "Your job request has been cancelled sucessfully!";  
} else {
    echo "An error occured!";
}
} else {
    echo "An error occured!";
}   
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