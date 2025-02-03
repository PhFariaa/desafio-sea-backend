CREATE TABLE books(
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    isbn VARCHAR(13) NOT NULL,
    number_of_pages INT NOT NULL,
    publication_date TIMESTAMP NOT NULL
)