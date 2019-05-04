<?php
include "DataConnector.php";

$sql = "SELECT symp_id,symptom,dise_id,disease FROM healthguider_data";

if(isset($_GET['symptom'])){
    $sql = "SELECT symp_id,symptom FROM healthguider_data WHERE symp_id = ".$_GET['symptom'];
    if(isset($_GET['symp'])){
        $sql = "SELECT symp_id,symptom FROM healthguider_data WHERE symptom LIKE '%".$_GET["symp"]."%'";
    }
}

if(isset($_GET['disease'])){
    $sql = "SELECT dise_id,disease FROM healthguider_data WHERE dise_id = ".$_GET['disease'];
    if(isset($_GET['dise'])){
        $sql = "SELECT dise_id,disease FROM healthguider_data WHERE disease LIKE '%".$_GET["dise"]."%'";
    }
}

$sth = mysqli_query($sql);
$rows = array();
while($r = mysqli_fetch_assoc($sth)) {
    $rows[] = $r;
}
echo json_encode($rows);
mysqli_close($con);
?>