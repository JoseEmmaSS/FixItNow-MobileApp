<?php
use PHPMailer\PHPMailer\PHPMailer;
use PHPMailer\PHPMailer\SMTP;
use PHPMailer\PHPMailer\Exception;

// Incluir la biblioteca de PHPMailer
require 'librerias/vendor/phpmailer/phpmailer/src/Exception.php';
require 'librerias/vendor/phpmailer/phpmailer/src/PHPMailer.php';
require 'librerias/vendor/phpmailer/phpmailer/src/SMTP.php';

// Configurar el servidor de correo saliente (SMTP) de Gmail
$mail = new PHPMailer(true);
$mail->SMTPDebug = 0;
$mail->isSMTP();
$mail->Host = 'smtp.gmail.com';
$mail->SMTPAuth = true;
$mail->Username = 'ivan130granados@gmail.com';
$mail->Password = 'eykojbhozohjkvlp';
$mail->SMTPSecure = 'tls';
$mail->Port = 587;
$mail->CharSet = 'UTF-8';

// Conexión a la base de datos
$servername = "localhost";
$username = "root";
$password = "";
$database = "fixinow";

$conn = new mysqli($servername, $username, $password, $database);
if ($conn->connect_error) {
    die("Conexión fallida: " . $conn->connect_error);
}

// Obtener los datos enviados desde la aplicación
$correo = $_POST['correo'];

// Consulta para verificar si el correo existe en la tabla
$sql = "SELECT * FROM usuarios WHERE correo = '$correo'";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // El correo existe en la tabla
    $response = array(
        'success' => true,
        'message' => 'Se ha enviado el código de recuperación de contraseña a tu correo electrónico.'
    );
    try {
        // Configurar el remitente y el destinatario
        $mail->setFrom('uriel130granados@gmail.com', 'Remitente');
        $mail->addAddress($correo, 'Destinatario');
        $url = "http://192.168.137.1/phpconex/Recuperacion.php";
        // Configurar el asunto y el cuerpo del correo
        $mail->Subject = 'Recuperación de contraseña';
        $mail->Body = 'Por favor ingrese aquí para realizar la recuperación de su contraseña: ' .$url;

        // Enviar el correo
        if (!$mail->send()) {
            throw new Exception('Hubo un error al enviar el código de recuperación de contraseña.');
        }
    } catch (Exception $e) {
        $response['success'] = false;
        $response['message'] = $e->getMessage();
    }

} else {
    // El correo no existe en la tabla
    $response = array(
        'success' => false,
        'message' => 'El correo no existe en la tabla.'
    );
}

$conn->close();
echo json_encode($response);
?>
