/*
* Saul Echeverri
* 17-02-2026
* Sprint #1
* Archivo Version Incial, esta version es creada con el fin de llevar control de la version de los scripts de BD
* Manual de Uso:
* 1. Abrir un espacio de comentarios entre corchetes
* 2. Definir el Usuario que realiza la modificación
* 3. Fecha en la cual se realiza modificación
* 4. Detalle o definicion de la modificación
* 5. Anexar script para ser ejecutado
* 6. Generar un Pull Request siguiendo el proceso establecido para actualización de ramas
*/

/*
* Saul Echeverri
* 17-02-2026
* Version Inicial - Script de creación y datos de tablas: bd_student_management_spring_boot_lta con las tablas:
* students
*/
-- Tabla students
CREATE TABLE IF NOT EXISTS public.students (
    id_student SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NULL
);
COMMENT ON TABLE students IS 'Tabla que registra la información inicial de los estudiantes';
COMMENT ON COLUMN students.id_student IS 'Identificador de la tabla students';
COMMENT ON COLUMN students.name IS 'Nombre del estudiante';
COMMENT ON COLUMN students.last_name IS 'Apellido del estudiante';
COMMENT ON COLUMN students.email IS 'Correo electronico del estudiante';
COMMENT ON COLUMN students.created_at IS 'Fecha de creación del registro';
COMMENT ON COLUMN students.updated_at IS 'Fecha de actualización del registro';