CREATE TABLE Book (
    id INT PRIMARY KEY,
    isbn INT,
    genre VARCHAR(255),
    title VARCHAR(255),
    author VARCHAR(255),
    year_published INT,
    member_id INT,
    FOREIGN KEY (member_id) REFERENCES Member(id)
);
