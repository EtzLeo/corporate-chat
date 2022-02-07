CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    name TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL
);

INSERT INTO users(name, password) VALUES ('BOT', 1234);

CREATE TABLE room (
    id BIGSERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    type TEXT NOT NULL,
    owner_id BIGINT NOT NULL,
    FOREIGN KEY (owner_id) REFERENCES users
);

CREATE TABLE message (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    room_id BIGINT NOT NULL,
    text TEXT NOT NULL,
    delivering_time TIMESTAMP NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users,
    FOREIGN KEY (room_id) REFERENCES room
);

CREATE TABLE user_room (
    user_id BIGINT NOT NULL,
    room_id BIGINT NOT NULL,
    role TEXT NOT NULL,
    blocked BOOLEAN NOT NULL,
    start_blocking TIMESTAMP NOT NULL,
    blocking_duration BIGINT NOT NULL,
    PRIMARY KEY (user_id, room_id),
    FOREIGN KEY (room_id) REFERENCES room,
    FOREIGN KEY (user_id) REFERENCES users
);