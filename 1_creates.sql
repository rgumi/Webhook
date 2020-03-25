DROP TABLE IF EXISTS statuses;

DROP TABLE IF EXISTS messages;

DROP TABLE IF EXISTS contacts;
^
-- Mixed Primary Key msg_id and status
-- ID AUTO_INCREMENT not necessary
CREATE TABLE statuses (
    `msg_id` varchar(50) not null,
    `contact_id` integer not null,
    `status` varchar(10) not null,
    `updated_at` timestamp default now(),

    PRIMARY KEY(`msg_id`, `status`)
);

CREATE TABLE contacts (
    `contact_id` integer PRIMARY KEY,
    `profile_name` varchar(100),
    `created_at` timestamp default now(),
    `last_access` timestamp default now() on update now()
);

CREATE TABLE messages (
    `id` integer AUTO_INCREMENT PRIMARY KEY,
    `msg_id` varchar(50) not null,
    `contact_id` integer not null,
    `msg_type` varchar(10) not null,
    `msg_object` varchar(255) not null,
    `created_at` timestamp default now()
);

CREATE UNIQUE INDEX idx_messages_msg_Id ON messages (`msg_id`);

ALTER TABLE
    messages
ADD
    CONSTRAINT FK_contact_messages FOREIGN KEY (`contact_id`) REFERENCES contacts(`contact_id`);


-- Assume that msg_id is always correct
-- no need for foreign key
-- ALTER TABLE
--     statuses
-- ADD
--     CONSTRAINT FK_messages_statuses FOREIGN KEY (`msg_id`) REFERENCES messages(`msg_id`);

INSERT INTO
    contacts (`contact_id`, `profile_name`)
 VALUES
    (123, "Ron");

INSERT INTO
    messages (`msg_id`, `contact_id`, `msg_type`, `msg_object`)
VALUES
    (
        "m1234",
        123,
        "text",
        "{\"body\": \"Hello World!\""
    );


SELECT
    *
FROM
    messages m
    INNER JOIN contacts c ON m.contact_id = c.contact_id;

INSERT INTO
    statuses (`msg_id`, `contact_id`, `status`)
VALUES
    ("m1234", 123, "sent");

SELECT
    s.msg_id,
    s.status,
    s.contact_id,
    m.msg_type,
    s.updated_at
FROM
    statuses s
    INNER JOIN messages m ON s.msg_id = m.msg_id
ORDER BY
    s.updated_at;

SELECT
    messages.msg_id,
    messages.contact_id,
    statuses.status,
    messages.msg_type,
    statuses.updated_at
FROM
    (
        SELECT
            msg_id,
            MAX(updated_at) AS updated_at
        FROM
            statuses
        GROUP BY
            msg_id
    ) AS latest_updates
    INNER JOIN statuses ON statuses.msg_id = latest_updates.msg_id
    AND statuses.updated_at = latest_updates.updated_at
    INNER JOIN messages ON messages.msg_id = statuses.msg_id;