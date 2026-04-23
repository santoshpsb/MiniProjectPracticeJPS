-- Most COnsulted doctor
SELECT 
    d.name AS 'Doctor Name', 
    d.specialization AS 'Department', 
    COUNT(a.appointment_id) AS 'Total Consultations'
FROM Doctors d
JOIN Appointments a ON d.doctor_id = a.doctor_id
GROUP BY d.doctor_id
ORDER BY COUNT(a.appointment_id) DESC;



-- most common diseses
SELECT 
    diagnosis AS 'Medical Condition', 
    COUNT(treatment_id) AS 'Total Cases'
FROM Treatments
GROUP BY diagnosis
ORDER BY COUNT(treatment_id) DESC;





-- Patient Frequency
SELECT 
    p.name AS 'Patient Name', 
    p.age AS 'Age', 
    COUNT(a.appointment_id) AS 'Number of VIsits'
FROM Patients p
JOIN Appointments a ON p.patient_id = a.patient_id
GROUP BY p.patient_id
ORDER BY COUNT(a.appointment_id) DESC;




 



-- Revenue of Month
SELECT 
    MONTHNAME(a.appointment_date) AS 'Month',
    SUM(t.cost) AS 'Revenue of Month'
FROM Appointments a
JOIN Treatments t ON a.appointment_id=t.appointment_id
GROUP BY MONTHNAME(a.appointment_date);



-- Doctor Revenue
SELECT
    d.doctor_id AS 'Doctor ID',
    d.name AS 'Doctor Name',
    d.specialization AS 'Specialization',
    r.total_revenue AS ' Revenue '
FROM Doctors d
LEFT JOIN (
    SELECT a.doctor_id, SUM(t.cost) AS total_revenue 
    FROM Appointments a 
    JOIN Treatments t ON a.appointment_id = t.appointment_id 
    GROUP BY a.doctor_id
) r 
ON d.doctor_id = r.doctor_id
ORDER BY r.total_revenue DESC;