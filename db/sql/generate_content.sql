INSERT INTO PATIENT (FIRST_NAME, LAST_NAME, PATRONYMIC, PHONE_NUMBER)
VALUES ('Караваев', 'Игорь', 'Петрович', '+7(961)835-17-49');
INSERT INTO PATIENT (FIRST_NAME, LAST_NAME, PATRONYMIC, PHONE_NUMBER)
VALUES ('Васнецов', 'Всеволод', 'Артемьевич', '+7(902)198-60-62');
INSERT INTO PATIENT (FIRST_NAME, LAST_NAME, PATRONYMIC, PHONE_NUMBER)
VALUES ('Жилина', 'Светлана', 'Владимировна', '+7(909)353-70-46');
INSERT INTO PATIENT (FIRST_NAME, LAST_NAME, PATRONYMIC, PHONE_NUMBER)
VALUES ('Волошина', 'Алёна', 'Игоревна', '+7(927)668-14-40');
INSERT INTO PATIENT (FIRST_NAME, LAST_NAME, PATRONYMIC, PHONE_NUMBER)
VALUES ('Фирсов', 'Евгений', 'Михайлович', '+7(046)787-84-23');
INSERT INTO PATIENT (FIRST_NAME, LAST_NAME, PATRONYMIC, PHONE_NUMBER)
VALUES ('Трофимов', 'Лев', 'Васильевич', '+7(872)256-42-68');
INSERT INTO PATIENT (FIRST_NAME, LAST_NAME, PATRONYMIC, PHONE_NUMBER)
VALUES ('Белоусова', 'Валерия', 'Евгеньевна', '+7(846)291-75-07');
INSERT INTO PATIENT (FIRST_NAME, LAST_NAME, PATRONYMIC, PHONE_NUMBER)
VALUES ('Ермаков', 'Александр', 'Алексеевич', '+7(987)883-16-96');
INSERT INTO PATIENT (FIRST_NAME, LAST_NAME, PATRONYMIC, PHONE_NUMBER)
VALUES ('Ермаков', 'Святослав', 'Александрович', '+7(987)536-05-70');


INSERT INTO DOCTOR (FIRST_NAME, LAST_NAME, PATRONYMIC, SPECIALIZATION)
VALUES ('Беляева', 'Анна', 'Андреевна', 'Терапевт');
INSERT INTO DOCTOR (FIRST_NAME, LAST_NAME, PATRONYMIC, SPECIALIZATION)
VALUES ('Абрамов', 'Вячеслав', 'Андреевич', 'Стоматолог');
INSERT INTO DOCTOR (FIRST_NAME, LAST_NAME, PATRONYMIC, SPECIALIZATION)
VALUES ('Косарев', 'Николай', 'Егорович', 'Гинеколог');
INSERT INTO DOCTOR (FIRST_NAME, LAST_NAME, PATRONYMIC, SPECIALIZATION)
VALUES ('Кулешова', 'Алина', 'Тимуровна', 'Невролог');
INSERT INTO DOCTOR (FIRST_NAME, LAST_NAME, PATRONYMIC, SPECIALIZATION)
VALUES ('Медведева', 'Елена', 'Богдановна', 'Окулист');
INSERT INTO DOCTOR (FIRST_NAME, LAST_NAME, PATRONYMIC, SPECIALIZATION)
VALUES ('Новиков', 'Григорий', 'Никитич', 'Психиатр');

INSERT INTO PRESCRIPTION (DESCRIPTION, PATIENT_ID, DOCTOR_ID, DATE_CREAT, VALIDITY_PERIOD, PRIORITY)
VALUES ('Цистамин', '0', '0', '2020-07-31', '7', 'Нормальный');
INSERT INTO PRESCRIPTION (DESCRIPTION, PATIENT_ID, DOCTOR_ID, DATE_CREAT, VALIDITY_PERIOD, PRIORITY)
VALUES ('Холина салицилат', '1', '1', '2020-07-15', '30', 'Нормальный');
INSERT INTO PRESCRIPTION (DESCRIPTION, PATIENT_ID, DOCTOR_ID, DATE_CREAT, VALIDITY_PERIOD, PRIORITY)
VALUES ('Хинидин', '2', '2', '2020-05-30', '180', 'Нормальный');
INSERT INTO PRESCRIPTION (DESCRIPTION, PATIENT_ID, DOCTOR_ID, DATE_CREAT, VALIDITY_PERIOD, PRIORITY)
VALUES ('Празозин', '3', '3', '2020-07-30', '20', 'Statim');
INSERT INTO PRESCRIPTION (DESCRIPTION, PATIENT_ID, DOCTOR_ID, DATE_CREAT, VALIDITY_PERIOD, PRIORITY)
VALUES ('Аллоксим', '4', '4', '2020-07-28', '15', 'Cito');
INSERT INTO PRESCRIPTION (DESCRIPTION, PATIENT_ID, DOCTOR_ID, DATE_CREAT, VALIDITY_PERIOD, PRIORITY)
VALUES ('Агомелатин', '5', '5', '2020-08-01', '30', 'Cito');
INSERT INTO PRESCRIPTION (DESCRIPTION, PATIENT_ID, DOCTOR_ID, DATE_CREAT, VALIDITY_PERIOD, PRIORITY)
VALUES ('Фтивазид', '6', '1', '2020-08-01', '30', 'Нормальный');
INSERT INTO PRESCRIPTION (DESCRIPTION, PATIENT_ID, DOCTOR_ID, DATE_CREAT, VALIDITY_PERIOD, PRIORITY)
VALUES ('Цефаксон', '7', '0', '2020-08-01', '30', 'Нормальный');
INSERT INTO PRESCRIPTION (DESCRIPTION, PATIENT_ID, DOCTOR_ID, DATE_CREAT, VALIDITY_PERIOD, PRIORITY)
VALUES ('Цефелим', '8', '3', '2020-08-01', '30', 'Statim');
INSERT INTO PRESCRIPTION (DESCRIPTION, PATIENT_ID, DOCTOR_ID, DATE_CREAT, VALIDITY_PERIOD, PRIORITY)
VALUES ('Фунзол', '4', '5', '2020-08-01', '30', 'Cito');
INSERT INTO PRESCRIPTION (DESCRIPTION, PATIENT_ID, DOCTOR_ID, DATE_CREAT, VALIDITY_PERIOD, PRIORITY)
VALUES ('Эмбермин', '8', '4', '2020-08-01', '30', 'Нормальный');