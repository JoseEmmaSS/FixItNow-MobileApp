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

// Obtener los datos del usuario enviados desde la aplicación
$googleId = $_POST["google_id"];
$nombre = $_POST["nombre"];
$correo = $_POST["correo"];

// Preparar la consulta SQL para insertar los datos en la tabla de usuarios
$sql = "INSERT INTO usuariosG (google_id, nombre, correo) VALUES ('$googleId', '$nombre', '$correo')";

// Ejecutar la consulta SQL
if ($conexion->query($sql) === TRUE) {
    echo "Registro exitoso";
} else {
    echo "Error al registrar el usuario: " . $conexion->error;
}

// Cerrar la conexión a la base de datos
$conexion->close();

?>
