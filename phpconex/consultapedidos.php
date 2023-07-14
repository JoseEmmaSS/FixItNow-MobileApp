    <?php
    // Obtener el valor del parámetro 'usuarioid' enviado desde la aplicación
    $usuarioid = $_GET['userId'];

    // Realizar la conexión a la base de datos (ajusta los valores de host, usuario, contraseña y nombre de la base de datos según tu configuración)
    $host = 'localhost';
    $usuario = 'root';
    $contrasena = '';
    $base_de_datos = 'fixinow';

    $conexion = mysqli_connect($host, $usuario, $contrasena, $base_de_datos);
    if (!$conexion) {
        die('Error al conectar a la base de datos: ' . mysqli_connect_error());
    }

    // Consulta SQL para seleccionar los registros de la tabla "pedidos" donde el usuarioid sea igual al valor proporcionado
    $consulta = "SELECT * FROM pedidos WHERE usuarioid = '$usuarioid'";
    $resultado = mysqli_query($conexion, $consulta);

    // Crear un array para almacenar los resultados de la consulta
    $pedidos = array();

    // Recorrer los resultados de la consulta y agregarlos al array
    while ($fila = mysqli_fetch_assoc($resultado)) {
        $pedidos[] = $fila;
    }

    // Devolver los resultados como JSON
    header('Content-Type: application/json');
    echo json_encode($pedidos);

    // Cerrar la conexión a la base de datos
    mysqli_close($conexion);
    ?>
