package com.database.ProjectDB.DQO;

import com.database.ProjectDB.Controller;
import com.database.ProjectDB.DAO.ServiceDAO;
import com.database.ProjectDB.entity.Address;
import com.database.ProjectDB.entity.Employee;
import com.database.ProjectDB.exceptions.client.InsertClientException;
import com.database.ProjectDB.exceptions.employee.DeleteEmployeeException;
import com.database.ProjectDB.exceptions.employee.InsertEmployeeException;
import com.database.ProjectDB.exceptions.employee.UpdateEmployeeException;
import com.database.ProjectDB.tools.AlertType;
import com.database.ProjectDB.tools.WindowComponents;
import com.database.ProjectDB.ui.components.EmployeeComponent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.JDBCException;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.sql.Insert;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by seba on 2017-05-16.
 */

public class EmployeeQueries {
    private static Controller controller = Controller.getControllerInstance();
    private static ServiceDAO service = ServiceDAO.getInstance();

    public static ObservableList<Employee> selectEmployeers() {
        ObservableList<Employee> employeeList = FXCollections.observableArrayList();
        try {
            employeeList.clear();
            EntityManager entityManager = service.beginTransaction();
            List<Employee> list = new ArrayList();
            list = entityManager.createQuery("from Employee", Employee.class).getResultList();
            list.forEach(employee ->
                    employeeList.add(employee)
            );
            service.commitTransaction();
        } catch (Exception exception) {
            WindowComponents.createAlert(AlertType.ERROR, exception.getMessage());
        }
        return employeeList;
    }

    public static void insertEmployee() {
        try {
            EntityManager entityManager = service.beginTransaction();
            Employee employee = new Employee();
            if (controller.getEmployeeFirstNameInput().getText().isEmpty())
                throw new InsertEmployeeException("Please insert first name of employee");
            else employee.setFirstName(controller.getEmployeeFirstNameInput().getText());

            if (controller.getEmployeeLastNameInput().getText().isEmpty())
                throw new InsertEmployeeException("Please insert last name of employee");
            else employee.setLastName(controller.getEmployeeLastNameInput().getText());

            employee.setPhoneNumber(Long.parseLong(controller.getEmployeePhoneNumberInput().getText()));

            if (controller.getEmployeeEmailInput().getText().isEmpty())
                throw new InsertEmployeeException("Please insert email address");
            else employee.setEmail(controller.getEmployeeEmailInput().getText());

            employee.setPesel(controller.getEmployeePeselInput().getText());
            if (controller.getEmployeeSalaryInput().getText().isEmpty())
                throw new InsertEmployeeException("Please insert salary of employee");
            else employee.setSalary(controller.getEmployeeSalaryInput().getText());

            if (!(controller.getEmployeeAddresssIDInput().getText().isEmpty())) {
                Long addressId = Long.parseLong(controller.getEmployeeAddresssIDInput().getText());
                Address address = entityManager.createQuery("from Address a where a.id = " + addressId, Address.class).getSingleResult();
                employee.setAddress(address);
                address.getAddressesOfEmployers().add(employee);

                entityManager.persist(employee);
                service.commitTransaction();
                EmployeeComponent.createTable(selectEmployeers());

                WindowComponents.createAlert(AlertType.INFORMATION, "Employee has beed created.");
                WindowComponents.sendConsoleMessage("SUCCESS: Employee has beed created");
            } else {
                throw new InsertEmployeeException("Please insert Address ID");
            }

        } catch (Exception exception) {
            WindowComponents.createAlert(AlertType.ERROR, exception.getMessage());
        }
    }

