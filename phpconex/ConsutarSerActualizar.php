<?php
$id = $_GET['servicioId'];

// Database connection
$servername = "localhost";
$username = "root";
$password = "";
$database = "fixinow";

// Create connection
$conn = new mysqli($servername, $username, $password, $database);

// Check connection
if ($conn->connect_error) {
    die("Connection error: " . $conn->connect_error);
}

// Prepare and execute the query
$stmt = $conn->prepare("SELECT * FROM servicios WHERE id=?");
$stmt->bind_param('s', $id);
$stmt->execute();

// Get the query result
$resultado = $stmt->get_result();

// Check if any records were found
if ($resultado->num_rows > 0) {
    $rows = array();

    // Fetch all rows and add them to the response array
    while ($fila = $resultado->fetch_assoc()) {
        $rows[] = $fila;
    }

    $response = array(
        'success' => true,
        'data' => $rows
    );
    echo json_encode($response, JSON_UNESCAPED_UNICODE);
} else {
    $response = array(
        'success' => false,
        'message' => 'No se encontraron registros'
    );
    echo json_encode($response, JSON_UNESCAPED_UNICODE);
}

// Close the statement and the connection
$stmt->close();
$conn->close();
?>
