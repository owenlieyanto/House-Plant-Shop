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

if ($_POST['id'] && $_POST['namaBaru'] && $_POST['passwordBaru']) {
    $id = $_POST['id'];
    $namaBaru = $_POST['namaBaru'];
    $passwordBaru = $_POST['passwordBaru'];

    $sql = "UPDATE customers
            SET nama = '$namaBaru', password = '$passwordBaru'
            WHERE id = $id";
    $result = $c->query($sql);
    echo json_encode(array(
        'result' => 'OK',
        'message' => "Nama dan password berhasil di update. id = $id"
    ));
} else {
    echo json_encode(array(
        'result' => 'ERROR',
        'message' => "Profil gagal terupdate. Pastikan semua data sudah terisi."
    ));
}
