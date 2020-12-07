<?php
error_reporting(E_ERROR | E_PARSE);

$c = new mysqli("localhost", "nmp160418081", "ubaya", "nmp160418081");
if ($c->connect_errno) {
	echo json_encode(array('result' => 'ERROR', 'message' => 'Failed to connect DB'));
	die();
}

$user_id = $_GET['id'];

$sql = "SELECT p.id AS product_id, p.name AS name, p.price AS price, p.image AS image, c.quantity AS quantity 
		FROM products p 
		inner join carts c on p.id = c.products_id 
		where customers_id = $user_id";

$result = $c->query($sql);
$array = array();
if ($result->num_rows > 0) {
	while ($obj = $result->fetch_object()) {
		$array[] = $obj;
	}
	echo json_encode(array('result' => 'OK', 'data' => $array));
} else {
	echo json_encode(array('result' => 'ERROR', 'message' => 'No data found'));
	die();
}
