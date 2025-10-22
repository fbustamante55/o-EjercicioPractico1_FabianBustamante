-- Script: biblioteca_schema.sql
-- Crea la BD, tablas y datos de ejemplo para la aplicación de la biblioteca (MySQL)
-- 
-- Autor: Sistema Biblioteca Digital
-- Fecha: 2024
-- Descripción: Script completo para crear la base de datos de la biblioteca

-- 1) Crear la base de datos
DROP DATABASE IF EXISTS biblioteca;
CREATE DATABASE biblioteca
 CHARACTER SET = utf8mb4
 COLLATE = utf8mb4_unicode_ci;

USE biblioteca;

-- 2) Tabla categorias
DROP TABLE IF EXISTS categoria;
CREATE TABLE categoria (
 id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
 nombre VARCHAR(100) NOT NULL,
 descripcion VARCHAR(255) DEFAULT NULL,
 created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
 updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 PRIMARY KEY (id),
 UNIQUE KEY ux_categoria_nombre (nombre)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 3) Tabla libros
DROP TABLE IF EXISTS libro;
CREATE TABLE libro (
 id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
 titulo VARCHAR(255) NOT NULL,
 autor VARCHAR(200) NOT NULL,
 isbn VARCHAR(20) DEFAULT NULL,
 descripcion TEXT DEFAULT NULL,
 categoria_id BIGINT UNSIGNED NOT NULL,
 fecha_publicacion DATE DEFAULT NULL,
 disponible BOOLEAN NOT NULL DEFAULT TRUE,
 precio DECIMAL(10,2) DEFAULT NULL,
 created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
 updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 PRIMARY KEY (id),
 KEY idx_libro_categoria (categoria_id),
 KEY idx_libro_titulo (titulo),
 KEY idx_libro_autor (autor),
 KEY idx_libro_isbn (isbn),
 KEY idx_libro_disponible (disponible),
 CONSTRAINT fk_libro_categoria FOREIGN KEY (categoria_id)
 REFERENCES categoria (id) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 4) Tabla quejas (sugerencias/quejas)
DROP TABLE IF EXISTS queja;
CREATE TABLE queja (
 id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
 nombre_cliente VARCHAR(150) DEFAULT NULL,
 email VARCHAR(200) DEFAULT NULL,
 telefono VARCHAR(30) DEFAULT NULL,
 tipo ENUM('QUEJA','SUGERENCIA','CONSULTA') NOT NULL DEFAULT 'CONSULTA',
 asunto VARCHAR(200) DEFAULT NULL,
 mensaje TEXT NOT NULL,
 tratado BOOLEAN NOT NULL DEFAULT FALSE,
 created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
 PRIMARY KEY (id),
 KEY idx_queja_tipo (tipo),
 KEY idx_queja_tratado (tratado),
 KEY idx_queja_email (email),
 KEY idx_queja_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 5) Datos de ejemplo: categorías
INSERT INTO categoria (nombre, descripcion) VALUES
('Ficción', 'Novelas y relatos de ficción de diversos géneros literarios.'),
('Ciencia', 'Libros de divulgación científica y textos académicos sobre ciencias.'),
('Historia', 'Libros sobre eventos históricos, biografías y análisis históricos.'),
('Tecnología', 'Libros sobre programación, informática y tecnología moderna.'),
('Filosofía', 'Obras filosóficas, ética y pensamiento crítico.'),
('Arte', 'Libros sobre arte, diseño, arquitectura y expresión creativa.'),
('Salud', 'Libros sobre medicina, bienestar y salud personal.'),
('Educación', 'Libros educativos, pedagogía y desarrollo profesional.');

