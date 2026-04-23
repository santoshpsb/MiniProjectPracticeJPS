CREATE TABLE Doctors (
    doctor_id INT PRIMARY KEY,
    name VARCHAR(100),
    specialization VARCHAR(100)
);

CREATE TABLE Patients (
    patient_id INT PRIMARY KEY,
    name VARCHAR(100),
    age INT,
    gender VARCHAR(10)
);


CREATE TABLE Appointments (
    appointment_id INT PRIMARY KEY,
    patient_id INT,
    doctor_id INT,
    appointment_date DATE,
    FOREIGN KEY (patient_id) REFERENCES Patients(patient_id),
    FOREIGN KEY (doctor_id) REFERENCES Doctors(doctor_id)
);



CREATE TABLE Treatments (
    treatment_id INT PRIMARY KEY,
    appointment_id INT,
    diagnosis VARCHAR(255),
    cost DECIMAL(10, 2),
    FOREIGN KEY (appointment_id) REFERENCES Appointments(appointment_id)
);