import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.*;

class AppIntegrationTest {

    private static EmployeeDAO employeeDAO;

    @BeforeAll
    static void setUp() {
        employeeDAO = new EmployeeDAO();
    }

    @Test
    void testGetEmployeeById() {
        Employee employee = employeeDAO.getEmployee(255530);

        assertNotNull(employee);
        assertEquals(255530, employee.getEmpNo());
        assertEquals("Ronghao", employee.getFirstName());
        assertEquals("Garigliano", employee.getLastName());
    }

    @Test
    void testGetEmployeeNotFound() {
        assertNull(employeeDAO.getEmployee(9999999));
    }

    @Test
    void testGetAllSalaries() {
        ArrayList<Employee> employees = employeeDAO.getAllSalaries();

        assertNotNull(employees);
        assertFalse(employees.isEmpty());
    }

    @Test
    void testGetDepartment() {
        Department department = employeeDAO.getDepartment("Development");

        assertNotNull(department);
        assertEquals("Development", department.dept_name);
    }

    @Test
    void testGetSalariesByDepartment() {
        Department department = employeeDAO.getDepartment("Development");
        assertNotNull(department);

        ArrayList<Employee> employees =
                employeeDAO.getSalariesByDepartment(department);

        assertNotNull(employees);
        assertFalse(employees.isEmpty());
    }

    @Test
    void testGetEmployeeByName() {
        Employee employee =
                employeeDAO.getEmployee("Ronghao", "Garigliano");

        assertNotNull(employee);
        assertEquals("Ronghao", employee.getFirstName());
        assertEquals("Garigliano", employee.getLastName());
    }

    @Test
    void testAddEmployee() throws Exception {
        int empNo = 9999998;
        Employee employee = new Employee(empNo, "Lab", "Seven", 50000);

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement delete = connection.prepareStatement(
                 "DELETE FROM employees WHERE emp_no = ?")) {
            delete.setInt(1, empNo);
            delete.executeUpdate();
        }

        employeeDAO.addEmployee(employee);

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                 "SELECT first_name, last_name FROM employees WHERE emp_no = ?")) {
            statement.setInt(1, empNo);
            try (ResultSet result = statement.executeQuery()) {
                assertTrue(result.next());
                assertEquals("Lab", result.getString("first_name"));
                assertEquals("Seven", result.getString("last_name"));
            }
        }
    }

}
