package com.database.ProjectDB.ui.components;


import com.database.ProjectDB.Controller;
import com.database.ProjectDB.DQO.EmployeeQueries;
import com.database.ProjectDB.entity.Employee;
import com.database.ProjectDB.tools.AlertType;
import com.database.ProjectDB.tools.WindowComponents;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;

/**
 * Created by seba on 2017-05-16.
 */
public class EmployeeComponent{
    private static Controller controller = Controller.getControllerInstance();
    private static boolean show = true;


    public static void createTable(ObservableList<Employee> lista) {
        TableColumn<Employee, Long> idColumn = new TableColumn("Employee ID");
        idColumn.setCellValueFactory(new PropertyValueFactory("id"));

        TableColumn<Employee, String> firstNameColumn = new TableColumn("First Name");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory("firstName"));

        TableColumn<Employee, String> lastNameColumn = new TableColumn("Last Name");
        lastNameColumn.setCellValueFactory(new PropertyValueFactory("lastName"));

        TableColumn<Employee, Long> phoneNumberColumn = new TableColumn("Phone Number");
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory("phoneNumber"));

        TableColumn<Employee, String> emailColumn = new TableColumn("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory("email"));

        TableColumn<Employee, String> peselColumn = new TableColumn("Pesel");
        peselColumn.setCellValueFactory(new PropertyValueFactory("pesel"));

        TableColumn<Employee, BigDecimal> salaryColumn = new TableColumn("Salary");
        salaryColumn.setCellValueFactory(new PropertyValueFactory("salary"));

        TableColumn<Employee, String> orderIDColumn = new TableColumn("Order ID");
        orderIDColumn.setCellValueFactory(new PropertyValueFactory("collectionOfOrders"));

        TableColumn<Employee, Long> addressIDColumn = new TableColumn("Address ID");
        addressIDColumn.setCellValueFactory(new PropertyValueFactory("address"));

        controller.getEmployeeTable().getColumns().clear();
        controller.getEmployeeTable().setItems(lista);
        controller.getEmployeeTable().getColumns().addAll(idColumn, firstNameColumn, lastNameColumn, phoneNumberColumn,
                emailColumn, peselColumn, salaryColumn, orderIDColumn, addressIDColumn);

    }

    public static void sumbitButtonTools(){
        if(controller.getEmployeeTab().isSelected() && controller.getSelectEmployeeButton().isSelected()){
            ObservableList<Employee> lista = EmployeeQueries.selectEmployeers();
            createTable(lista);
        }
        if (controller.getEmployeeTab().isSelected() && controller.getInsertEmployeeButton().isSelected())
            EmployeeQueries.insertEmployee();
        if (controller.getEmployeeTab().isSelected() && controller.getDeleteEmployeeButton().isSelected())
            EmployeeQueries.deleteEmployee();
        if (controller.getEmployeeTab().isSelected() && controller.getUpdateEmployeeButton().isSelected())
            EmployeeQueries.updateEmployee();
    }

    public static void employeePaneTools() {
        if (controller.getSelectEmployeeButton().isSelected()) controller.getEmployeePane().setDisable(true);
        else controller.getEmployeePane().setDisable(false);
        if (controller.getInsertEmployeeButton().isSelected()) controller.getEmployeeIdInput().setDisable(true);
        else controller.getEmployeeIdInput().setDisable(false);
        if (controller.getDeleteEmployeeButton().isSelected()){
            boolean zmiana = true;
            controller.getEmployeeSalaryInput().setDisable(zmiana);
            controller.getEmployeePeselInput().setDisable(zmiana);
            controller.getEmployeeEmailInput().setDisable(zmiana);
            controller.getEmployeeFirstNameInput().setDisable(zmiana);
            controller.getEmployeePhoneNumberInput().setDisable(zmiana);
            controller.getEmployeeAddresssIDInput().setDisable(zmiana);
            controller.getEmployeeLastNameInput().setDisable(zmiana);
        }
        else
        {
            boolean zmiana = false;
            controller.getEmployeeSalaryInput().setDisable(zmiana);
            controller.getEmployeePeselInput().setDisable(zmiana);
            controller.getEmployeeEmailInput().setDisable(zmiana);
            controller.getEmployeeFirstNameInput().setDisable(zmiana);
            controller.getEmployeePhoneNumberInput().setDisable(zmiana);
            controller.getEmployeeAddresssIDInput().setDisable(zmiana);
            controller.getEmployeeLastNameInput().setDisable(zmiana);
        }
        if (controller.getUpdateEmployeeButton().isSelected() && show) {
            WindowComponents.sendConsoleMessage("The databse will update order by id only fields with data");
            WindowComponents.createAlert(AlertType.INFORMATION, "The databse will update order by id only fields with data");
            show = false;
            WindowComponents.clearInputs();
        }
    }

    public static void injectDataToComponents() {
        if (controller.getEmployeeTable().getSelectionModel().isEmpty());
        else {
            Employee employee = (Employee)controller.getEmployeeTable().getSelectionModel().getSelectedItem();
            controller.getEmployeeIdInput().setText(employee.getId().toString());
            controller.getEmployeeFirstNameInput().setText(employee.getFirstName());
            controller.getEmployeeLastNameInput().setText(employee.getLastName());
            controller.getEmployeePhoneNumberInput().setText(employee.getPhoneNumber().toString());
            controller.getEmployeeEmailInput().setText(employee.getEmail());
            controller.getEmployeePeselInput().setText(employee.getPesel());
            controller.getEmployeeSalaryInput().setText(employee.getSalary().toString());
            controller.getEmployeeAddresssIDInput().setText(employee.getAddress().toString());
        }
    }

    public static void deleteSelected() {
        if (controller.getEmployeeTab().isSelected()) {
            if (controller.getEmployeeTable().getSelectionModel().isEmpty()) ;
            else {
                Employee employee = (Employee) controller.getEmployeeTable().getSelectionModel().getSelectedItem();
                EmployeeQueries.deleteEmployee(employee);
            }
        }
    }
}
