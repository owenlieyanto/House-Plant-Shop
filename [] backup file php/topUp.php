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

if ($_POST['id'] && $_POST['jumlahTopup']) {
    $id = $_POST['id'];
    $jumlahTopup = $_POST['jumlahTopup'];

    $sql = "UPDATE customers
            SET wallet = wallet + $jumlahTopup
            WHERE id = $id";
    $result = $c->query($sql);

    echo json_encode(array(
        'result' => 'OK',
        'message' => "top up berhasil. customers_id = $id"
    ));
} else {
    echo json_encode(array(
        'result' => 'ERROR',
        'message' => "top up gagal. Pastikan nominal yg dimasukkan sudah benar."
    ));
}