-- 6) Datos de ejemplo: libros
INSERT INTO libro (titulo, autor, isbn, descripcion, categoria_id, fecha_publicacion, disponible, precio) VALUES
('Cien años de soledad', 'Gabriel García Márquez', '978-0307474728', 'Novela clásica de realismo mágico que narra la historia de la familia Buendía a lo largo de siete generaciones en el pueblo ficticio de Macondo.', 1, '1967-06-05', TRUE, 19.90),
('Breves respuestas a las grandes preguntas', 'Stephen Hawking', '978-0241346714', 'Divulgación científica sobre las grandes preguntas de la humanidad desde la perspectiva de uno de los físicos más importantes del siglo XX.', 2, '2018-10-16', TRUE, 14.50),
('Sapiens: De animales a dioses', 'Yuval Noah Harari', '978-0062316097', 'Una exploración fascinante de cómo el Homo sapiens llegó a dominar el mundo, desde la revolución cognitiva hasta la actualidad.', 3, '2014-02-10', TRUE, 16.99),
('Clean Code', 'Robert C. Martin', '978-0132350884', 'Una guía práctica para escribir código limpio, mantenible y profesional en cualquier lenguaje de programación.', 4, '2008-08-01', TRUE, 45.00),
('El Arte de la Guerra', 'Sun Tzu', '978-0486425576', 'Tratado militar chino que ha influenciado tanto la estrategia militar como la empresarial durante siglos.', 5, '500-01-01', TRUE, 12.99),
('El Arte de la Programación', 'Donald Knuth', '978-0201896835', 'Una obra maestra de la informática que cubre algoritmos fundamentales y técnicas de programación avanzadas.', 4, '1968-01-01', TRUE, 89.99),
('Historia del Arte', 'E.H. Gombrich', '978-0714832470', 'Una introducción accesible y completa al arte occidental desde la antigüedad hasta el siglo XX.', 6, '1950-01-01', TRUE, 25.50),
('Medicina Interna', 'Harrison', '978-1259644047', 'Texto de referencia en medicina interna, considerado el estándar de oro para estudiantes y profesionales de la medicina.', 7, '2018-01-01', FALSE, 199.99),
('Pedagogía del Oprimido', 'Paulo Freire', '978-0826406118', 'Obra fundamental sobre educación liberadora y la relación entre educación y transformación social.', 8, '1970-01-01', TRUE, 18.75),
('1984', 'George Orwell', '978-0452284234', 'Novela distópica que explora temas de vigilancia, control social y manipulación de la verdad.', 1, '1949-06-08', TRUE, 13.99);

-- 7) Datos de ejemplo: quejas/sugerencias
INSERT INTO queja (nombre_cliente, email, telefono, tipo, asunto, mensaje, tratado) VALUES
('María Pérez', 'maria.perez@email.com', '506-8888-7777', 'SUGERENCIA', 'Ampliar horario', 'Sería ideal que la biblioteca abra más horas los fines de semana para facilitar el acceso a estudiantes y trabajadores.', FALSE),
('Juan Carlos Rodríguez', 'juan.rodriguez@email.com', '506-7777-6666', 'QUEJA', 'Libro dañado', 'El libro "Clean Code" que solicité tiene páginas rotas y es difícil de leer. Solicito un reemplazo.', FALSE),
('Ana Sofía González', 'ana.gonzalez@email.com', NULL, 'CONSULTA', 'Consulta sobre préstamos', '¿Cuál es el tiempo máximo de préstamo para libros técnicos? ¿Hay alguna diferencia con otros tipos de libros?', TRUE),
('Carlos Eduardo López', 'carlos.lopez@email.com', '506-6666-5555', 'SUGERENCIA', 'Mejorar catálogo digital', 'Sugiero agregar más libros de programación y tecnología, especialmente sobre frameworks modernos como React y Angular.', FALSE),
('Laura Martínez', 'laura.martinez@email.com', NULL, 'CONSULTA', 'Acceso remoto', '¿Es posible acceder a los libros digitales desde casa? ¿Hay algún sistema de préstamo digital disponible?', TRUE);

-- 8) Crear índices adicionales para mejorar el rendimiento
CREATE INDEX idx_libro_fecha_publicacion ON libro(fecha_publicacion);
CREATE INDEX idx_libro_precio ON libro(precio);
CREATE INDEX idx_categoria_nombre ON categoria(nombre);

-- 9) Crear vistas útiles para reportes
CREATE VIEW vista_libros_disponibles AS
SELECT 
    l.id,
    l.titulo,
    l.autor,
    l.isbn,
    c.nombre as categoria,
    l.fecha_publicacion,
    l.precio,
    l.created_at
FROM libro l
JOIN categoria c ON l.categoria_id = c.id
WHERE l.disponible = TRUE
ORDER BY l.titulo;

CREATE VIEW vista_quejas_pendientes AS
SELECT 
    q.id,
    q.nombre_cliente,
    q.email,
    q.tipo,
    q.asunto,
    q.created_at,
    DATEDIFF(NOW(), q.created_at) as dias_pendiente
FROM queja q
WHERE q.tratado = FALSE
ORDER BY q.created_at ASC;

