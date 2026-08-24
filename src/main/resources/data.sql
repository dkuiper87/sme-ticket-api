
-- USERS
-- Passwords are dummy data

INSERT INTO users (username, password, email) VALUES ('test_admin1', 'DummyPassword1', 'admin1@novi.nl');
INSERT INTO users (username, password, email) VALUES ('test_sme1', 'DummyPassword1!', 'sme1@novi.nl');
INSERT INTO users (username, password, email) VALUES ('test_sme2', 'DummyPassword1!', 'sme2@novi.nl');
INSERT INTO users (username, password, email) VALUES ('test_student1', 'DummyPassword1!', 'student1@novi.nl');
INSERT INTO users (username, password, email) VALUES ('test_student2', 'DummyPassword1!', 'student2@novi.nl');
INSERT INTO users (username, password, email) VALUES ('test_student3', 'DummyPassword1!', 'student3@novi.nl');

-- AUTHORITIES

INSERT INTO authorities (username, authority) VALUES ('test_admin1', 'ROLE_ADMIN');
INSERT INTO authorities (username, authority) VALUES ('test_sme1', 'ROLE_SME');
INSERT INTO authorities (username, authority) VALUES ('test_sme2', 'ROLE_SME');
INSERT INTO authorities (username, authority) VALUES ('test_student1', 'ROLE_STUDENT');
INSERT INTO authorities (username, authority) VALUES ('test_student2', 'ROLE_STUDENT');
INSERT INTO authorities (username, authority) VALUES ('test_student3', 'ROLE_STUDENT');


-- CATEGORIES

INSERT INTO categories (name, description) VALUES ('Algemeen', 'Algemene vragen over de opleiding');
INSERT INTO categories (name, description) VALUES ('Rooster', 'Vragen over het rooster');
INSERT INTO categories (name, description) VALUES ('Java', 'Vragen over Java basics en OOP');
INSERT INTO categories (name, description) VALUES ('Spring Boot', 'Vragen over het Spring Boot framework');
INSERT INTO categories (name, description) VALUES ('Databases', 'Vragen over databases en database design');
INSERT INTO categories (name, description) VALUES ('Security', 'Vragen over het beveiligen van je applicatie');
INSERT INTO categories (name, description) VALUES ('HTML', 'Vragen over markup en structuur');
INSERT INTO categories (name, description) VALUES ('CSS', 'Vragen over styling, Flexbox en Grid');
INSERT INTO categories (name, description) VALUES ('React', 'Vragen over hooks, components en state');
INSERT INTO categories (name, description) VALUES ('Git', 'Vragen over commits, branches en merge conflicts');
INSERT INTO categories (name, description) VALUES ('Design', 'Vragen over UI/UX en wireframing');

-- COURSES

INSERT INTO courses (name, description) VALUES ('Full-Stack Bootcamp', 'De complete opleiding inclusief Front-End en Back-End');
INSERT INTO courses (name, description) VALUES ('Back-End Bootcamp', 'Focus op Java, Spring Boot en Databases');
INSERT INTO courses (name, description) VALUES ('Front-End Bootcamp', 'Focus op HTML, CSS en React');


-- TAGS

INSERT INTO tags (name, color_hex) VALUES ('Urgent', '#FF0000');
INSERT INTO tags (name, color_hex) VALUES ('Escaleren', '#0000FF');
INSERT INTO tags (name, color_hex) VALUES ('Nakijken', '#FFA500');
INSERT INTO tags (name, color_hex) VALUES ('Bug', '#800080');
INSERT INTO tags (name, color_hex) VALUES ('Behandelen in de les', '#00FF00');

-- TICKETS

-- Ticket 1 (Java, Back-End Bootcamp)
INSERT INTO tickets (title, description, status, student_username, sme_username, category_id, course_id, created_at)
VALUES ('NullPointerException in Main klasse', 'Mijn applicatie crasht bij het opstarten en ik snap de stacktrace niet. Help!', 'OPEN', 'test_student1', 'test_sme1', 3, 2, CURRENT_TIMESTAMP);

