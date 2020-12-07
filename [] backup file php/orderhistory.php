<?php
error_reporting(E_ERROR | E_PARSE);

$c = new mysqli("localhost", "nmp160418081", "ubaya", "nmp160418081");
if($c->connect_errno) {
	echo json_encode(array('result'=> 'ERROR', 'message' => 'Failed to connect DB'));
die();
}

$id = $_GET['id'];

$sql = "SELECT o.id, o.total, o.orderDate, COUNT( od.quantity ) AS order_count, SUM( od.quantity ) AS order_sum FROM orders o INNER JOIN detail_order od ON o.id = od.orders_id WHERE customers_id = $id group by o.id ORDER BY id DESC";
$result = $c->query($sql);
$array = array();
if ($result->num_rows > 0) {
	while ($obj = $result -> fetch_assoc()) {
		$array[] = $obj;

	}
	if ($array[0]['order_count'] == 0)
	{
		echo json_encode(array('result' => 'KOSONG', 'data' => "data kosong"));
	}
	else
	{
		echo json_encode(array('result' => 'OK', 'data' => $array));
	}
	
	
} else {
	echo json_encode(array('result'=> 'ERROR', 'message' => 'No data found'));
	die();
}

?>
