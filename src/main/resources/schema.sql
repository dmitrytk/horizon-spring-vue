CREATE TABLE fields
(
    id           BIGSERIAL PRIMARY KEY,
    name         text UNIQUE NOT NULL,
    type         text,
    location     text
);


CREATE TABLE wells
(
    id               BIGSERIAL PRIMARY KEY,
    name             text    NOT NULL,
    pad              text,
    type             text,
    status           text,
    lat              numeric(10, 6),
    lng              numeric(10, 6),
    x                numeric(20, 2),
    y                numeric(20, 2),
    alt              numeric(6, 2),
    bottom           numeric(7, 2),
    field_id         integer NOT NULL,
    UNIQUE (name, field_id),
    FOREIGN KEY (field_id) REFERENCES fields (id) ON DELETE CASCADE
);

CREATE TABLE inclinometry
(
    id      BIGSERIAL PRIMARY KEY,
    well_id integer,
    md      numeric(7, 2) NOT NULL CHECK ( md >= 0 ),
    inc     numeric(5, 2),
    azi     numeric(5, 2),
    FOREIGN KEY (well_id) REFERENCES wells (id) ON DELETE CASCADE
);


CREATE TABLE fields_coordinates
(
    id           BIGSERIAL PRIMARY KEY,
    x            numeric(20, 2),
    y            numeric(20, 2),
    lat          numeric(10, 7),
    lng          numeric(10, 7),
    field_id     integer,
    FOREIGN KEY (field_id) REFERENCES fields (id) ON DELETE CASCADE
);