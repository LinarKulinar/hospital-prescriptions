CREATE TABLE PATIENT
(
    patient_id   BIGINT IDENTITY PRIMARY KEY,
    last_name    VARCHAR(40) DEFAULT NULL,
    first_name   VARCHAR(40) DEFAULT NULL,
    patronymic   VARCHAR(40) DEFAULT NULL,
    phone_number VARCHAR(40) DEFAULT NULL
);

CREATE TABLE DOCTOR
(
    doctor_id      BIGINT IDENTITY PRIMARY KEY,
    last_name      VARCHAR(40) DEFAULT NULL,
    first_name     VARCHAR(40) DEFAULT NULL,
    patronymic     VARCHAR(40) DEFAULT NULL,
    specialization VARCHAR(40) DEFAULT NULL
);

CREATE TABLE PRESCRIPTION
(
    prescription_id BIGINT IDENTITY PRIMARY KEY,
    description     VARCHAR(1000),
    patient_id      BIGINT,
    doctor_id       BIGINT,
    date_creat      DATE CHECK (date_creat <= current_date),
    validity_period BIGINT,
    priority        VARCHAR(40) CHECK (priority IN ('Нормальный', 'Cito', 'Statim')),
    FOREIGN KEY (patient_id) REFERENCES PATIENT (patient_id),
    FOREIGN KEY (doctor_id) REFERENCES DOCTOR (doctor_id)
);