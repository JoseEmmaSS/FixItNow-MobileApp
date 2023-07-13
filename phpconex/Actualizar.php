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
} else {
    echo "Conexión exitosa";
}

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $imagen = $_POST['foto'];
    $nombreFoto = $_POST['nombrefoto'];
    $nombre = $_POST['nombre'];
    $usuario = $_POST['usuario'];
    $telefono = $_POST['telefono'];
    $correo = $_POST['correo'];

    // RUTA DONDE SE GUARDARAN LAS IMAGENES
    $path = "Fotos/" . $nombreFoto . ".png";
    $actualpath = "http://localhost/phpconex/" . $path;
    $sql = "INSERT INTO usuarios (name, user, correo, telefono, foto) VALUES ('$nombre', '$usuario', '$correo', '$telefono', '$actualpath')";

    if (mysqli_query($conexion, $sql)) {
        file_put_contents($path, base64_decode($imagen));

        echo "SE SUBIÓ EXITOSAMENTE";
        mysqli_close($conexion);
    } else {
        echo "Error: " . mysqli_error($conexion);
    }
}
?>
