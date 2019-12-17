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






    // check for empty result



$result = mysqli_query($con,"SELECT * FROM tblchannels where active=1");
$response["channels"] = array();
        while ($row = mysqli_fetch_array($result)) {
            // temp user array
            $channel = array();
            $channel["id"] = $row["id"];
            $channel["channel_name"] = $row["channel_name"];
            $channel["filename"] = $row["filename"];

            $channel["active"] = $row["active"];

            // push single product into final response array

            array_push($response["channels"], $channel);
        }
        if(isset($_GET["adult"])){
            $adult=$_GET["adult"];
            $result = mysqli_query($con,"SELECT * FROM vwcontent where adult=$adult ");

        }else{
            $result = mysqli_query($con,"SELECT *,popular FROM vwcontent,tblcontent");
        }
            $response["content"] = array();
        while ($row = mysqli_fetch_array($result)) {
            // temp user array
            $content = array();
            $content["id"] = $row["id"];
            $content["content_name"] = $row["content_name"];
            $content["description"] = $row["description"];
            $content["image"] = $row["image"];
            $content["channel"] = $row["channel"];
            $content["content_type"] = $row["content_type"];
            $content["category"] = $row["category"];
            $content["classification"] = $row["classification"];
            $content["popular"] = $row["popular"];


            // push single product into final response array
            array_push($response["content"], $content);
        }

    $result = mysqli_query($con,"SELECT * FROM tblcontenttype ");

$response["content_type"] = array();
while ($row = mysqli_fetch_array($result)) {
    // temp user array
    $content = array();
    $content["id"] = $row["id"];
    $content["content_type_name"] = $row["content_type_name"];



    // push single product into final response array
    array_push($response["content_type"], $content);
}
$result = mysqli_query($con,"SELECT * FROM tblcategories ");

$response["categories"] = array();
while ($row = mysqli_fetch_array($result)) {
    // temp user array
    $content = array();
    $content["id"] = $row["id"];
    $content["category_name"] = $row["category_name"];



    // push single product into final response array
    array_push($response["categories"], $content);
}
        // success
        $response["success"] = 1;

        // echoing JSON response
        echo json_encode($response);








// required field is missing

?>