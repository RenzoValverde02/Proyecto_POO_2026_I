-- =========================================================
-- BASE DE DATOS SysGourmet 
-- =========================================================

CREATE DATABASE IF NOT EXISTS restauranteSysGourmet_db
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE restauranteSysGourmet_db;

-- USUARIOS

CREATE TABLE IF NOT EXISTS usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    usuario VARCHAR(50) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL,
    rol ENUM('ADMIN', 'MOZO', 'CAJERO', 'COCINERO') NOT NULL,
    estado TINYINT DEFAULT 1,
    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP,

    CHECK (estado IN (0, 1))
) ENGINE=InnoDB;

-- =========================================================
-- DATOS POR DEFECTO (Para que funcione en otras PCs)
-- =========================================================

INSERT INTO usuarios (nombre, apellido, usuario, contrasena, rol, estado) VALUES 
('Jefe', 'Admin', 'admin', '123456', 'ADMIN', 1),
('Juan', 'Mozo', 'juan', '123456', 'MOZO', 1),
('Maria', 'Cajera', 'maria', '123456', 'CAJERO', 1),
('Carlos', 'Cocinero', 'carlos', '123456', 'COCINERO', 1);
-- MESAS

CREATE TABLE IF NOT EXISTS mesas (
    id_mesa INT AUTO_INCREMENT PRIMARY KEY,
    numero_mesa VARCHAR(10) NOT NULL UNIQUE,
    capacidad INT NOT NULL,
    estado ENUM('LIBRE', 'OCUPADA', 'RESERVADA', 'MANTENIMIENTO') DEFAULT 'LIBRE',

    CHECK (capacidad > 0)
) ENGINE=InnoDB;


-- MENU

CREATE TABLE IF NOT EXISTS menu_items (
    id_item INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    categoria ENUM('ENTRADA', 'FONDO', 'BEBIDA', 'POSTRE') NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    estado ENUM('DISPONIBLE', 'AGOTADO') DEFAULT 'DISPONIBLE',

    CHECK (precio >= 0)
) ENGINE=InnoDB;


-- PEDIDOS

CREATE TABLE IF NOT EXISTS pedidos (
    id_pedido INT AUTO_INCREMENT PRIMARY KEY,
    id_mesa INT NOT NULL,
    id_mozo INT NOT NULL,
    fecha_hora DATETIME DEFAULT CURRENT_TIMESTAMP,
    estado ENUM('PENDIENTE', 'EN_PREPARACION', 'LISTO', 'PAGADO', 'CANCELADO') DEFAULT 'PENDIENTE',

    FOREIGN KEY (id_mesa) REFERENCES mesas(id_mesa)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,

    FOREIGN KEY (id_mozo) REFERENCES usuarios(id_usuario)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
) ENGINE=InnoDB;


-- DETALLE DEL PEDIDO

CREATE TABLE IF NOT EXISTS detalle_pedido (
    id_detalle INT AUTO_INCREMENT PRIMARY KEY,
    id_pedido INT NOT NULL,
    id_item INT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    nota VARCHAR(200),
    estado_cocina ENUM('PENDIENTE', 'EN_PREPARACION', 'LISTO') DEFAULT 'PENDIENTE',

    FOREIGN KEY (id_pedido) REFERENCES pedidos(id_pedido)
        ON UPDATE CASCADE
        ON DELETE CASCADE,

    FOREIGN KEY (id_item) REFERENCES menu_items(id_item)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,

    CHECK (cantidad > 0),
    CHECK (precio_unitario >= 0)
) ENGINE=InnoDB;


-- PAGOS

CREATE TABLE IF NOT EXISTS pagos (
    id_pago INT AUTO_INCREMENT PRIMARY KEY,
    id_pedido INT NOT NULL UNIQUE,
    tipo_comprobante ENUM('BOLETA', 'FACTURA') NOT NULL,
    numero_comprobante VARCHAR(20) NOT NULL UNIQUE,
    metodo_pago ENUM('EFECTIVO', 'TARJETA', 'YAPE', 'PLIN') NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    igv DECIMAL(10,2) NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    monto_recibido DECIMAL(10,2),
    vuelto DECIMAL(10,2),
    fecha_pago DATETIME DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (id_pedido) REFERENCES pedidos(id_pedido)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,

    CHECK (subtotal >= 0),
    CHECK (igv >= 0),
    CHECK (total >= 0),
    CHECK (monto_recibido IS NULL OR monto_recibido >= 0),
    CHECK (vuelto IS NULL OR vuelto >= 0)
) ENGINE=InnoDB;


