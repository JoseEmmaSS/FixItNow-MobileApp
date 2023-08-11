<?php

// Establecer los datos de conexión a la base de datos
$host = 'localhost';
$usuario = 'root';
$contrasena = '';
$base_de_datos = 'fixinow';

// Conectar a la base de datos
$conexion = mysqli_connect($host, $usuario, $contrasena, $base_de_datos);

// Verificar la conexión
if (!$conexion) {
    die('Error de conexión: ' . mysqli_connect_error());
}

// Obtener el parámetro de búsqueda de la URL (q)
$busqueda = $_GET['q'];

// Escapar el parámetro de búsqueda para evitar inyección SQL
$busqueda = mysqli_real_escape_string($conexion, $busqueda);

// Construir la consulta SQL
$sql = "SELECT * FROM servicios WHERE tipo_servicio LIKE '%$busqueda%'";

// Ejecutar la consulta
$resultado = mysqli_query($conexion, $sql);

// Verificar si hubo resultados
if (mysqli_num_rows($resultado) > 0) {
    // Crear un array para almacenar los resultados
    $resultados_array = array();

    // Recorrer los resultados y agregarlos al array
    while ($fila = mysqli_fetch_assoc($resultado)) {
        $resultados_array[] = $fila;
    }

    // Crear un array de respuesta
    $response = array(
        'success' => true,
        'results' => $resultados_array
    );
} else {
    // No se encontraron resultados
    $response = array(
        'success' => false,
        'message' => 'No se encontraron resultados'
    );
}

// Cerrar la conexión a la base de datos
mysqli_close($conexion);

// Devolver la respuesta en formato JSON
header('Content-Type: application/json');
echo json_encode($response);

?>
