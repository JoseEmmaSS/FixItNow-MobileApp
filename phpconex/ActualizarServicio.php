<?php
// Database connection
$servername = "localhost";
$username = "root";
$password = "";
$database = "fixinow";

// Create connection
$conn = new mysqli($servername, $username, $password, $database);

// Verificar si se recibieron los parámetros esperados
if(isset($_POST['servicioId'], $_POST['tipo_servicio'], $_POST['costo_servicio'], $_POST['numero_telefono'])) {
    // Obtener los parámetros de la solicitud POST
    $servicioId = $_POST['servicioId'];
    $tipoServicio = $_POST['tipo_servicio'];
    $costoServicio = $_POST['costo_servicio'];
    $numeroTelefono = $_POST['numero_telefono'];

    // Actualizar el servicio en la base de datos
    $sql = "UPDATE servicios SET tipo_servicio = '$tipoServicio', costo_servicio = '$costoServicio', numero_telefono = '$numeroTelefono' WHERE id = '$servicioId'";
    $result = mysqli_query($conn, $sql);

    // Verificar si la actualización fue exitosa
    if($result) {
        // Enviar una respuesta de éxito al cliente
        $response = array(
            'success' => true,
            'message' => 'El servicio se actualizó correctamente'
        );
    } else {
        // Enviar una respuesta de error al cliente
        $response = array(
            'success' => false,
            'message' => 'Error al actualizar el servicio'
        );
    }
} else {
    // Enviar una respuesta de error al cliente si faltan parámetros
    $response = array(
        'success' => false,
        'message' => 'Faltan parámetros en la solicitud'
    );
}

// Convertir la respuesta a formato JSON y enviarla al cliente
echo json_encode($response);
?>