    public static void deleteEmployee() {
        try {
            EntityManager entityManager = service.beginTransaction();
            String employeeId = controller.getEmployeeIdInput().getText();
            if (!employeeId.isEmpty()) {
                String query = "from Employee e where e.id = " + employeeId;
                Employee employee = entityManager.createQuery(query, Employee.class).getSingleResult();
                Optional<Employee> optional = Optional.ofNullable(employee);
                if (optional.isPresent()) {
                    Address address = entityManager.createQuery("from Address a where a.id = " + employee.getAddress(), Address.class).getSingleResult();
                    address.getAddressesOfEmployers().remove(employee);
                    if (!employee.getCollectionOfOrders().isEmpty())
                        throw new DeleteEmployeeException("This object is connected with some orders");
                    entityManager.remove(employee);
                }
                service.commitTransaction();
                EmployeeComponent.createTable(EmployeeQueries.selectEmployeers());
                WindowComponents.createAlert(AlertType.INFORMATION, "Employeer had beed deleted.");
                WindowComponents.sendConsoleMessage("SUCCESS: Employeer has beed deleted.");
            } else
                throw new DeleteEmployeeException("Please insert Employee ID");
        } catch (Exception exception) {
            WindowComponents.sendConsoleMessage(exception.getMessage());
            WindowComponents.createAlert(AlertType.ERROR, exception.getMessage());
        }
    }

    public static void deleteEmployee(Employee employee) {
        try {
            EntityManager entityManager = service.beginTransaction();
            Optional<Employee> optional = Optional.ofNullable(employee);
            if (optional.isPresent()) {
                Address address = entityManager.createQuery("from Address a where a.id = " + employee.getAddress(), Address.class).getSingleResult();
                address.getAddressesOfEmployers().remove(employee);
                if (!employee.getCollectionOfOrders().isEmpty())
                    throw new DeleteEmployeeException("This object is connected with some orders");
                entityManager.remove(employee);
                service.commitTransaction();
                EmployeeComponent.createTable(EmployeeQueries.selectEmployeers());
                WindowComponents.createAlert(AlertType.INFORMATION, "Employeer had beed deleted.");
                WindowComponents.sendConsoleMessage("SUCCESS: Employeer has beed deleted.");
            }
        } catch (Exception exception) {
            WindowComponents.sendConsoleMessage(exception.getLocalizedMessage());
            WindowComponents.createAlert(AlertType.ERROR, exception.getLocalizedMessage());
        }
    }

    public static void updateEmployee() {
        try {
            String employeeId = controller.getEmployeeIdInput().getText();
            if (employeeId.isEmpty()) {
                throw new UpdateEmployeeException("Please insert Employee ID");
            } else {
                EntityManager entityManager = service.beginTransaction();
                Employee employee = entityManager.createQuery("from Employee e where e.id = " + employeeId, Employee.class).getSingleResult();

                if (!controller.getEmployeeEmailInput().getText().isEmpty())
                    employee.setEmail(controller.getEmployeeEmailInput().getText());

                if (!controller.getEmployeePeselInput().getText().isEmpty())
                    employee.setPesel(controller.getEmployeePeselInput().getText());

                if (!controller.getEmployeeSalaryInput().getText().isEmpty())
                    employee.setSalary(controller.getEmployeeSalaryInput().getText());

                if (!controller.getEmployeeFirstNameInput().getText().isEmpty())
                    employee.setFirstName(controller.getEmployeeFirstNameInput().getText());

                if (!controller.getEmployeeLastNameInput().getText().isEmpty())
                    employee.setLastName(controller.getEmployeeLastNameInput().getText());

                if (!controller.getEmployeePhoneNumberInput().getText().isEmpty())
                    employee.setPhoneNumber(Long.parseLong(controller.getEmployeePhoneNumberInput().getText()));

                if (!controller.getEmployeeAddresssIDInput().getText().isEmpty()) {
                    Long addressId = Long.parseLong(controller.getEmployeeAddresssIDInput().getText());
                    Address address = entityManager.createQuery("from Address a where a.id = " + addressId, Address.class).getSingleResult();
                    employee.getAddressObject().getAddressesOfEmployers().remove(employee);
                    employee.setAddress(address);
                    address.getAddressesOfEmployers().add(employee);
                }
                service.commitTransaction();
                EmployeeComponent.createTable(selectEmployeers());
                WindowComponents.createAlert(AlertType.INFORMATION, "Employee has beed updated");
                WindowComponents.sendConsoleMessage("SUCCESS: Employee has beed updated");

            }
        } catch (Exception exception) {
            WindowComponents.sendConsoleMessage(exception.getMessage());
            WindowComponents.createAlert(AlertType.ERROR, exception.getMessage());
        }
    }
}