CREATE VIEW vista_estadisticas_categorias AS
SELECT 
    c.nombre as categoria,
    COUNT(l.id) as total_libros,
    COUNT(CASE WHEN l.disponible = TRUE THEN 1 END) as libros_disponibles,
    COUNT(CASE WHEN l.disponible = FALSE THEN 1 END) as libros_no_disponibles,
    AVG(l.precio) as precio_promedio
FROM categoria c
LEFT JOIN libro l ON c.id = l.categoria_id
GROUP BY c.id, c.nombre
ORDER BY total_libros DESC;

-- 10) Crear procedimientos almacenados útiles
DELIMITER //

CREATE PROCEDURE sp_marcar_queja_tratada(IN p_queja_id BIGINT)
BEGIN
    UPDATE queja 
    SET tratado = TRUE 
    WHERE id = p_queja_id;
    
    SELECT 'Queja marcada como tratada exitosamente' as mensaje;
END //

CREATE PROCEDURE sp_obtener_libros_por_categoria(IN p_categoria_nombre VARCHAR(100))
BEGIN
    SELECT 
        l.titulo,
        l.autor,
        l.isbn,
        l.descripcion,
        l.fecha_publicacion,
        l.disponible,
        l.precio
    FROM libro l
    JOIN categoria c ON l.categoria_id = c.id
    WHERE c.nombre LIKE CONCAT('%', p_categoria_nombre, '%')
    ORDER BY l.titulo;
END //

CREATE PROCEDURE sp_estadisticas_generales()
BEGIN
    SELECT 
        (SELECT COUNT(*) FROM libro) as total_libros,
        (SELECT COUNT(*) FROM libro WHERE disponible = TRUE) as libros_disponibles,
        (SELECT COUNT(*) FROM categoria) as total_categorias,
        (SELECT COUNT(*) FROM queja) as total_quejas,
        (SELECT COUNT(*) FROM queja WHERE tratado = FALSE) as quejas_pendientes;
END //

DELIMITER ;

-- 11) Crear triggers para auditoría
DELIMITER //

CREATE TRIGGER tr_libro_insert_audit
AFTER INSERT ON libro
FOR EACH ROW
BEGIN
    INSERT INTO auditoria_libros (libro_id, accion, fecha_accion, usuario)
    VALUES (NEW.id, 'INSERT', NOW(), USER());
END //

CREATE TRIGGER tr_libro_update_audit
AFTER UPDATE ON libro
FOR EACH ROW
BEGIN
    INSERT INTO auditoria_libros (libro_id, accion, fecha_accion, usuario)
    VALUES (NEW.id, 'UPDATE', NOW(), USER());
END //

DELIMITER ;

-- 12) Crear tabla de auditoría (opcional)
CREATE TABLE IF NOT EXISTS auditoria_libros (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    libro_id BIGINT UNSIGNED NOT NULL,
    accion ENUM('INSERT', 'UPDATE', 'DELETE') NOT NULL,
    fecha_accion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    usuario VARCHAR(100),
    INDEX idx_auditoria_libro_id (libro_id),
    INDEX idx_auditoria_fecha (fecha_accion)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 13) Comprobar permisos y datos insertados
SELECT 'Verificando datos insertados...' as mensaje;

SELECT 'Categorías:' as tabla, COUNT(*) as cantidad FROM categoria
UNION ALL
SELECT 'Libros:', COUNT(*) FROM libro
UNION ALL
SELECT 'Quejas:', COUNT(*) FROM queja;

-- 14) Mostrar algunos datos de ejemplo
SELECT 'Libros disponibles:' as info;
SELECT titulo, autor, categoria_id FROM libro WHERE disponible = TRUE LIMIT 5;

SELECT 'Quejas pendientes:' as info;
SELECT nombre_cliente, tipo, asunto FROM queja WHERE tratado = FALSE LIMIT 3;

-- 15) Crear usuario específico para la aplicación (opcional)
-- CREATE USER 'biblioteca_user'@'localhost' IDENTIFIED BY 'biblioteca_password';
-- GRANT SELECT, INSERT, UPDATE, DELETE ON biblioteca.* TO 'biblioteca_user'@'localhost';
-- FLUSH PRIVILEGES;

-- Fin del script
SELECT 'Script ejecutado exitosamente. Base de datos biblioteca creada con datos de ejemplo.' as resultado;
