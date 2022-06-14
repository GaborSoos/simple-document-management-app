DELETE FROM document_doc_tags;
DELETE FROM doc_tags;
DELETE FROM documents;
DELETE FROM doc_types;
DELETE FROM partners;
DELETE FROM user_roles;
DELETE FROM roles;
DELETE FROM users;

INSERT INTO users (id, username, firstname, lastname, password, enabled) VALUES
(1, 'johndoe', 'John' , 'Doe', '$2a$10$fdfwiDTCQjarhj7U80RgJuImLhSOAKpOLXJYPtY15xpSyMWW.HfRy', 1),
(2, 'janedoe', 'Jane', 'Doe', '$2a$10$fdfwiDTCQjarhj7U80RgJuImLhSOAKpOLXJYPtY15xpSyMWW.HfRy', 1),
(3, 'gipszjakab', 'Jakab', 'Gipsz', '$2a$10$b7wnqdlcepRbdn.PFKww8e11aZaLdZtOGl8RVNkd1mKFuWRzA/IpK', 1),
(4, 'hackelek', 'Elek', 'Hack', '$2a$10$b7wnqdlcepRbdn.PFKww8e11aZaLdZtOGl8RVNkd1mKFuWRzA/IpK', 0);

INSERT INTO roles (id, name, enabled) VALUES
(1, 'ADMIN', 1),
(2, 'USER', 1),
(3, 'GUEST', 1);

INSERT INTO user_roles (id, user_id, role_id, enabled) VALUES
(1, 1, 2, 1),
(2, 2, 1, 1),
(3, 3, 3, 1),
(4, 4, 1, 0);

INSERT INTO partners (id, name, address, email, telephone, enabled) VALUES
(1, 'MOL', '1111 Bp. Texas utca 1.', 'oil@mol.hu', '06-1-234-5678', 1),
(2, 'Google', '1111 Bp. Kereső utca 1.', 'info@google.com', '06-1-234-5678', 1),
(3, 'Facebook', '1111 Bp. Cukkemberg utca 1.', 'info@fb.com', '06-1-234-5678', 1);

INSERT INTO doc_types (id, name) VALUES
(1, 'invoice'),
(2, 'contract');

INSERT INTO documents (id, original_document_id, doc_type_id, subject, description, original_document_date, partner_id, enabled) VALUES
(1, 'INV/2022/00001', 1, 'Üzemanyag számla - 2022.01.hó', 'Ez egy sima üzemanyagszámla az XYZ-789 rendszámú céges autóhoz', '2022-01-01', 1, 1),
(2, 'INV/2022/00002', 1, 'Üzemanyag számla - 2022.02.hó', 'Ez egy sima üzemanyagszámla az XYZ-789 rendszámú céges autóhoz', '2022-02-01', 1, 1);

INSERT INTO doc_tags (id, name) VALUES
(1, 'finance-document'),
(2, 'hr-document'),
(3, 'it-document');

INSERT INTO document_doc_tags (document_id, doc_tag_id) VALUES
(1, 1),
(2, 1);