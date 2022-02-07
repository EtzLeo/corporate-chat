INSERT INTO users(name, password) VALUES ('user1', '$2a$12$k5V8/tcd13ZVu3jVlkAGQOKdS6VPvn6H5sSm3aeCLXmogwPOPDl0y');
INSERT INTO users(name, password) VALUES('user2', '$2a$12$k5V8/tcd13ZVu3jVlkAGQOKdS6VPvn6H5sSm3aeCLXmogwPOPDl0y');
INSERT INTO users(name, password) VALUES('user3', '$2a$12$k5V8/tcd13ZVu3jVlkAGQOKdS6VPvn6H5sSm3aeCLXmogwPOPDl0y');

INSERT INTO room(name, type, owner_id) VALUES ('bot-room', 'PRIVATE', 1);
INSERT INTO room(name, type, owner_id) VALUES ('bot-room', 'PRIVATE', 1);
INSERT INTO room(name, type, owner_id) VALUES ('bot-room', 'PRIVATE', 1);

INSERT INTO user_room(user_id, room_id, role, blocked, start_blocking, blocking_duration)
VALUES (2, 1, 'ROLE_USER', false, '2022-02-02 19:16:55.000000', 0);
INSERT INTO user_room(user_id, room_id, role, blocked, start_blocking, blocking_duration)
VALUES (3, 2, 'ROLE_USER', false, '2022-02-02 19:16:55.000000', 0);
INSERT INTO user_room(user_id, room_id, role, blocked, start_blocking, blocking_duration)
VALUES (4, 3, 'ROLE_USER', false, '2022-02-02 19:16:55.000000', 0);

INSERT INTO user_room(user_id, room_id, role, blocked, start_blocking, blocking_duration)
VALUES (1, 1, 'ROLE_ADMIN', false, '2022-02-02 19:16:55.000000', 0);
INSERT INTO user_room(user_id, room_id, role, blocked, start_blocking, blocking_duration)
VALUES (1, 2, 'ROLE_ADMIN', false, '2022-02-02 19:16:55.000000', 0);
INSERT INTO user_room(user_id, room_id, role, blocked, start_blocking, blocking_duration)
VALUES (1, 3, 'ROLE_ADMIN', false, '2022-02-02 19:16:55.000000', 0);

