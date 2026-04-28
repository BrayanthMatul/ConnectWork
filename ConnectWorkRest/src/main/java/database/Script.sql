CREATE DATABASE connect_work_db;

CREATE USER 'user_proyecto_final'@'localhost' IDENTIFIED BY '12345';
GRANT ALL PRIVILEGES ON connect_work_db.* TO 'user_proyecto_final'@'localhost';
FLUSH PRIVILEGES;

USE connect_work_db;

CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    nombre_completo VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    tipo_usuario VARCHAR(50) NOT NULL
);

INSERT INTO usuarios (username, password, nombre_completo, email, tipo_usuario) VALUES
('admin', '$2a$10$u1QwQwQwQwQwQwQwQwQwQeQwQwQwQwQwQwQwQwQwQwQwQwQwQwQw', 'Administrador', 'admin@example.com', 'administrador');

CREATE TABLE perfiles (
    id_perfil INT PRIMARY KEY,
    cui VARCHAR(15) NOT NULL UNIQUE,
    telefono VARCHAR(20) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    direccion VARCHAR(255) NOT NULL,
    saldo DECIMAL(10, 2) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    perfil_completado BOOLEAN NOT NULL DEFAULT FALSE,

    FOREIGN KEY (id_perfil) REFERENCES usuarios(id)
);

CREATE TABLE freelancers (
    id_freelancer INT PRIMARY KEY,
    biografia TEXT NOT NULL,
    nivel_experiencia VARCHAR(50) NOT NULL,
    calificacion DECIMAL(3, 2) NOT NULL,
    tarifa_hora DECIMAL(10, 2) NOT NULL,

    FOREIGN KEY (id_freelancer) REFERENCES usuarios(id)
);

CREATE TABLE clientes (
    id_cliente INT PRIMARY KEY,
    descripcion TEXT NOT NULL,
    sector VARCHAR(255) NOT NULL,
    sitio_web VARCHAR(255),

    FOREIGN KEY (id_cliente) REFERENCES usuarios(id)
);

CREATE TABLE recargas(
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT NOT NULL,
    monto DECIMAL(10, 2) NOT NULL,
    fecha_hora DATETIME NOT NULL,

    FOREIGN KEY (id_cliente) REFERENCES clientes(id_cliente)
);

CREATE TABLE categorias (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL UNIQUE, 
    descripcion VARCHAR(255) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE habilidades (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_categoria INT NOT NULL,
    nombre VARCHAR(255) NOT NULL UNIQUE,
    descripcion VARCHAR(255) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    FOREIGN KEY (id_categoria) REFERENCES categorias(id)
);

CREATE TABLE freelancer_habilidades (
    id_freelancer INT NOT NULL,
    id_habilidad INT NOT NULL,
    PRIMARY KEY (id_freelancer, id_habilidad),
    FOREIGN KEY (id_freelancer) REFERENCES freelancers(id_freelancer),
    FOREIGN KEY (id_habilidad) REFERENCES habilidades(id)
);

CREATE TABLE proyectos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT NOT NULL,
    id_categoria INT NOT NULL,
    titulo VARCHAR(255) NOT NULL,
    descripcion TEXT NOT NULL,
    presupuesto_maximo DECIMAL(10, 2) NOT NULL,
    fecha_limite DATETIME NOT NULL,
    estado VARCHAR(50) NOT NULL,

    FOREIGN KEY (id_cliente) REFERENCES clientes(id_cliente),
    FOREIGN KEY (id_categoria) REFERENCES categorias(id)
);

CREATE TABLE proyecto_habilidades (
    id_proyecto INT NOT NULL,
    id_habilidad INT NOT NULL,
    PRIMARY KEY (id_proyecto, id_habilidad),
    FOREIGN KEY (id_proyecto) REFERENCES proyectos(id),
    FOREIGN KEY (id_habilidad) REFERENCES habilidades(id)
);

CREATE TABLE propuestas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_proyecto INT NOT NULL,
    id_freelancer INT NOT NULL,
    monto_ofertado DECIMAL(10, 2) NOT NULL,
    mensaje TEXT NOT NULL,
    fecha_hora DATETIME NOT NULL,
    plazoEntrega INT NOT NULL,
    estado VARCHAR(50) NOT NULL,

    FOREIGN KEY (id_proyecto) REFERENCES proyectos(id),
    FOREIGN KEY (id_freelancer) REFERENCES freelancers(id_freelancer)
);

CREATE TABLE contratos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_propuesta INT NOT NULL,
    fecha_inicio DATETIME NOT NULL,
    monto DECIMAL(10, 2) NOT NULL,
    estado VARCHAR(50) NOT NULL,
    calificacion DECIMAL(3, 2),

    FOREIGN KEY (id_propuesta) REFERENCES propuestas(id)
);

CREATE TABLE entregas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_contrato INT NOT NULL,
    descripcion TEXT NOT NULL,
    archivo_url VARCHAR(255) NOT NULL,
    fecha_hora DATETIME NOT NULL,
    estado VARCHAR(50) NOT NULL,
    motivo_rechazo TEXT,

    FOREIGN KEY (id_contrato) REFERENCES contratos(id)
);

CREATE TABLE saldos_sistema (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_contrato INT NOT NULL UNIQUE,
    fecha_hora DATETIME NOT NULL,
    porcentaje_comision DECIMAL(5, 2) NOT NULL,
    monto_bruto DECIMAL(10, 2) NOT NULL,
    monto_comision DECIMAL(10, 2) NOT NULL,

    FOREIGN KEY (id_contrato) REFERENCES contratos(id)
);

CREATE TABLE historial_porcentajes_comisiones (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fecha_hora_inicio DATETIME NOT NULL,
    fecha_hora_fin DATETIME,
    porcentaje_comision DECIMAL(5, 2) NOT NULL
);