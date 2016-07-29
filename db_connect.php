<?php
 //this PHP connects to the database on the Pi server
class DB_CONNECT {

	//constructor
	function __construct() {
		//connecting to database
		$this->connect();
	}
	//deconstructor
	function __destruct() {
		//closing db connection
		$this->close();
	}
	function __connect() {
		//import database connection variables
		require_once __DIR__ . '/db_config.php';

		//connecting to mysql database
		$con = mysql_connect(DB_SERVER, DB_USER, DB_PASSWORD) or die(mysql_error());

		//selecting database
		$db = mysql_select_db(DB_DATABASE) or die(mysql_error()) or die(mysql_error());

		//returing connection cursor
		return $con;
	}
	function close() {
		//closing db connection
		mysql-close();
	}
}
?>
