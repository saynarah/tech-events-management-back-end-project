CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE event(
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description VARCHAR(250) NOT NULL,
    imgURL VARCHAR(600) NOT NULL,
    event_Url VARCHAR(600) NOT NULL,
    date TIMESTAMP NOT NULL,
    remote BOOLEAN NOT NULL

);