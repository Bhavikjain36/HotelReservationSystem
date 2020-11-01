INSERT INTO User(username, encrypted_Password, enabled)
VALUES ('Bhavik', '$2a$10$1ltibqiyyBJMJQ4hqM7f0OusP6np/IHshkYc4TjedwHnwwNChQZCy', 1);
INSERT INTO User(username, encrypted_Password, enabled)
VALUES ('Nishit', '$2a$10$1ltibqiyyBJMJQ4hqM7f0OusP6np/IHshkYc4TjedwHnwwNChQZCy', 1);

INSERT INTO Role(rolename)
VALUES('ROLE_ADMIN');
INSERT INTO Role(rolename)
VALUES('ROLE_USER');

INSERT INTO User_Roles(users_id, roles_id)
VALUES(1, 1);
INSERT INTO User_Roles(users_id, roles_id)
VALUES(1, 2);
INSERT INTO User_Roles(users_id, roles_id)
VALUES(2, 2);