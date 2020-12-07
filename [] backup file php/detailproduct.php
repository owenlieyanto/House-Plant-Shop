<?php
error_reporting(E_ERROR | E_PARSE);

$c = new mysqli("localhost", "nmp160418081", "ubaya", "nmp160418081");
if ($c->connect_errno) {
	echo json_encode(array('result' => 'ERROR', 'message' => 'Failed to connect DB'));
	die();
}

$id = $_GET['id'];

$sql = "SELECT p.*, c.name AS category_name FROM products p
		INNER JOIN categories c ON p.categories_id = c.id
		WHERE p.id= $id";
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
