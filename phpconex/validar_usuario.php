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
        'user' => $fila
    );
    echo json_encode($response, JSON_UNESCAPED_UNICODE);
} else {
   // Verificar qué dato es incorrecto
   $sentencia = $conexion->prepare("SELECT * FROM usuarios WHERE correo=?");
   $sentencia->bind_param('s', $correo);
   $sentencia->execute();
   $resultado = $sentencia->get_result();
   if ($fila = $resultado->fetch_assoc()) {
       echo "contraseña incorrecta";
   } else {
       echo "correo incorrecto";
   }
}

$sentencia->close();
$conexion->close();
?>
