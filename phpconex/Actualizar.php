<?php
error_reporting(0);
	ini_set('display_errors', '1');
// Obtén los parámetros enviados por GET

header('Content-Type: application/json');

$servername = "localhost";
$username = "root";
$password = "";
$database = "fixinow";


// Crear una conexión
$conexion = new mysqli($servername, $username, $password, $database);

$id 		= $_POST['id'];
$user 		= $_POST['username'];
$name 		= $_POST['name'];
$telefono 	= $_POST['telefono'];
$correo 	= $_POST['correo'];
$foto 		= $_POST['foto'];

// Verifica si la conexión fue exitosa
if (!$conexion) {
    die("Error de conexión: " . mysqli_connect_error());
}
// Construye la consulta de actualización
$sentencia = $conexion->prepare("UPDATE usuarios SET name=?, telefono=?,correo=?, fotoPerfil=? WHERE id=?");
$sentencia->bind_param('sssss', $name, $telefono, $correo, $foto, $id);
if ($sentencia->execute()) {
	$response = array(
		'success' => 'true',
		'message' => 'Usuario actualizado correctamente'
	);
} else {
	// Error al insertar el usuario
	$response = array(
		'success' => 'false',
		'message' => 'Error al actualizar el usuario'
	);
}
echo json_encode($response);
?>