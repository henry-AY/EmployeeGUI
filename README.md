# EmployeeGUI

**EmployeeGUI** is a Java-based desktop application for managing and tracking employee information in a simplified and user-friendly interface.

## Purpose

The goal of this project is to provide a basic GUI for:
- Storing employee data (name, SSN, pronouns, age, salary, department, years of experience).
- Validating inputs using regex (especially for formatted fields like SSN).
- Throwing errors with Alert objects and GUI popups.
- Sorting and viewing employee data.
- Persisting data to and loading from a `.dat` file.

This is ideal for small organizations or as a project to practice Java GUI programming with JavaFX, file I/O, and data validation.

---

## Features

- **Add Employee**: Input fields to add a new employee's information.
- **Sort Employees**: Sort by Name, ID, or Salary.
- **Save Data**: Save employee records to a `.dat` file.
- **Load Data**: View saved employee data in a structured list.
- **Input Validation**:
  - SSN format validation (e.g., `111-11-1111`) using **RegEx**.
  - Age, salary, and years must be numeric and within sensible limits.

### Data Entry Interface
<img width="404" alt="Screenshot 2025-05-15 at 1 03 07 PM" src="https://github.com/user-attachments/assets/a3410c29-65ec-4433-81d5-79dd33b06604" />

### Data View Interface
<img width="405" alt="Screenshot 2025-05-15 at 1 03 38 PM" src="https://github.com/user-attachments/assets/6bca56f8-252f-4666-8441-d91038ba23e8" />

---

## Project Tech Stack
- **Language**: Java
- **GUI**: JavaFX
- **Persistence**: Java Serialization (`.dat` file)
- **Validation**: Regular Expressions (Regex) and Java Alerts

---

## Getting Started

### Prerequisites
- Java 8 or later
- IDE like IntelliJ IDEA or Eclipse (or use the command line)

### Running the App

1. Clone this repository:
```bash
git clone https://github.com/henry-AY/EmployeeGUI.git
cd EmployeeGUI
```

2. Compile and run
```bash
javac EmployeeGUI.java
java EmployeeGUI
```

3. Start adding employees, sorting them, and saving data!

## License

This project is open source and available under the [MIT License](https://opensource.org/license/mit)
