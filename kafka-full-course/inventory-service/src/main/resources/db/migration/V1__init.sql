CREATE TABLE venue (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL ,
    total_capacity INTEGER NOT NULL
);

CREATE TABLE event (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) not null ,
    venue_id BIGINT NOT NULL,
    total_capacity INTEGER NOT NULL ,
    left_capacity INTEGER NOT NULL ,
    FOREIGN KEY (venue_id) REFERENCES venue(id)
)
