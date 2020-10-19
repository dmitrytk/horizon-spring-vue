DROP DATABASE horizon;

CREATE DATABASE horizon
    WITH OWNER postgres
    ENCODING 'UTF8'
    LC_COLLATE = 'ru_RU.UTF-8'
    LC_CTYPE = 'ru_RU.UTF-8'
    TEMPLATE = template0;

-- Set datestyle to dd.mm.yyyy
-- SET DATESTYLE German;

\c horizon;