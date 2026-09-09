# Student Management System

A desktop application built using **Java Swing** for the GUI and **SQLite** for relational database operations. It features real-time search, robust input validation, local file storage, and CSV report export functionality.

---

## Key Features

* **Full CRUD Operations:** Add, view, and delete student records dynamically.
* **Input Validation:** Restricts input fields to numeric values and prevents duplicate Student IDs.
* **Real-time Live Search:** Dynamically filters the records table as you type in the search bar.
* **Data Persistence:** Automatically saves records to `students_data.txt` and reloads them when the application starts.
* **CSV Report Export:** Generates a structured `Students_Report.csv` file for external data reporting.
* **Standalone SQL Script:** Includes `student_db.sql` containing schema creation, sample inserts, update/delete queries, and group-by aggregation tasks.

---

## Application Screenshots

### 1. Numeric Input Validation
Restricts ID and Roll Number fields to numeric digits only.

![Numeric Validation Error](Screenshot%20%28268%29)

---

### 2. Duplicate ID Validation
Prevents adding duplicate records to maintain database uniqueness.

![Duplicate ID Validation](Screenshot%20%28269%29)

---

### 3. Live Search & Dynamic Filtering
Filters records instantly based on text entered in the search bar.

![Live Search Feature]()

---

### 4. CSV Data Export Confirmation
Exports structured student records directly to a `.csv` report file.

![CSV Export Confirmation]()

---

## Tech Stack

* **Language:** Java
* **UI Framework:** Java Swing (`JFrame`, `JTable`, `TableRowSorter`)
* **Database:** SQLite
* **IDE / Editor:** Visual Studio Code

---

## How to Run

### 1. Running the Java Application

Open your terminal or command prompt in the project root directory and execute:

```bash
javac StudentManagementSystem.java
java StudentManagementSystem