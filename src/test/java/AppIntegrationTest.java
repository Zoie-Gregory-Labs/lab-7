import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

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
}
