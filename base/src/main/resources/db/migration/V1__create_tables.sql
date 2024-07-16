-- Creación de la tabla usuarios
CREATE TABLE IF NOT EXISTS usuarios (
  id BIGINT(20) NOT NULL AUTO_INCREMENT,
  contrasenia VARCHAR(255) DEFAULT NULL,
  email VARCHAR(100) DEFAULT NULL UNIQUE,
  estado TINYINT(1) DEFAULT NULL,
  fecha_actualizacion DATETIME DEFAULT NULL,
  fecha_creacion DATETIME DEFAULT NULL,
  nombre VARCHAR(50) DEFAULT NULL,
  role ENUM('ADMIN', 'USER') DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Creación de la tabla topics
CREATE TABLE IF NOT EXISTS topics (
  id BIGINT(20) NOT NULL AUTO_INCREMENT,
  estado TINYINT(1) DEFAULT NULL,
  fecha_actualizacion DATETIME DEFAULT NULL,
  fecha_creacion DATETIME DEFAULT NULL,
  genero ENUM('COCINA', 'DEPORTE', 'TECNOLOGIA', 'VIDEO_JUEGOS') DEFAULT NULL,
  mensaje VARCHAR(2550) NOT NULL,
  titulo VARCHAR(255) NOT NULL,
  usuario_id BIGINT(20) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FK_usuario (usuario_id),
  CONSTRAINT FK_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Creación de la tabla respuestas
CREATE TABLE IF NOT EXISTS respuestas (
  id BIGINT(20) NOT NULL AUTO_INCREMENT,
  estado TINYINT(1) DEFAULT NULL,
  fecha_actualizacion DATETIME DEFAULT NULL,
  fecha_creacion DATETIME DEFAULT NULL,
  mensaje_respuesta VARCHAR(255) NOT NULL,
  topic_id BIGINT(20) DEFAULT NULL,
  usuario_id BIGINT(20) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY FK_topic (topic_id),
  KEY FK_usuario_respuesta (usuario_id),
  CONSTRAINT FK_usuario_respuesta FOREIGN KEY (usuario_id) REFERENCES usuarios (id),
  CONSTRAINT FK_topic FOREIGN KEY (topic_id) REFERENCES topics (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
