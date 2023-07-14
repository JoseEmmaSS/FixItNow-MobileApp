<?php
// Obtener el término de búsqueda del usuario
$termino_busqueda = $_GET['termino_busqueda'];

// Establecer los datos de conexión a la base de datos
$host = 'localhost';
$usuario = 'root';
$contrasena = '';
$base_de_datos = 'fixinow';

// Conectar a la base de datos
$conexion = mysqli_connect($host, $usuario, $contrasena, $base_de_datos);

// Verificar la conexión
if (!$conexion) {
    die("Error de conexión: " . mysqli_connect_error());
}

// Escapar y sanitizar el término de búsqueda para evitar inyección SQL
$termino_busqueda = mysqli_real_escape_string($conexion, $termino_busqueda);

// Ejecutar la consulta SQL para buscar coincidencias parciales
$query = "SELECT * FROM Empresas WHERE nombre LIKE '%" . $termino_busqueda . "%' OR servicio LIKE '%" . $termino_busqueda . "%'";
$resultado = mysqli_query($conexion, $query);

// Verificar si se encontraron resultados
if (mysqli_num_rows($resultado) > 0) {
    // Crear un array para almacenar los resultados
    $resultados = array();

    // Recorrer los resultados y agregarlos al array
    while ($fila = mysqli_fetch_assoc($resultado)) {
        $resultado_actual = array(
            'nombre' => $fila['nombre'],
            'servicio' => $fila['servicio']
        );
        $resultados[] = $resultado_actual;
    }

    // Imprimir los resultados como una cadena JSON
    echo json_encode($resultados);
} else {
    echo "No se encontraron resultados.";
}

// Cerrar la conexión a la base de datos
mysqli_close($conexion);
?>
