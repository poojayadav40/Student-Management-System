import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

class Student implements Serializable {
    String id, name, course, rollNo;

    public Student(String id, String name, String course, String rollNo) {
        this.id = id;
        this.name = name;
        this.course = course;
        this.rollNo = rollNo;
    }
}

public class StudentManagementSystem extends JFrame {
    private ArrayList<Student> studentList = new ArrayList<>();
    private JTextField txtId, txtName, txtCourse, txtRollNo, txtSearch;
    private JTable table;
    private DefaultTableModel model;
    private TableRowSorter<DefaultTableModel> sorter;
    private final String FILE_NAME = "students_data.txt";

    public StudentManagementSystem() {
        setTitle("Student Management System (Production Ready)");
        setSize(800, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

      
        JPanel topContainer = new JPanel(new BorderLayout(5, 5));

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Student Details"));

        inputPanel.add(new JLabel("Student ID (Numeric):"));
        txtId = new JTextField();
        inputPanel.add(txtId);

        inputPanel.add(new JLabel("Name:"));
        txtName = new JTextField();
        inputPanel.add(txtName);

        inputPanel.add(new JLabel("Course:"));
        txtCourse = new JTextField();
        inputPanel.add(txtCourse);

        inputPanel.add(new JLabel("Roll No (Numeric):"));
        txtRollNo = new JTextField();
        inputPanel.add(txtRollNo);

        JPanel searchPanel = new JPanel(new BorderLayout(5, 5));
        searchPanel.setBorder(BorderFactory.createTitledBorder("Live Search"));
        searchPanel.add(new JLabel(" Search Student: "), BorderLayout.WEST);
        txtSearch = new JTextField();
        searchPanel.add(txtSearch, BorderLayout.CENTER);

        topContainer.add(inputPanel, BorderLayout.NORTH);
        topContainer.add(searchPanel, BorderLayout.SOUTH);

        add(topContainer, BorderLayout.NORTH);

      
        String[] columns = {"ID", "Name", "Course", "Roll No"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        add(new JScrollPane(table), BorderLayout.CENTER);

      
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton btnAdd = new JButton("Add Student");
        JButton btnDelete = new JButton("Delete Selected");
        JButton btnClear = new JButton("Clear Fields");
        JButton btnExport = new JButton("Export to CSV");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnExport);

        add(buttonPanel, BorderLayout.SOUTH);

        
        btnAdd.addActionListener(e -> addStudent());
        btnDelete.addActionListener(e -> deleteStudent());
        btnClear.addActionListener(e -> clearFields());
        btnExport.addActionListener(e -> exportToCSV());

      
        txtSearch.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filter(); }

            private void filter() {
                String text = txtSearch.getText().trim();
                if (text.length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });

        
        loadDataFromFile();
    }

    private void addStudent() {
        String id = txtId.getText().trim();
        String name = txtName.getText().trim();
        String course = txtCourse.getText().trim();
        String rollNo = txtRollNo.getText().trim();

        // 1. Validation: Empty Check
        if (id.isEmpty() || name.isEmpty() || course.isEmpty() || rollNo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 2. Validation: Numeric Check for ID & Roll No
        if (!id.matches("\\d+") || !rollNo.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Student ID and Roll No must contain digits only!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 3. Validation: Duplicate ID Check
        for (Student s : studentList) {
            if (s.id.equals(id)) {
                JOptionPane.showMessageDialog(this, "Student ID already exists!", "Duplicate Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        Student student = new Student(id, name, course, rollNo);
        studentList.add(student);
        model.addRow(new Object[]{id, name, course, rollNo});
        saveDataToFile();
        clearFields();
        JOptionPane.showMessageDialog(this, "Student Added & Saved Successfully!");
    }

    private void deleteStudent() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int modelRow = table.convertRowIndexToModel(selectedRow);
            studentList.remove(modelRow);
            model.removeRow(modelRow);
            saveDataToFile();
            JOptionPane.showMessageDialog(this, "Student Deleted Successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtCourse.setText("");
        txtRollNo.setText("");
        txtSearch.setText("");
    }

    // --- CSV Export Feature ---
    private void exportToCSV() {
        if (studentList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No data available to export!", "Export Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter("Students_Report.csv"))) {
            writer.println("ID,Name,Course,Roll No");
            for (Student s : studentList) {
                writer.println(s.id + "," + s.name + "," + s.course + "," + s.rollNo);
            }
            JOptionPane.showMessageDialog(this, "Data exported successfully to 'Students_Report.csv'!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error exporting data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // --- File Persistence: Save & Load ---
    private void saveDataToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Student s : studentList) {
                bw.write(s.id + "," + s.name + "," + s.course + "," + s.rollNo);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving data: " + e.getMessage());
        }
    }

    private void loadDataFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    Student s = new Student(parts[0], parts[1], parts[2], parts[3]);
                    studentList.add(s);
                    model.addRow(new Object[]{parts[0], parts[1], parts[2], parts[3]});
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading data: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentManagementSystem().setVisible(true));
    }
}