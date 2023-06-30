<?php
// Obtén los parámetros enviados por GET
$username = $_GET['username'];
$name = $_GET['name'];
$telefono = $_GET['telefono'];
$correo = $_GET['correo'];
$foto = $_GET['foto'];

// Realiza la lógica de actualización en la base de datos
// Aquí deberías utilizar las funciones y consultas adecuadas para actualizar los datos en tu base de datos

// Ejemplo de actualización utilizando MySQLi
// Establece los datos de conexión a tu base de datos
$servername = "localhost";
$username = "root";
$password = "";
$database = "fixinow";


// Crea una conexión a la base de datos
$conn = mysqli_connect($servername, $username, $password, $database);

// Verifica si la conexión fue exitosa
if (!$conn) {
    die("Error de conexión: " . mysqli_connect_error());
}

// Construye la consulta de actualización
$sql = "UPDATE usuarios SET name='$name', telefono='$telefono', correo='$correo', rutaFoto='$foto' WHERE user='$username'";

// Ejecuta la consulta
if (mysqli_query($conn, $sql)) {
    echo "Actualización exitosa";
} else {
    echo "Error en la actualización: " . mysqli_error($conn);
}

// Cierra la conexión a la base de datos
mysqli_close($conn);
?>
