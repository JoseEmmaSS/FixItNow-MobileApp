<?php
$servername = "localhost";
$username = "root";
$password = "";
$database = "fixinow";
// Crear una conexión
$conexion = new mysqli($servername, $username, $password, $database);

// Verificar la conexión
if ($conexion->connect_error) {
    die("Error al conectar con la base de datos: " . $conexion->connect_error);
}

$correo = $_POST['correo'];
$psw = $_POST['psw'];

$sentencia = $conexion->prepare("SELECT * FROM usuarios WHERE correo=? AND psw=?");
$sentencia->bind_param('ss', $correo, $psw);
$sentencia->execute();

$resultado = $sentencia->get_result();
if ($fila = $resultado->fetch_assoc()) {
    $response = array(
        'success' => true,
        'message' => 'Usuario autenticado correctamente',
        'name'=> $fila['name'],
        'id' => $fila['id']// Incluye los datos de la fila en la respuesta
    );
    echo json_encode($response, JSON_UNESCAPED_UNICODE);
} else {
    $response = array(
        'success' => false,
        'message' => 'Correo o contraseña incorrectos'
    );
    echo json_encode($response, JSON_UNESCAPED_UNICODE);
}

$sentencia->close();
$conexion->close();
?>
