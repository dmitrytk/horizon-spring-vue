CREATE TABLE IF NOT EXISTS fields
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(100) UNIQUE NOT NULL,
    type        VARCHAR(50),
    location    VARCHAR(100),
    description TEXT
);


CREATE TABLE IF NOT EXISTS field_coordinates
(
    id       BIGSERIAL PRIMARY KEY,
    x        NUMERIC(20, 2),
    y        NUMERIC(20, 2),
    lat      NUMERIC(10, 7),
    lng      NUMERIC(10, 7),
    field_id INTEGER,
    FOREIGN KEY (field_id) REFERENCES fields (id) ON DELETE CASCADE
);



CREATE TABLE IF NOT EXISTS wells
(
    id       BIGSERIAL PRIMARY KEY,
    name     VARCHAR(255) NOT NULL,
    pad      VARCHAR(100),
    type     VARCHAR(100),
    status   VARCHAR(100),
    lat      NUMERIC(10, 6),
    lng      NUMERIC(10, 6),
    x        NUMERIC(20, 2),
    y        NUMERIC(20, 2),
    alt      NUMERIC(6, 2),
    bottom   NUMERIC(7, 2),
    field_id INTEGER      NOT NULL,
    UNIQUE (name, field_id),
    FOREIGN KEY (field_id) REFERENCES fields (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS inclinometry
(
    id      BIGSERIAL PRIMARY KEY,
    well_id INTEGER,
    md      NUMERIC(7, 2) NOT NULL CHECK ( md >= 0 ),
    inc     NUMERIC(5, 2),
    azi     NUMERIC(5, 2),
    FOREIGN KEY (well_id) REFERENCES wells (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS rates
(
    id       BIGSERIAL PRIMARY KEY,
    well_id  INTEGER,
    date     DATE,
    rate     NUMERIC(10, 2),
    dynamic  NUMERIC(6, 2),
    static   NUMERIC(6, 2),
    pressure NUMERIC(6, 2),
    FOREIGN KEY (well_id) REFERENCES wells (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS mer
(
    id         BIGSERIAL PRIMARY KEY,
    well_id    INTEGER,
    date       DATE,
    status     VARCHAR(100),
    rate       NUMERIC(10, 2),
    production NUMERIC(20, 2),
    work_days  INTEGER,
    FOREIGN KEY (well_id) REFERENCES wells (id) ON DELETE CASCADE
);


