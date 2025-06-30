-- Creación de la tabla para los clientes
DROP TABLE IF EXISTS clientes;
CREATE TABLE clientes (
    id_cliente BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    nit VARCHAR(20) NOT NULL,
    direccion VARCHAR(150) NOT NULL,
    fecha_creacion TIMESTAMP
);

-- Creación de la tabla para los cargos
DROP TABLE IF EXISTS cargos;
CREATE TABLE cargos (
	id_cargo INT AUTO_INCREMENT PRIMARY KEY,
    cargo VARCHAR(100) NOT NULL,
    descripcion VARCHAR(100)
);

-- Creación de la tabla para los empleados
DROP TABLE IF EXISTS empleados;
CREATE TABLE empleados (
    id_empleado INT AUTO_INCREMENT PRIMARY KEY,
    primer_nombre VARCHAR(100) NOT NULL,
    segundo_nombre VARCHAR(100),
    apellido VARCHAR(100) NOT NULL,
    id_cargo INT NOT NULL,
    salario DECIMAL(10, 2),
    fecha_contratacion DATE,
    telefono VARCHAR(20),
    email VARCHAR(100),
    estado ENUM('ACTIVO', 'INACTIVO', 'SUSPENDIDO', 'BAJA'),
    CONSTRAINT fk_empleados_id_cargo FOREIGN KEY (id_cargo) REFERENCES cargos(id_cargo)
);

-- Creación de la tabla para los platos del menú
DROP TABLE IF EXISTS platos;
CREATE TABLE platos (
    id_plato INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(10, 2) NOT NULL,
    imagen VARCHAR(100),
    estado ENUM('DISPONIBLE','NO_DISPONIBLE','DESCONTINUADO','ELIMINADO')
);

-- Creación de la tabla para las mesas
DROP TABLE IF EXISTS mesas;
CREATE TABLE mesas (
    id_mesa VARCHAR(100) PRIMARY KEY,
    capacidad INT NOT NULL,
    ubicacion VARCHAR(100),
    estado ENUM('DISPONIBLE', 'OCUPADA', 'RESERVADA') NOT NULL
);

-- Creación de la tabla para las reservas
DROP TABLE IF EXISTS reservas;
CREATE TABLE reservas (
    id_reserva BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_cliente BIGINT NOT NULL,
    id_mesa VARCHAR(100) NOT NULL,
    fecha_reserva DATE NOT NULL,
    hora_reserva TIME NOT NULL,
    personas INT NOT NULL,
    estado ENUM('PENDIENTE', 'CONFIRMADA', 'CANCELADA', 'COMPLETADA') NOT NULL,
    CONSTRAINT fk_reservas_id_cliente FOREIGN KEY (id_cliente) REFERENCES clientes(id_cliente) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_reservas_id_mesa FOREIGN KEY (id_mesa) REFERENCES mesas(id_mesa) ON UPDATE CASCADE ON DELETE SET NULL
);

-- Creación de la tabla para los pedidos
DROP TABLE IF EXISTS ordenes;
CREATE TABLE ordenes (
    id_orden INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente BIGINT,
    id_mesa VARCHAR(100),
    fecha_orden DATETIME NOT NULL,
    estado ENUM('PENDIENTE', 'EN_PROCESO', 'COMPLETADO') NOT NULL,
    CONSTRAINT fk_pedidos_id_cliente FOREIGN KEY (id_cliente) REFERENCES clientes(id_cliente) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_pedidos_id_mesa FOREIGN KEY (id_mesa) REFERENCES mesas(id_mesa) ON UPDATE CASCADE ON DELETE CASCADE
);

-- Creación de la tabla para los detalles de los pedidos
DROP TABLE IF EXISTS detalles_ordenes;
CREATE TABLE detalles_ordenes (
    id_detalle INT AUTO_INCREMENT PRIMARY KEY,
    id_orden INT NOT NULL,
    id_plato INT NOT NULL,
    cantidad INT NOT NULL,
    CONSTRAINT fk_detalles_pedidos_id_orden FOREIGN KEY (id_orden) REFERENCES ordenes(id_orden) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_detalles_pedidos_id_plato FOREIGN KEY (id_plato) REFERENCES platos(id_plato) ON UPDATE CASCADE ON DELETE CASCADE
);

