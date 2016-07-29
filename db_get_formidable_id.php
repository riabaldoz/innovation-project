<?php
//This PHP file gets the matching Formidable ID, using the inputting SSO ID
//array for JSON response
$response = array();

//check for required fields
if (isset($_POST['SSO_ID']) && isset($_POST['SSO_Password'])) {
	$SSO_ID = $_POST['SSO_ID'];
	$SSO_Password = $_POST['SSO_Password'];

	//include db connect class
	require_once __DIR__ . '/db_connect.php';

	//connecting to db
	$db = new DB_CONNECT();

	//mysql retrieving the Formidable_ID that matches SSO_ID
	$result = mysql_query("SELECT Formidable_ID FROM SSOFormidableLogins where SSO_ID='$SSO_ID'");

	//check if row found or not
	if ($result) {
		//sucessfully find
		$response["success"] = 1;
		//$response["message"] = mysql_fetch_object($result, 'Formidable_ID');
		$response["message"] = "Your Formidable ID is ria.baldoz@ge.com ";// . mysql_fetch_object($result, 'Formidable_ID'); //not sure if this works

		//echoing JSON response
		echo json_encode($response);
	] else {
		//no record found
		$response["success"] = 0;
		$response["message"] = "Oops! You are not registered to use Formidable. Call IT Support.";

		//echoing JSON response
		echo json_encode($response);
	}
} else {
	//required field is missing
	$response["success"] = 0;
	$response["message"] = "Enter both SSO ID and password";

	//echoing JSON response
	echo json_encode($response);
}
?>
