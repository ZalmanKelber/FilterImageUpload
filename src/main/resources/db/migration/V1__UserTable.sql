CREATE TABLE imageuser (
    id UUID NOT NULL PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    hashedPassword VARCHAR(200) NOT NULL,
    imageIndex INT
);

CREATE TABLE image (
    id SERIAL PRIMARY KEY,
    userId UUID NOT NULL,
    filename VARCHAR(100) NOT NULL
);
