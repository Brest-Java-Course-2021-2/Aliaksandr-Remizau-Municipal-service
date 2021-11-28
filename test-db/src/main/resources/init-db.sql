INSERT INTO CLIENT (client_id, client_name) VALUES (1, 'Aleksandrovich Aleksey Iosifovich');
INSERT INTO CLIENT (client_id, client_name) VALUES (2, 'Orlov Petr Ivanovich');
INSERT INTO CLIENT (client_id, client_name) VALUES (3, 'Borodach Michail Ivanovich');


INSERT INTO REPAIR (repair_id, repair_type, address, difficulty_level, preference_date, client_id)
              VALUES(1,'ELECTRIC','MOSKOVSKAYA 263-13','EASY','2021-12-20',1),
                    (2,'FINISHING','MOSKOVSKAYA 263-13','HARD','2021-12-25',1),
                    (3,'FINISHING','MOSKOVSKAYA 250-10','HARD','2021-12-29',2),
                    (4,'PLUMBER','MOSKOVSKAYA 250-10','MEDIUM','2021-12-30',2),
                    (5,'ANOTHER','MOSKOVSKAYA 100-15','EASY','2021-12-30',2);
