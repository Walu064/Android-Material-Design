CREATE TABLE RegisteredUsers (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_login VARCHAR(50),
    user_email VARCHAR(100),
    user_password VARCHAR(50),
    user_name VARCHAR(50),
    user_surname VARCHAR(50)
);

CREATE TABLE note(
    note_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    note_owner VARCHAR(50),
    note_title VARCHAR(50),
    note_content VARCHAR(500)
)