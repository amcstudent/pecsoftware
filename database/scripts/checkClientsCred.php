<?php
$user = $_GET["user"];
$pass = $_GET["pass"];
$str = "";
require_once('connection.php');
$con->set_charset("utf8");
$resource = $con->query('SELECT id, company, position, user, pass, email, i.maxInvoiceNo from pecClients INNER JOIN (SELECT IFNULL(MAX(id), 0) as maxInvoiceNo from invoices) as i');
while ($rows = $resource->fetch_assoc()) {
    if($rows["user"] == $user && $rows["pass"] == $pass)
    {
       $str = $rows["id"]."$".$rows["company"]."$".$rows["position"]."$".$rows["pass"]."$".$rows["email"]."$".$rows["maxInvoiceNo"];
       break;
    }
}
$resource->free();
$con->close();
echo $str;
?>