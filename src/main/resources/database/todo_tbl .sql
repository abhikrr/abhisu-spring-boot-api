CREATE TABLE todo_tbl (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    description TEXT,
    completed BOOLEAN,
    user_id BIGINT,
    CONSTRAINT fk_todo_user FOREIGN KEY (user_id) REFERENCES user_tbl(id)
);

-- For user_id = 1 (john_doe)
INSERT INTO todo_tbl (title, description, completed, user_id) VALUES
('Buy groceries', 'Milk, eggs, bread, and fruit', false, 1),
('Pay electricity bill', 'Due before 25th of the month', true, 1),
('Morning workout', 'Go to gym at 7 AM', false, 1);

-- For user_id = 2 (admin_user)
INSERT INTO todo_tbl (title, description, completed, user_id) VALUES
('Review new feature PR', 'Check pull request #42 from dev team', false, 2),
('Deploy backend service', 'Push latest changes to production', false, 2);
