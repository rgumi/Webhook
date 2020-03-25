INSERT INTO
    statuses (`msg_id`, `contact_id`, `status`)
VALUES
    ("m1234", 123, "read");

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