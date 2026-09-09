
CREATE TABLE Students (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    course TEXT,
    marks INTEGER
);


INSERT INTO Students (name, course, marks) VALUES ('Pooja', 'CSE', 92);
INSERT INTO Students (name, course, marks) VALUES ('Aarav', 'CSE', 85);
INSERT INTO Students (name, course, marks) VALUES ('Riya', 'ECE', 78);


SELECT * FROM Students;

UPDATE Students SET marks = 95 WHERE name = 'Pooja';


DELETE FROM Students WHERE id = 3;


SELECT course, COUNT(*) AS total_students, AVG(marks) AS avg_marks 
FROM Students 
GROUP BY course;