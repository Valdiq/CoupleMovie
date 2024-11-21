CREATE TABLE IF NOT EXISTS film (
    id BIGINT AUTO_INCREMENT,
    imdbId VARCHAR(255),
    title VARCHAR(255),
    year VARCHAR(30),
    type VARCHAR(50),
    poster VARCHAR(255),
    PRIMARY KEY (id)
);