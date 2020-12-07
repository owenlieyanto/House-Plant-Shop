<?php
error_reporting(E_ERROR | E_PARSE);

$c = new mysqli("localhost", "nmp160418081", "ubaya", "nmp160418081");
if($c->connect_errno) {
	echo json_encode(array('result'=> 'ERROR', 'message' => 'Failed to connect DB'));
die();
}

$id = $_GET['id']; //id customer
$total = $_GET['total'];
$wallet = $_GET['wallet'];

//INSERTION 1: Tabel orders
$sql = "INSERT INTO orders (total, customers_id) values(?,?)";
$stmt = $c->prepare($sql);
$stmt->bind_param("ii", $total, $id);
$stmt->execute();

//jika insertion berhasil
if ($stmt->affected_rows > 0)
{
	//ambil id terakhir
	$sql2 = "SELECT max(id) as maxId FROM orders";
	$result = $c->query($sql2);
	$array = array();
	if ($result->num_rows > 0) {
		while ($obj = $result -> fetch_assoc()) {
			$array[] = $obj;
		}
		$id_order = $array[0]['maxId'];
		//echo json_encode(array('result' => 'OK', 'data' => $array, 'id' => $id));

		////////////////////////////////////////////////////////////////////////////
		//INSERTION 2: pindahkan barang dari tabel carts ke tabel orders
		//Step1: get barang from carts, simpan didalam array2
		$sql3 = "SELECT c.products_id, c.quantity, c.quantity * p.price AS subtotal FROM carts c INNER JOIN products p ON c.products_id = p.id WHERE customers_id = ".$id;
		$result = $c->query($sql3);
		$array2 = array();
		if ($result->num_rows > 0) {
			while ($obj = $result -> fetch_assoc()) {
				$array2[] = $obj;
			}
			//echo json_encode(array('result' => 'OK', 'isi cart' => $array2));

			//Step2: insert satu persatu ke tabel detail_order
			$jumlah_barang = count($array2);
			for ($i = 0; $i < $jumlah_barang; $i++) {
				$sql4 = "INSERT INTO detail_order (products_id, orders_id, price, quantity) values(?,?,?,?)";
				$stmt = $c->prepare($sql4);
				$stmt->bind_param("iiii", $array2[$i]['products_id']
										, $id_order
										, $array2[$i]['subtotal']
										, $array2[$i]['quantity']);
				$stmt->execute();
			}
			
			//JIKA BERHASIL INSERT
			if ($stmt->affected_rows > 0){
				//echo json_encode(array('result' => 'OK', 'berhasil' => 'data masuk', 'jumlah barang' => $jumlah_barang));

				/////////////////////////////////////////////////////////////////
				// DELETE DATA FROM TABLE CARTS
				//
				$sql5 = "DELETE FROM carts WHERE customers_id = (?)";
				$stmt = $c->prepare($sql5);
				$stmt->bind_param("i", $id);
				$stmt->execute();

				if ($stmt->affected_rows > 0) {
					// echo json_encode(array('result' => 'OK', 'berhasil' => 'penghapusan cart berhasil'));

					///////////////////////////////////////////////////////////////////////
					// UPDATE WALLET
					$current_wallet = $wallet - $total;
					$sql6 = "UPDATE customers SET wallet = (?) WHERE id= (?)";
					$stmt = $c->prepare($sql6);
					$stmt->bind_param("ii", $current_wallet ,$id);
					$stmt->execute();
					if ($stmt->affected_rows > 0) {
						echo json_encode(array('result' => 'OK', 'berhasil' => 'transaksi berhasil'));
					}
					else{
						echo json_encode(array('result'=> 'ERROR', 'message' => 'transaksi gagal, cek sintaks sql5', 'isi sql' => $sql6));
						die();
					}

				}
				else{
					echo json_encode(array('result'=> 'ERROR', 'message' => 'gagal hapus cart, cek sintaks sql5', 'isi sql' => $sql5));
					die();
				}

			}
			else{
				echo json_encode(array('result'=> 'ERROR', 'message' => 'gagal insert to detail, cek sintaks sql4', 'isi sql' => $sql4, 'jumlah barang' => $jumlah_barang));
				die();
			}
		}
		else {
			echo json_encode(array('result'=> 'ERROR', 'message' => 'gagal ambil isi cart, cek sintaks sql3', 'isi sql' => $sql3));
			die();
		}
	}
	else {
		echo json_encode(array('result'=> 'ERROR', 'message' => 'gagal ambil id, cek sintaks sql2'));
		die();
	}
} else {
	echo json_encode(array('result'=> 'ERROR', 'message' => 'gagal insert order, cek sintaks sql'));
	die();
}

?>
