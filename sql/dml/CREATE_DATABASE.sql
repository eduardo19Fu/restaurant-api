-- Creación de la tabla para los clientes
CREATE TABLE clientes (
    id_cliente INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    telefono VARCHAR(20),
    email VARCHAR(100)
);

-- Creación de la tabla para los empleados
CREATE TABLE empleados (
    id_empleado INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    cargo VARCHAR(50) NOT NULL,
    salario DECIMAL(10, 2),
    fecha_contratacion DATE,
    telefono VARCHAR(20),
    email VARCHAR(100),
    estado ENUM('ACTIVO', 'INACTIVO', 'SUSPENDIDO', 'BAJA')
);

-- Creación de la tabla para los platos del menú
CREATE TABLE platos (
    id_plato INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(10, 2) NOT NULL
);

-- Creación de la tabla para las mesas
CREATE TABLE mesas (
    id_mesa INT AUTO_INCREMENT PRIMARY KEY,
    capacidad INT NOT NULL,
    ubicacion VARCHAR(100),
    estado ENUM('DISPONIBLE', 'OCUPADA', 'RESERVADA') NOT NULL
);

-- Creación de la tabla para las reservas
CREATE TABLE reservas (
    id_reserva INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT NOT NULL,
    id_mesa INT NOT NULL,
    fecha_reserva DATE NOT NULL,
    hora_reserva TIME NOT NULL,
    personas INT NOT NULL,
    estado ENUM('PENDIENTE', 'CONFIRMADA', 'CANCELADA', 'COMPLETADA') NOT NULL,
    CONSTRAINT fk_reservas_id_cliente FOREIGN KEY (id_cliente) REFERENCES clientes(id_cliente),
    CONSTRAINT fk_reservas_id_mesa FOREIGN KEY (id_mesa) REFERENCES mesas(id_mesa)
);

-- Creación de la tabla para los pedidos
CREATE TABLE ordenes (
    id_orden INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT,
    id_mesa INT,
    fecha_orden DATETIME NOT NULL,
    estado ENUM('PENDIENTE', 'EN_PROCESO', 'COMPLETADO') NOT NULL,
    CONSTRAINT fk_pedidos_id_cliente FOREIGN KEY (id_cliente) REFERENCES clientes(id_cliente),
    CONSTRAINT fk_pedidos_id_mesa FOREIGN KEY (id_mesa) REFERENCES mesas(id_mesa)
);

-- Creación de la tabla para los detalles de los pedidos
CREATE TABLE detalles_ordenes (
    id_detalle INT AUTO_INCREMENT PRIMARY KEY,
    id_orden INT NOT NULL,
    id_plato INT NOT NULL,
    cantidad INT NOT NULL,
    CONSTRAINT fk_detalles_pedidos_id_orden FOREIGN KEY (id_orden) REFERENCES pedidos(id_orden),
    CONSTRAINT fk_detalles_pedidos_id_plato FOREIGN KEY (id_plato) REFERENCES menu(id_plato)
);

-- Creación de la tabla para las facturas
CREATE TABLE facturas (
    id_factura INT AUTO_INCREMENT PRIMARY KEY,
    id_orden INT NOT NULL,
    fecha_factura DATE NOT NULL,
    total DECIMAL(10, 2) NOT NULL,
    estado ENUM('PENDIENTE', 'PAGADA') NOT NULL,
    CONSTRAINT fk_facturas_id_orden FOREIGN KEY (id_orden) REFERENCES ordenes(id_orden)
);

-- Creación de la tabla para los pagos
CREATE TABLE pagos (
    id_pago INT AUTO_INCREMENT PRIMARY KEY,
    id_factura INT NOT NULL,
    fecha_pago DATE NOT NULL,
    monto_pagado DECIMAL(10, 2) NOT NULL,
    metodo_pago ENUM('TARJETA_CREDITO', 'TARJETA_DEBITO', 'EFECTIVO', 'CUPON') NOT NULL,
    CONSTRAINT fk_pagos_id_factura FOREIGN KEY (id_factura) REFERENCES facturas(id_factura)
);

-- Creación de la tabla para unidades de medida (llave primaria será el identificador de cada unidad)
CREATE TABLE unidades_medida (
	id_unidad_medida VARCHAR(10) NOT NULL PRIMARY KEY,
    unidad VARCHAR(25) NOT NULL,
    fecha_registro DATETIME
);

-- Creación de la tabla para los ingredientes
CREATE TABLE ingredientes (
    id_ingrediente INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    cantidad_stock DECIMAL(10, 2) NOT NULL,
    id_unidad_medida VARCHAR(10) NOT NULL,
    CONSTRAINT fk_ingredientes_id_unidad_medida FOREIGN KEY (id_unidad_medida) REFERENCES unidades_medida(id_unidad_medida)
);

-- Creación de la tabla para la relación entre platos e ingredientes
CREATE TABLE platos_ingredientes (
    id_plato INT NOT NULL,
    id_ingrediente INT NOT NULL,
    cantidad DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (id_plato, id_ingrediente),
    CONSTRAINT fk_platos_ingredientes_id_plato FOREIGN KEY (id_plato) REFERENCES menu(id_plato),
    CONSTRAINT fk_platos_ingredientes_id_ingrediente FOREIGN KEY (id_ingrediente) REFERENCES ingredientes(id_ingrediente)
);

-- Creación de la tabla para llevar el registro de inventario
CREATE TABLE inventario (
    id_ingrediente INT NOT NULL,
    fecha DATE NOT NULL,
    cantidad DECIMAL(10, 2) NOT NULL,
    tipo_operacion ENUM('ENTRADA', 'SALIDA', 'COMPRA') NOT NULL,
    id_origen INT, -- Puede ser el id_pedido, id_factura, etc.
    tipo_origen ENUM('PEDIDO', 'FACTURA') NOT NULL,
    PRIMARY KEY (id_ingrediente, fecha, tipo_operacion, id_origen, tipo_origen),
    CONSTRAINT fk_inventario_id_ingrediente FOREIGN KEY (id_ingrediente) REFERENCES ingredientes(id_ingrediente)
);
