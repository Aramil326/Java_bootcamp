INSERT INTO chat.user (login, password)
VALUES ('jobrukulloipeu-1497@yopmail.com', 'sadf'),
       ('sirattettari-3859@yopmail.com', 'asdf'),
       ('cefranoihamau-9343@yopmail.com', 'asdf'),
       ('asdffdsa-9343@yopmail.com', 'asdf'),
       ('qwerrewq-9343@yopmail.com', 'asdf'),
       ('zxcvvcxzz-9343@yopmail.com', 'asdf'),
       ('bvnmmnbv-9343@yopmail.com', 'asdf');

INSERT INTO chat.chatroom (name, owner)
VALUES ('vertelki', (SELECT id FROM chat.user WHERE login = 'jobrukulloipeu-1497@yopmail.com')),
       ('flowers', (SELECT id FROM chat.user WHERE login = 'jobrukulloipeu-1497@yopmail.com')),
       ('cars and bids', (SELECT id FROM chat.user WHERE login = 'jobrukulloipeu-1497@yopmail.com')),
       ('cars', (SELECT id FROM chat.user WHERE login = 'sirattettari-3859@yopmail.com')),
       ('guns', (SELECT id FROM chat.user WHERE login = 'cefranoihamau-9343@yopmail.com')),
       ('plums', (SELECT id FROM chat.user WHERE login = 'asdffdsa-9343@yopmail.com')),
       ('applesFactory', (SELECT id FROM chat.user WHERE login = 'qwerrewq-9343@yopmail.com')),
       ('Birthday', (SELECT id FROM chat.user WHERE login = 'zxcvvcxzz-9343@yopmail.com')),
       ('Pong', (SELECT id FROM chat.user WHERE login = 'bvnmmnbv-9343@yopmail.com'));

INSERT INTO chat.message (author, room, text)
VALUES ((SELECT id FROM chat.user WHERE login = 'jobrukulloipeu-1497@yopmail.com'),
        (SELECT id FROM chat.chatroom WHERE name = 'vertelki'), 'hello'),
       ((SELECT id FROM chat.user WHERE login = 'sirattettari-3859@yopmail.com'),
        (SELECT id FROM chat.chatroom WHERE name = 'cars'), 'hi'),
       ((SELECT id FROM chat.user WHERE login = 'cefranoihamau-9343@yopmail.com'),
        (SELECT id FROM chat.chatroom WHERE name = 'guns'), 'nice'),
       ((SELECT id FROM chat.user WHERE login = 'asdffdsa-9343@yopmail.com'),
        (SELECT id FROM chat.chatroom WHERE name = 'plums'), 'hello'),
       ((SELECT id FROM chat.user WHERE login = 'qwerrewq-9343@yopmail.com'),
        (SELECT id FROM chat.chatroom WHERE name = 'applesFactory'), 'hello'),
       ((SELECT id FROM chat.user WHERE login = 'zxcvvcxzz-9343@yopmail.com'),
        (SELECT id FROM chat.chatroom WHERE name = 'Birthday'), 'hello'),
       ((SELECT id FROM chat.user WHERE login = 'bvnmmnbv-9343@yopmail.com'),
        (SELECT id FROM chat.chatroom WHERE name = 'Pong'), 'hello');

INSERT INTO chat.user_chatroom (user_id, chatroom_id)
VALUES ((SELECT id FROM chat.user WHERE login = 'jobrukulloipeu-1497@yopmail.com'),
        (SELECT id FROM chat.chatroom WHERE name = 'vertelki')),
       ((SELECT id FROM chat.user WHERE login = 'sirattettari-3859@yopmail.com'),
        (SELECT id FROM chat.chatroom WHERE name = 'cars')),
       ((SELECT id FROM chat.user WHERE login = 'cefranoihamau-9343@yopmail.com'),
        (SELECT id FROM chat.chatroom WHERE name = 'guns')),
       ((SELECT id FROM chat.user WHERE login = 'asdffdsa-9343@yopmail.com'),
        (SELECT id FROM chat.chatroom WHERE name = 'plums')),
       ((SELECT id FROM chat.user WHERE login = 'qwerrewq-9343@yopmail.com'),
        (SELECT id FROM chat.chatroom WHERE name = 'applesFactory')),
       ((SELECT id FROM chat.user WHERE login = 'zxcvvcxzz-9343@yopmail.com'),
        (SELECT id FROM chat.chatroom WHERE name = 'Birthday')),
       ((SELECT id FROM chat.user WHERE login = 'bvnmmnbv-9343@yopmail.com'),
        (SELECT id FROM chat.chatroom WHERE name = 'Pong')),
       ((SELECT id FROM chat.user WHERE login = 'bvnmmnbv-9343@yopmail.com'),
        (SELECT id FROM chat.chatroom WHERE name = 'Birthday')),
       ((SELECT id FROM chat.user WHERE login = 'bvnmmnbv-9343@yopmail.com'),
        (SELECT id FROM chat.chatroom WHERE name = 'applesFactory'));
