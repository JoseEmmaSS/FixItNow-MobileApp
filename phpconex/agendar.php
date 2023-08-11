<?php
// Valores de conexión
$servername = "localhost";
$username = "root";
$password = "";
$database = "fixinow";

// Crear una conexión
$conexion = new mysqli($servername, $username, $password, $database);

// Verificar la conexión
if ($conexion->connect_error) {
    die("Error de conexión: " . $conexion->connect_error);
}

// Obtener los datos enviados desde la aplicación
$userId = $conexion->real_escape_string($_POST['userId']);
$empresa = $conexion->real_escape_string($_POST['empresa']);
$tipoServicio = $conexion->real_escape_string($_POST['tipoServicio']);
$costoServicio = $conexion->real_escape_string($_POST['costoServicio']);
$numeroTelefono = $conexion->real_escape_string($_POST['numeroTelefono']);

// Preparar la consulta SQL para insertar un nuevo pedido
$sql = "INSERT INTO pedidos (empresa, servicio, telefono, costo_servicio, usuarioid) 
        VALUES ('$empresa', '$tipoServicio', '$numeroTelefono', '$costoServicio', '$userId')";

// Inicializar un arreglo para la respuesta
$response = array();

// Ejecutar la consulta y verificar el resultado
if ($conexion->query($sql) === TRUE) {
    $response['success'] = true;
    $response['message'] = "Pedido agendado correctamente";
    $response['data'] = array(
        'empresa' => $empresa,
        'tipoServicio' => $tipoServicio,
        'numeroTelefono' => $numeroTelefono,
        'costoServicio' => $costoServicio,
        'userId' => $userId
    );
} else {
    $response['success'] = false;
    $response['message'] = "Error al agendar el pedido";
    $response['data'] = array(
        'empresa' => $empresa,
        'tipoServicio' => $tipoServicio,
        'numeroTelefono' => $numeroTelefono,
        'costoServicio' => $costoServicio,
        'userId' => $userId
    );
}

// Enviar la respuesta como JSON
header('Content-Type: application/json');
echo json_encode($response);

// Cerrar la conexión
$conexion->close();
?>
