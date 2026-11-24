CREATE TABLE IF NOT EXISTS customer(
    id UUID primary key,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(50)
);