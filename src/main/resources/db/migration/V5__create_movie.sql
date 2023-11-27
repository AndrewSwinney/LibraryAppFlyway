CREATE TABLE Movie (
    id INT PRIMARY KEY,
    title VARCHAR(255),
    lead_actor VARCHAR(255),
    director VARCHAR(255),
    screen_writer VARCHAR(255),
    release_year INT,
    genre VARCHAR(255),
    rating DOUBLE,
    member_id INT,
    FOREIGN KEY (member_id) REFERENCES Member(id)
);
