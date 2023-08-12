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
    $userId = $_POST['userId'];
    $imagen = $_POST['foto'];
    $nombreFoto = $_POST['nombrefoto'];
    $nombre = $_POST['name'];
    $usuario = $_POST['user'];
    $telefono = $_POST['telefono'];
    $correo = $_POST['correo'];

    // RUTA DONDE SE GUARDARAN LAS IMAGENES
    $path = "Fotos/" . $nombreFoto . ".png";
    $actualpath = "http://10.0.11.118/phpconex/" . $path;
    
    // Actualizar la fila con el ID correspondiente
    $sql = "UPDATE usuarios SET name = '$nombre', user = '$usuario', correo = '$correo', telefono = '$telefono', foto = '$actualpath' WHERE id = '$userId'";

    if (mysqli_query($conexion, $sql)) {
        file_put_contents($path, base64_decode($imagen));

        echo "SE ACTUALIZÓ EXITOSAMENTE";
        mysqli_close($conexion);
    } else {
        echo "Error: " . mysqli_error($conexion);
    }
}
?>
