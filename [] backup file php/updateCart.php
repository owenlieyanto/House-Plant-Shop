<?php
error_reporting(E_ERROR | E_PARSE);

$c = new mysqli("localhost", "nmp160418081", "ubaya", "nmp160418081");
if ($c->connect_errno) {
    echo json_encode(array('result' => 'ERROR', 'message' => 'Failed to connect DB'));
    die();
}

$customers_id = $_GET['customers_id'];
$products_id = $_GET['products_id'];
$quantity = $_GET['quantity'];

$sql = "SELECT * FROM carts 
        WHERE customers_id = $customers_id AND products_id = $products_id";
$result = $c->query($sql);

if ($result->num_rows > 0) {
    if ($arr = $result->fetch_assoc()) {
        if (($arr['quantity'] + $quantity) <= 0) {
            // hapus cart
            $sql = "DELETE FROM carts 
                    WHERE customers_id = $customers_id AND products_id = $products_id";
            $result = $c->query($sql);
            echo json_encode(array(
                'result' => 'OK',
                'message' => "cart dihapus dr DB. customers_id = $customers_id AND products_id = $products_id"
            ));
        } else {
            // update quantity
            $sql = "UPDATE carts
                SET quantity = quantity + $quantity
                WHERE customers_id = $customers_id AND products_id = $products_id";
            $result = $c->query($sql);

            echo json_encode(array(
                'result' => 'OK',
                'message' => "quantity cart diupdate. customers_id = $customers_id AND products_id = $products_id"
            ));
        }
    }
} else {
    // tambah cart
    $sql = "INSERT INTO carts (customers_id, products_id, quantity)
                VALUES ($customers_id, $products_id, $quantity)";
    $result = $c->query($sql);

    echo json_encode(array(
        'result' => 'OK',
        'message' => "Cart baru ditambahkan. customers_id = $customers_id AND products_id = $products_id"
    ));
}
