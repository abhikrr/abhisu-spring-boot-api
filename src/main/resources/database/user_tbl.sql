CREATE TABLE user_tbl (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(100) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL,
  role_id BIGINT,
  FOREIGN KEY (role_id) REFERENCES role_tbl(id)
);


-- Insert User with BCrypt hashed password "password"
INSERT INTO user_tbl (username, password, role_id) VALUES
('john_doe', '$2a$10$DOWSDdMf4AczFoYJq9HEjOZrEtWhHhYZ1ymVxti9yo/6yQAzvKb8W', 1),
('admin_user', '$2a$10$DOWSDdMf4AczFoYJq9HEjOZrEtWhHhYZ1ymVxti9yo/6yQAzvKb8W', 2);