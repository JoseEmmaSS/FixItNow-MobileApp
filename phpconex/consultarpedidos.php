<?php
$servername = "localhost";
$username = "root";
$password = "";
$database = "fixinow";

// Crear una conexión
$conexion = new mysqli($servername, $username, $password, $database);

// Verificar la conexión
if ($conexion->connect_error) {
    die("Conexión fallida: " . $conexion->connect_error);
}

// Obtener el valor del parámetro usuarioid de la URL
if (isset($_GET['usuarioid'])) {
    $userId = $_GET['usuarioid'];
    
    // Consulta SQL para obtener los pedidos del usuario
    $consulta = "SELECT * FROM pedidos WHERE usuarioid = '$userId'";
    $resultado = $conexion->query($consulta);

    if ($resultado->num_rows > 0) {
        // Crear un array para almacenar los pedidos
        $pedidos = array();

        // Recorrer los resultados y agregarlos al array
        while ($row = $resultado->fetch_assoc()) {
            $pedidos[] = $row;
        }

        // Devolver el array como respuesta en formato JSON
        echo json_encode($pedidos);
    } else {
        echo "No se encontraron pedidos para el usuario.";
    }
} else {
    echo "Parámetro usuarioid no proporcionado.";
}

// Cerrar la conexión
$conexion->close();
?>
