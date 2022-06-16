INSERT INTO users (id, username, firstname, lastname, password, enabled) VALUES
(1, 'johndoe', 'John' , 'Doe', '$2a$10$fdfwiDTCQjarhj7U80RgJuImLhSOAKpOLXJYPtY15xpSyMWW.HfRy', 1);

INSERT INTO roles (id, name, enabled) VALUES
(1, 'ROLE_ADMIN', 1),
(2, 'ROLE_USER', 1),
(3, 'ROLE_GUEST', 1);

INSERT INTO user_roles (id, user_id, role_id, enabled) VALUES
(1, 1, 1, 1);
