/*
* Saul Echeverri
* 17-02-2026
* Tabla de logs
*/
-- Tabla logs
CREATE TABLE IF NOT EXISTS public.logs (
    id_log SERIAL PRIMARY KEY,
    action VARCHAR(100) NOT NULL,
    log_timestamp TIMESTAMP NOT NULL DEFAULT NOW()
);