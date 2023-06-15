
<?php
$servername = "localhost";
$username = "root";
$password = "";
$database = "fixinow";

// Establecer la conexión a la base de datos
$conn = new mysqli($servername, $username, $password, $database);

// Verificar si hay errores de conexión
if ($conn->connect_error) {
    die("Error de conexión a la base de datos: " . $conn->connect_error);
}

if (isset($_POST['recuperar'])) {
    $usuario = $_POST['usuario'];
    $correo = $_POST['correo'];
    $nueva_contrasena = $_POST['nueva_contrasena'];
    $confirmar_contrasena = $_POST['confirmar_contrasena'];

    // Verificar si el usuario y el correo existen en la tabla "usuarios"
    $query = "SELECT * FROM usuarios WHERE user = '$usuario' AND correo = '$correo'";
    $result = $conn->query($query);

    if ($result->num_rows > 0) {
        // Verificar si la nueva contraseña coincide con la confirmación
        if ($nueva_contrasena === $confirmar_contrasena) {
            // Actualizar la contraseña en la base de datos
            $update_query = "UPDATE usuarios SET psw = '$nueva_contrasena' WHERE user = '$usuario'";
            
            if ($conn->query($update_query) === TRUE) {
                // Redirigir al usuario a una página de éxito
                header("Location: recuperacion_exitosa.php");
                exit();
            } else {
                echo "Error al actualizar la contraseña: " . $conn->error;
            }
        } else {
            echo "Las contraseñas no coinciden.";
        }
    } else {
        echo "El usuario y/o el correo no existen.";
    }

    $conn->close();
}
?>
