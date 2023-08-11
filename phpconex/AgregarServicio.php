<?php
// Obtener los datos del formulario
$tipoServicio = $_POST['tipo_servicio'];
$costoServicio = $_POST['costo_servicio'];
$numeroTelefono = $_POST['numero_telefono'];
$idusuairo = $_POST['idusuario'];
$empresa = $_POST['empresa'];
// Conexión a la base de datos
$servername = "localhost";
$username = "root";
$password = "";
$database = "fixinow";

// Crear conexión
$conn = new mysqli($servername, $username, $password, $database);

// Verificar la conexión
if ($conn->connect_error) {
    die("Error de conexión: " . $conn->connect_error);
}

// Preparar la consulta SQL para la inserción
$sql = "INSERT INTO servicios (empresa, tipo_servicio, costo_servicio, numero_telefono, idusuario)
        VALUES ('$empresa', '$tipoServicio', '$costoServicio', '$numeroTelefono', '$idusuairo')";

// Ejecutar la consulta
if ($conn->query($sql) === TRUE) {
    echo "El servicio se ha guardado exitosamente";
} else {
    echo "Error al guardar el servicio: " . $conn->error;
}

// Cerrar la conexión
$conn->close();
?>