-- Ticket 2 (React, Front-End Bootcamp)
INSERT INTO tickets (title, description, status, student_username, sme_username, category_id, course_id, created_at)
VALUES ('State update reageert traag', 'Als ik op de knop druk, zie ik de verandering pas na een tweede klik.', 'IN_BEHANDELING', 'test_student2', 'test_sme2', 9, 3, CURRENT_TIMESTAMP);

-- Ticket 3 (Spring Boot, Full-Stack Bootcamp)
INSERT INTO tickets (title, description, status, student_username, sme_username, category_id, course_id, created_at)
VALUES ('Dependency injection faalt', 'Krijg een error over een ontbrekende Bean bij het runnen van mijn testen.', 'GEPAUZEERD', 'test_student3', 'test_sme1', 4, 1, CURRENT_TIMESTAMP);

-- Ticket 4 (Rooster, Full-Stack Bootcamp)
INSERT INTO tickets (title, description, status, student_username, sme_username, category_id, course_id, created_at)
VALUES ('Wanneer is de gastles over Security?', 'In de PDF staat maandag, maar in de app staat dinsdag.', 'AFGEROND', 'test_student1', 'test_sme2', 2, 1, CURRENT_TIMESTAMP);

-- Ticket 5 (Git, Back-End Bootcamp)
INSERT INTO tickets (title, description, status, student_username, sme_username, category_id, course_id, created_at)
VALUES ('Enorm merge conflict na git pull', 'Ik durf niks meer aan te raken, bang dat ik code van mijn groepsgenoot weggooi.', 'IN_BEHANDELING', 'test_student3', 'test_sme1', 10, 2, CURRENT_TIMESTAMP);


-- TICKET_TAGS (Koppeltabel)

-- Ticket 1 krijgt tags: Urgent (1)
INSERT INTO ticket_tags (ticket_id, tag_id) VALUES (1, 1);

-- Ticket 2 krijgt tags: Bug (4), Nakijken (3)
INSERT INTO ticket_tags (ticket_id, tag_id) VALUES (2, 4);
INSERT INTO ticket_tags (ticket_id, tag_id) VALUES (2, 3);

-- Ticket 3 krijgt tags: Escaleren (2)
INSERT INTO ticket_tags (ticket_id, tag_id) VALUES (3, 2);

-- Ticket 5 krijgt tags: Urgent (1), Behandelen in de les (5)
INSERT INTO ticket_tags (ticket_id, tag_id) VALUES (5, 1);
INSERT INTO ticket_tags (ticket_id, tag_id) VALUES (5, 5);


-- INTERNAL NOTES

-- Notitie voor Ticket 1
INSERT INTO internal_notes (note_text, ticket_id, sme_username, created_at)
VALUES ('Student is vergeten zijn object te initialiseren met het keyword "new". Ik wacht nog even of hij het zelf ziet na mijn hint.', 1, 'test_sme1', CURRENT_TIMESTAMP);

-- Notities voor Ticket 2
INSERT INTO internal_notes (note_text, ticket_id, sme_username, created_at)
VALUES ('Klassiek React asynchrone state probleem. Heb de documentatie van useEffect doorgestuurd.', 2, 'test_sme2', CURRENT_TIMESTAMP);

-- Notities voor Ticket 3 (Twee SME's die met elkaar overleggen)
INSERT INTO internal_notes (note_text, ticket_id, sme_username, created_at)
VALUES ('Ik kom hier niet uit. Lijkt een conflict in de application.properties.', 3, 'test_sme1', CURRENT_TIMESTAMP);
INSERT INTO internal_notes (note_text, ticket_id, sme_username, created_at)
VALUES ('Gezien! Ik neem deze vanmiddag even van je over, ik heb dit vaker gezien.', 3, 'test_sme2', CURRENT_TIMESTAMP);

-- Notities voor Ticket 5
INSERT INTO internal_notes (note_text, ticket_id, sme_username, created_at)
VALUES ('Meer studenten in deze groep lopen hier tegenaan. Ik ga dit morgen plenair behandelen op het bord.', 5, 'test_sme1', CURRENT_TIMESTAMP);
