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

if ($_POST['email'] && $_POST['nama'] && $_POST['password']) {
    $email = $_POST['email'];
    $nama = $_POST['nama'];
    $password = $_POST['password'];

    $sql = "SELECT * FROM customers";
    $result = $c->query($sql);
    if ($result->num_rows > 0) {
        $isEmailDobel = false;
        while ($obj = $result->fetch_assoc()) {
            if ($obj['email'] == $email) {
                $isEmailDobel = true;
            }
        }
    }

    if ($isEmailDobel) {
        $arr = array(
            "result" => "ERROR",
            "message" => "Email sudah pernah dipakai."
        );
        echo json_encode($arr);
    } else {
        $sql = "INSERT INTO customers (email, nama, password)
                VALUES (?,?,?)";
        $sqlMsg = "INSERT INTO customers (email, nama, password) VALUES ($email, $nama, $password)";
        $stmt = $c->prepare($sql);
        $stmt->bind_param('sss', $email, $nama, $password);
        if ($stmt->execute()) {
            $pesan1 = "INSERT success";
        } else {
            $pesan1 = "INSERT failed";
        }

        $arr = array(
            "result" => "OK",
            "sql"    => $sqlMsg,
            "message" => $pesan1
        );
        echo json_encode($arr);
    }
}
