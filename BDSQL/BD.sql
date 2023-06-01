CREATE DATABASE ServiciosMantenimiento;

-- Selección de la base de datos
USE ServiciosMantenimiento;

-- Creación de la tabla Usuarios
CREATE TABLE Usuarios (
  ID_Usuario INT AUTO_INCREMENT PRIMARY KEY,
  Nombre VARCHAR(50),
  Apellido VARCHAR(50),
  CorreoElectronico VARCHAR(100),
  Contraseña VARCHAR(50),
  Direccion VARCHAR(100),
  Telefono VARCHAR(20)
);

-- Creación de la tabla Categorías de Servicios
CREATE TABLE CategoriasServicios (
  ID_Categoria INT AUTO_INCREMENT PRIMARY KEY,
  Nombre VARCHAR(50)
);

-- Creación de la tabla Servicios
CREATE TABLE Servicios (
  ID_Servicio INT AUTO_INCREMENT PRIMARY KEY,
  ID_Categoria INT,
  Nombre VARCHAR(50),
  Descripcion VARCHAR(100),
  Precio DECIMAL(10,2),
  FOREIGN KEY (ID_Categoria) REFERENCES CategoriasServicios(ID_Categoria)
);

-- Creación de la tabla Proveedores de Servicios
CREATE TABLE ProveedoresServicios (
  ID_Proveedor INT AUTO_INCREMENT PRIMARY KEY,
  Nombre VARCHAR(50),
  Direccion VARCHAR(100),
  Telefono VARCHAR(20)
);

-- Creación de la tabla Horarios de Servicio
CREATE TABLE HorariosServicio (
  ID_Horario INT AUTO_INCREMENT PRIMARY KEY,
  ID_Proveedor INT,
  DiaSemana VARCHAR(20),
  HoraInicio TIME,
  HoraFin TIME,
  FOREIGN KEY (ID_Proveedor) REFERENCES ProveedoresServicios(ID_Proveedor)
);

-- Creación de la tabla Clientes
CREATE TABLE Clientes (
  ID_Cliente INT AUTO_INCREMENT PRIMARY KEY,
  Nombre VARCHAR(50),
  Apellido VARCHAR(50),
  CorreoElectronico VARCHAR(100),
  Direccion VARCHAR(100),
  Telefono VARCHAR(20)
);

-- Creación de la tabla Citas
CREATE TABLE Citas (
  ID_Cita INT AUTO_INCREMENT PRIMARY KEY,
  ID_Cliente INT,
  ID_Proveedor INT,
  ID_Servicio INT,
  Fecha DATE,
  Hora TIME,
  FOREIGN KEY (ID_Cliente) REFERENCES Clientes(ID_Cliente),
  FOREIGN KEY (ID_Proveedor) REFERENCES ProveedoresServicios(ID_Proveedor),
  FOREIGN KEY (ID_Servicio) REFERENCES Servicios(ID_Servicio)
);

-- Creación de la tabla Calificaciones
CREATE TABLE Calificaciones (
  ID_Calificacion INT AUTO_INCREMENT PRIMARY KEY,
  ID_Cita INT,
  Valoracion INT,
  Comentario VARCHAR(100),
  FOREIGN KEY (ID_Cita) REFERENCES Citas(ID_Cita)
);

-- Creación de la tabla Pagos
CREATE TABLE Pagos (
  ID_Pago INT AUTO_INCREMENT PRIMARY KEY,
  ID_Cita INT,
  MetodoPago VARCHAR(50),
  Monto DECIMAL(10,2),
  Fecha DATE,
  FOREIGN KEY (ID_Cita) REFERENCES Citas(ID_Cita)
);

-- Creación de la tabla Facturas
CREATE TABLE Facturas (
  ID_Factura INT AUTO_INCREMENT PRIMARY KEY,
  ID_Pago INT,
  ID_Cliente INT,
  Fecha DATE,
  Total DECIMAL(10,2),
  FOREIGN KEY (ID_Pago) REFERENCES Pagos(ID_Pago),
  FOREIGN KEY (ID_Cliente) REFERENCES Clientes(ID_Cliente)
);

-- Creación de la tabla Empleados
CREATE TABLE Empleados (
  ID_Empleado INT AUTO_INCREMENT PRIMARY KEY,
  Nombre VARCHAR(50),
  Apellido VARCHAR(50),
  Cargo VARCHAR(50),
  CorreoElectronico VARCHAR(100),
  Contraseña VARCHAR(50)
);