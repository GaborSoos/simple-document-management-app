CREATE TABLE partners (
                       id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(255) NOT NULL UNIQUE,
                       address VARCHAR(255) NOT NULL UNIQUE,
                       email VARCHAR(100) NOT NULL UNIQUE,
                       telephone VARCHAR(50),
                       enabled BOOLEAN
);

CREATE TABLE doc_types (
                       id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE doc_tags (
                       id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE documents (
                       id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                       original_document_id VARCHAR(255) NOT NULL UNIQUE,
                       doc_type_id BIGINT NOT NULL,
                       subject VARCHAR(100) NOT NULL,
                       description VARCHAR(255),
                       original_document_date DATE NOT NULL,
                       partner_id BIGINT,
                       enabled BOOLEAN,
                       FOREIGN KEY (partner_id) REFERENCES partners(id)
);

CREATE TABLE document_doc_tags (
                                document_id BIGINT NOT NULL,
                                doc_tag_id BIGINT NOT NULL,
                                PRIMARY KEY(document_id, doc_tag_id),
                                FOREIGN KEY (document_id) REFERENCES documents(id),
                                FOREIGN KEY (doc_tag_id) REFERENCES doc_tags(id)
);




