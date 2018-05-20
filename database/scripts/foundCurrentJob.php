<?php
$id = $_POST["id"];
require_once('connection.php');
$con->set_charset("utf8");
$sql = "delete from currentJobs where jobOrderId = " . $id;
if ($con->query($sql) === TRUE) {
    $sql = "INSERT INTO waitingJobs (jobOrderId) VALUES (" . $id . ")";
    if ($con->query($sql) === TRUE) {
                $sql = "update jobOrders set status = 'waiting' where id= " . $id;
                if ($con->query($sql) === TRUE) {
                    echo "Ok";
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