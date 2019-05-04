<?php
include "DataConnector.php";

$sql = "SELECT 0";

if(isset($_GET['adduser'])){
    $sql = "INSERT INTO healthguider_users(basics,diagnosed) VALUES '".$_GET['adduser']."','".$_GET['details']."'";
}

if(isset($_GET['getuser'])){
    $sql = "SELECT * FROM healthguider_users WHERE user_id = ".$_GET['getuser'];
}

$sth = mysqli_query($sql);
if(isset($_GET['adduser'])){
    echo $con->insert_id;
} else {
$rows = array();
while($r = mysqli_fetch_assoc($sth)) {
    $rows[] = $r;
}
echo json_encode($rows);
}
mysqli_close($con);
?>