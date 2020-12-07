<?php
error_reporting(E_ERROR | E_PARSE);
$c = new mysqli("localhost", "nmp160418081", "ubaya", "nmp160418081");
if ($c->connect_errno) {
    $arr = array(
        "result" => "ERROR",
        "message" => "Failed to Connect"
    );
    echo json_encode($arr);
    die();
}

if ($_POST['email'] && $_POST['password']) {
    $email = $_POST['email'];
    $password = $_POST['password'];

    $sql = "SELECT * FROM customers 
            WHERE email='$email' AND password='$password'";
    $result = $c->query($sql);
    $array = array();
    if ($result->num_rows > 0) {
        while ($obj = $result->fetch_object()) {
            $array[] = $obj;
        }
        echo json_encode(array('result' => 'OK', 'message' => 'Berhasil login.', 'data' => $array));
    } else {
        echo json_encode(array('result' => 'ERROR', 'message' => 'Nama atau password salah.'));
        die();
    }
}
