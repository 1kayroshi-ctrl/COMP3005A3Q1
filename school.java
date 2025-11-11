import java.sql.*;

public class school {
    private static final String URL = "jdbc:postgresql://localhost:5432/school";
    private static final String USER = "postgres";
    private static final String PASSWORD = "carleotn";

    public static void getAllStudents() {
        String query = "SELECT * FROM students";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                System.out.printf("ID: %d, Name: %s %s, Email: %s, Enrolled: %s%n",
                        rs.getInt("student_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getDate("enrollment_date"));
            }
        } catch (SQLException e) {
            e.printStackTrace(); //debugging
        }
    }

    public static void addStudent(String firstName, String lastName, String email, String enrollmentDate) {
        String query = "INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, email);
            pstmt.setDate(4, Date.valueOf(enrollmentDate));
            pstmt.executeUpdate();
            System.out.println("Student added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();//debugging
        }
    }

    public static void updateStudentEmail(int studentId, String newEmail) {
        String query = "UPDATE students SET email = ? WHERE student_id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, newEmail);
            pstmt.setInt(2, studentId);
            pstmt.executeUpdate();
            System.out.println("Email updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();//debugging
        }
    }

    public static void deleteStudent(int studentId) {
        String query = "DELETE FROM students WHERE student_id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, studentId);
            pstmt.executeUpdate();
            System.out.println("Student deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Example usage
    public static void main(String[] args) {
        getAllStudents();
        addStudent("test4", "Siddiqui", "moinsiddiqui3@cmail.carleton.ca", "2025-11-09");
        updateStudentEmail(1, "test9@email.com");
        deleteStudent(13);
    }
}
