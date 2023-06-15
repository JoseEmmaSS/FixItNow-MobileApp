<!DOCTYPE html>
<html>
<head>
    <title>Recuperar Contraseña</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
        }

        h2 {
            text-align: center;
            color: #333;
        }

        form {
            width: 90%;
            max-width: 400px;
            margin: 0 auto;
            background-color: #fff;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }

        label {
            display: block;
            margin-bottom: 10px;
            color: #555;
        }

        input[type="text"],
        input[type="email"],
        input[type="password"] {
            width: 100%;
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }

        input[type="submit"] {
            width: 100%;
            padding: 10px;
            background-color: #4CAF50;
            color: #fff;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }

        input[type="submit"]:hover {
            background-color: #45a049;
        }

        /* Estilos para dispositivos móviles */
        @media screen and (max-width: 480px) {
            form {
                padding: 10px;
            }

            input[type="submit"] {
                font-size: 14px;
            }
        }
    </style>
    <script>
        function validarContraseñas() {
            var nuevaContraseña = document.getElementById('nueva_contrasena').value;
            var confirmarContraseña = document.getElementById('confirmar_contrasena').value;
            
            if (nuevaContraseña !== confirmarContraseña) {
                alert("Las contraseñas no coinciden.");
                return false;
            }
        }
    </script>
</head>
<body>
    <h2>Recuperar Contraseña</h2>
    <form method="post" action="Recupera.php" onsubmit="return validarContraseñas()">
        <label for="usuario">Usuario:</label>
        <input type="text" name="usuario" id="usuario" required>

        <label for="correo">Correo Electrónico:</label>
        <input type="email" name="correo" id="correo" required>

        <label for="nueva_contrasena">Nueva Contraseña:</label>
        <input type="password" name="nueva_contrasena" id="nueva_contrasena" required>

        <label for="confirmar_contrasena">Confirmar Contraseña:</label>
        <input type="password" name="confirmar_contrasena" id="confirmar_contrasena" required>

        <input type="submit" name="recuperar" value="Recuperar Contraseña">
    </form>
</body>
</html>
