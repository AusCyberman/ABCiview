<?php

/*
 * Following code will get single product details
 * A product is identified by product id (pid)
 */

// array for JSON response
$response = array();

// include db connect class
require_once __DIR__ . '/db_config.php';

// connecting to db
$con = mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD) or die(mysqli_error($con));

// Selecting database
$db = mysqli_select_db($con,DB_DATABASE) or die(mysqli_error($con)) or die(mysqli_error($con));



// check for post data

if (isset($_GET["contentid"])) {
    $contentid = $_GET['contentid'];
    $result = mysqli_query($con,"SELECT * FROM tblContent where id =$contentid");
}else{
    $result = mysqli_query($con,"SELECT * FROM tblContent");
}

    if (!empty($result)) {
        // check for empty result
        if (mysqli_num_rows($result) > 0) {

            $result = mysqli_fetch_array($result);

            $product = array();
            $product["id"] = $result["id"];
            $product["name"] = $result["name"];
            $product["price"] = $result["price"];
            $product["description"] = $result["description"];
            $product["created_at"] = $result["created_at"];
            $product["updated_at"] = $result["updated_at"];
            // success
            $response["success"] = 1;

            // user node
            $response["product"] = array();

            array_push($response["product"], $product);

            // echoing JSON response
            echo json_encode($response);
        } else {
            // no product found
            $response["success"] = 0;
            $response["message"] = "No Content found";

            // echo no users JSON
            echo json_encode($response);
        }
    } else {
        // no product found
        $response["success"] = 0;
        $response["message"] = "No Content found";

        // echo no users JSON
        echo json_encode($response);
    }

    // required field is missing

?>