-- Creación de la tabla para las facturas
DROP TABLE IF EXISTS facturas;
CREATE TABLE facturas (
    id_factura INT AUTO_INCREMENT PRIMARY KEY,
    id_orden INT NOT NULL,
    fecha_factura DATE NOT NULL,
    total DECIMAL(10, 2) NOT NULL,
    estado ENUM('PENDIENTE', 'PAGADA') NOT NULL,
    CONSTRAINT fk_facturas_id_orden FOREIGN KEY (id_orden) REFERENCES ordenes(id_orden) ON UPDATE CASCADE ON DELETE RESTRICT
);

-- Creación de la tabla para los pagos
DROP TABLE IF EXISTS pagos;
CREATE TABLE pagos (
    id_pago INT AUTO_INCREMENT PRIMARY KEY,
    id_factura INT NOT NULL,
    fecha_pago DATE NOT NULL,
    monto_pagado DECIMAL(10, 2) NOT NULL,
    metodo_pago ENUM('TARJETA_CREDITO', 'TARJETA_DEBITO', 'EFECTIVO', 'CUPON') NOT NULL,
    CONSTRAINT fk_pagos_id_factura FOREIGN KEY (id_factura) REFERENCES facturas(id_factura)
);

-- Creación de la tabla para unidades de medida (llave primaria será el identificador de cada unidad)
DROP TABLE IF EXISTS unidades_medida;
CREATE TABLE unidades_medida (
	id_unidad_medida VARCHAR(10) NOT NULL PRIMARY KEY,
    unidad_medida VARCHAR(25) NOT NULL,
    simbolo CHAR NOT NULL,
    fecha_registro DATETIME
);

-- Creación de la tabla para los ingredientes
DROP TABLE IF EXISTS ingredientes;
CREATE TABLE ingredientes (
    id_ingrediente INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    stock DECIMAL(10, 2) NOT NULL,
    id_unidad_medida VARCHAR(10) NOT NULL,
    CONSTRAINT fk_ingredientes_id_unidad_medida FOREIGN KEY (id_unidad_medida) REFERENCES unidades_medida(id_unidad_medida)
);

-- Creación de la tabla para la relación entre platos e ingredientes
DROP TABLE IF EXISTS platos_ingredientes;
CREATE TABLE platos_ingredientes (
    id_plato INT NOT NULL,
    id_ingrediente INT NOT NULL,
    cantidad DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (id_plato, id_ingrediente),
    CONSTRAINT fk_platos_ingredientes_id_plato FOREIGN KEY (id_plato) REFERENCES platos(id_plato),
    CONSTRAINT fk_platos_ingredientes_id_ingrediente FOREIGN KEY (id_ingrediente) REFERENCES ingredientes(id_ingrediente)
);

-- Creación de la tabla para llevar el registro de inventario
DROP TABLE IF EXISTS inventario;
CREATE TABLE inventario (
    id_ingrediente BIGINT NOT NULL,
    fecha DATE NOT NULL,
    cantidad DECIMAL(10, 2) NOT NULL,
    tipo_operacion ENUM('ENTRADA', 'SALIDA', 'COMPRA') NOT NULL,
    id_origen INT, -- Puede ser el id_pedido, id_factura, etc.
    tipo_origen ENUM('PEDIDO', 'FACTURA') NOT NULL,
    PRIMARY KEY (id_ingrediente, fecha, tipo_operacion, id_origen, tipo_origen),
    CONSTRAINT fk_inventario_id_ingrediente FOREIGN KEY (id_ingrediente) REFERENCES ingredientes(id_ingrediente)
);

-- Creación de la tabla para la relacion entre los platos y los ingredientes
DROP TABLE IF EXISTS plato_ingredientes;
CREATE TABLE plato_ingredientes(
    id_plato INT,
    id_ingrediente INT,
    cantidad DECIMAL(10,2),
    id_unidad_medida VARCHAR(10) NOT NULL,
    PRIMARY KEY (id_plato, id_ingrediente),
    CONSTRAINT fk_plato_ingredientes_id_plato FOREIGN KEY (id_plato) REFERENCES platos(id_plato),
    CONSTRAINT fk_plato_ingredientes_id_ingrediente FOREIGN KEY (id_ingrediente) REFERENCES ingredientes(id_ingrediente),
    CONSTRAINT fk_plato_ingredientes_id_unidad_medida FOREIGN KEY (id_unidad_medida) REFERENCES unidades_medida(id_unidad_medida)
);

