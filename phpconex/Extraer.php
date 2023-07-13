<?php
// Datos de conexión a la base de datos
$servername = "localhost";
$username = "root";
$password = "";
$database = "fixinow";

// Obtén el valor del ID de usuario almacenado en el query parameter
$userId = $_GET['id'];

// Crear conexión
$conn = new mysqli($servername, $username, $password, $database);

// Verificar conexión
if ($conn->connect_error) {
    die("Error de conexión: " . $conn->connect_error);
}

// Escapar caracteres especiales para evitar SQL injection
$userId = $conn->real_escape_string($userId);

// Consulta SQL
$sql = "SELECT * FROM usuarios WHERE id = '$userId'";

// Ejecutar consulta y obtener resultados
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // Convertir resultados a un arreglo asociativo
    $rows = array();
    while ($row = $result->fetch_assoc()) {
        $rows[] = $row;
    }

    // Convertir arreglo a formato JSON y enviar respuesta
    echo json_encode($rows);
} else {
    // No se encontraron resultados
    echo "No se encontraron resultados.";
}

// Cerrar conexión
$conn->close();
?>
