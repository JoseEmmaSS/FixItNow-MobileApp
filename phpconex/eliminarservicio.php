<?php
// Obtener el parámetro servicioId
$servicioId = $_GET['id'];

// Verificar si se proporcionó un servicioId válido
if (isset($servicioId)) {
    // Conexión a la base de datos
// Database connection
$servername = "localhost";
$username = "root";
$password = "";
$database = "fixinow";

// Create connection
$conn = new mysqli($servername, $username, $password, $database);
   // Verificar la conexión
    if ($conn->connect_error) {
        die("Error de conexión: " . $conn->connect_error);
    }

    // Construir la consulta SQL para eliminar el servicio con el servicioId proporcionado
    $sql = "DELETE FROM servicios WHERE id = '$servicioId'";

    // Ejecutar la consulta
    if ($conn->query($sql) === TRUE) {
        // El servicio se eliminó correctamente
        $response = array();
        $response["success"] = true;
        $response["message"] = "Servicio eliminado correctamente";
    } else {
        // Ocurrió un error al eliminar el servicio
        $response = array();
        $response["success"] = false;
        $response["message"] = "Error al eliminar el servicio: " . $conn->error;
    }

    // Cerrar la conexión a la base de datos
    $conn->close();

    // Enviar la respuesta como JSON
    header('Content-Type: application/json');
    echo json_encode($response);
} else {
    // Si no se proporcionó un servicioId válido, enviar una respuesta de error
    $response = array();
    $response["success"] = false;
    $response["message"] = "No se proporcionó un servicioId válido";

    // Enviar la respuesta como JSON
    header('Content-Type: application/json');
    echo json_encode($response);
}
?>