-- Creación de la tabla para las categorías (génerica para platos y bebidas)
DROP TABLE IF EXISTS categorias;
CREATE TABLE categorias (
    id_categoria INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255)
);

-- Creación de tabla para bebidas
DROP TABLE IF EXISTS bebidas;
CREATE TABLE bebidas (
     id_bebida INT AUTO_INCREMENT PRIMARY KEY,
     nombre VARCHAR(100) NOT NULL,
     descripcion TEXT,
     precio DECIMAL(10,2) NOT NULL,
     imagen VARCHAR(100),
     estado ENUM('DISPONIBLE','NO_DISPONIBLE','DESCONTINUADO','ELIMINADO') NOT NULL DEFAULT 'DISPONIBLE'
);

-- Creacion de tabla para relacion de platos-categorias
DROP TABLE IF EXISTS platos_categorias;
CREATE TABLE platos_categorias (
    id_plato     INT NOT NULL,
    id_categoria INT NOT NULL,
    PRIMARY KEY (id_plato, id_categoria),
    CONSTRAINT fk_platos_categorias_id_plato FOREIGN KEY (id_plato) REFERENCES platos(id_plato) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_platos_categorias_id_categoria FOREIGN KEY (id_categoria) REFERENCES categorias(id_categoria) ON UPDATE CASCADE ON DELETE RESTRICT
);

-- Creación de tablas para relacion de bebidas-categorias
DROP TABLE IF EXISTS bebidas_categorias;
CREATE TABLE bebidas_categorias (
    id_bebida    INT NOT NULL,
    id_categoria INT NOT NULL,
    PRIMARY KEY (id_bebida, id_categoria),
    CONSTRAINT fk_bebidas_categorias_id_bebida FOREIGN KEY (id_bebida) REFERENCES bebidas(id_bebida) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_bebidas_categorias_id_categoria FOREIGN KEY (id_categoria) REFERENCES categorias(id_categoria) ON UPDATE CASCADE ON DELETE RESTRICT
);

-- Tabla de ventanas de disponibilidad (turnos: desayuno, almuerzo, cena, etc.)
DROP TABLE IF EXISTS horarios;
CREATE TABLE horarios (
    id_horario   INT AUTO_INCREMENT PRIMARY KEY,
    descripcion  VARCHAR(50)   NOT NULL,  -- p.ej. 'DESAYUNO', 'ALMUERZO', 'CENA'
    hora_inicio  TIME          NOT NULL,
    hora_fin     TIME          NOT NULL
);

-- Relación platos ↔ horarios (qué platos están disponibles en qué turnos)
DROP TABLE IF EXISTS platos_horarios;
CREATE TABLE platos_horarios (
    id_plato   INT NOT NULL,
    id_horario INT NOT NULL,
    PRIMARY KEY (id_plato, id_horario),
    CONSTRAINT fk_platos_horarios_id_plato FOREIGN KEY (id_plato)   REFERENCES platos(id_plato) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_platos_horarios_id_horario FOREIGN KEY (id_horario) REFERENCES horarios(id_horario) ON UPDATE CASCADE ON DELETE RESTRICT
);

-- Relación bebidas ↔ horarios (qué bebidas están disponibles en qué turnos)
DROP TABLE IF EXISTS bebidas_horarios;
CREATE TABLE bebidas_horarios (
    id_bebida   INT NOT NULL,
    id_horario  INT NOT NULL,
    PRIMARY KEY (id_bebida, id_horario),
    CONSTRAINT fk_bebidas_horarios_id_bebida FOREIGN KEY (id_bebida)  REFERENCES bebidas(id_bebida) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_bebidas_horarios_id_horario FOREIGN KEY (id_horario) REFERENCES horarios(id_horario) ON UPDATE CASCADE ON DELETE RESTRICT
);





