
DROP TABLE IF EXISTS comment;
DROP TABLE IF EXISTS task;

CREATE TABLE IF NOT EXISTS task
(
    id          BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    parent_id   BIGINT,
    name        VARCHAR(75) NOT NULL,
    description VARCHAR(255),
    localDate   TIMESTAMP,
    status      VARCHAR(15)
);

CREATE TABLE IF NOT EXISTS comment
(
    id        BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    task_id   BIGINT       NOT NULL,
    text      VARCHAR(255) NOT NULL,
    localDate TIMESTAMP,
    CONSTRAINT FOREIGN KEY (task_id) REFERENCES task (id)
)