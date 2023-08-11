<?php
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

// Verificar si se ha recibido el parámetro 'id' en la solicitud POST
if (isset($_POST['id'])) {
    $pedidoId = $_POST['id'];

    // Consulta SQL para eliminar el pedido por ID
    $sql = "DELETE FROM pedidos WHERE id = $pedidoId";

    if ($conn->query($sql) === TRUE) {
        echo "Pedido eliminado con éxito";
    } else {
        echo "Error al eliminar el pedido: " . $conn->error;
    }
} else {
    echo "No se proporcionó el parámetro 'id'";
}

// Cerrar la conexión
$conn->close();
?>
