<?php
header('Content-Type: application/json');

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

$name = $_POST['name'];
$user = $_POST['user'];
$psw = $_POST['psw'];
$correo = $_POST['correo'];
$telefono = $_POST['telefono'];

// Verificar si el campo 'name' está vacío
if (empty($name)) {
    $response = array(
        'success' => 'false',
        'message' => 'El campo "name" es requerido'
    );
} else {
    // Verificar si el correo ya existe en la base de datos
    $sentencia = $conexion->prepare("SELECT * FROM usuarios WHERE correo=?");
    $sentencia->bind_param('s', $correo);
    $sentencia->execute();
    $resultado = $sentencia->get_result();

    if ($resultado->num_rows > 0) {
        // El correo ya está registrado
        $response = array(
            'success' => 'false',
            'message' => 'El correo ya está registrado'
        );
    } else {
        // Insertar el nuevo usuario
        $sentencia = $conexion->prepare("INSERT INTO usuarios (name, user, psw, correo, telefono) VALUES (?, ?, ?, ?, ?)");
        $sentencia->bind_param('sssss', $name, $user, $psw, $correo, $telefono);

        if ($sentencia->execute()) {
            // Registro exitoso
            $response = array(
                'success' => 'true',
                'message' => 'Usuario registrado correctamente'
            );
        } else {
            // Error al insertar el usuario
            $response = array(
                'success' => 'false',
                'message' => 'Error al registrar el usuario'
            );
        }
    }
}

$sentencia->close();
$conexion->close();

echo json_encode($response);
?>
