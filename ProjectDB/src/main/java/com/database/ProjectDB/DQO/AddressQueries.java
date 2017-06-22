package com.database.ProjectDB.DQO;

import com.database.ProjectDB.Controller;
import com.database.ProjectDB.DAO.ServiceDAO;
import com.database.ProjectDB.entity.Address;
import com.database.ProjectDB.entity.Client;
import com.database.ProjectDB.entity.Employee;
import com.database.ProjectDB.exceptions.address.DeleteAddressException;
import com.database.ProjectDB.exceptions.address.InsertAddressException;
import com.database.ProjectDB.exceptions.address.UpdateAddressException;
import com.database.ProjectDB.tools.AlertType;
import com.database.ProjectDB.tools.WindowComponents;
import com.database.ProjectDB.ui.components.AddressComponent;
import com.database.ProjectDB.ui.components.ClientComponent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * Created by seba on 2017-05-17.
 */
public class AddressQueries {
    private static Controller controller = Controller.getControllerInstance();
    private static ServiceDAO service = ServiceDAO.getInstance();

    public static ObservableList<Address> selectAddresses() {
        ObservableList<Address> addressList = FXCollections.observableArrayList();
        try {
            addressList.clear();
            EntityManager entityManager = service.beginTransaction();
            List<Address> list = new ArrayList();
            list = entityManager.createQuery("from Address", Address.class).getResultList();
            list.forEach(address ->
                    addressList.add(address)
            );
            service.commitTransaction();
        } catch (Exception exception) {
            WindowComponents.createAlert(AlertType.ERROR, exception.getMessage());
        }
        return addressList;
    }

    public static void insertAddress() {
        try {
            EntityManager entityManager = service.beginTransaction();
            Address address = new Address();
            if (controller.getAddressCityInput().getText().isEmpty())
                throw new InsertAddressException("Please insert name of city");
            else address.setCity(controller.getAddressCityInput().getText());

            address.setStreet(controller.getAddressStreetInput().getText());

            if (controller.getAddressHomeNumberInput().getText().isEmpty())
                throw new InsertAddressException("Plase insert home number");
            else address.setHomeNumber(controller.getAddressHomeNumberInput().getText());

            if (controller.getAddressPinCodeInput().getText().isEmpty())
                throw new InsertAddressException("Please insert pin code");

            else address.setPinCode(controller.getAddressPinCodeInput().getText());
            if (!(controller.getAddressClientIDInput().getText().isEmpty())) {
                Long clientId = Long.parseLong(controller.getAddressClientIDInput().getText());
                Client client = entityManager.createQuery("from Client c where c.id = " + clientId, Client.class).getSingleResult();
                client.getAddressObject().getAddressesOfClients().remove(client);
                client.setAddress(address);
                address.getAddressesOfClients().add(client);
                entityManager.persist(address);
            }
            if (!(controller.getAddressEmployeeIDInput().getText().isEmpty())) {
                Long employeeId = Long.parseLong(controller.getAddressEmployeeIDInput().getText());
                Employee employee = entityManager.createQuery("from Employee e where e.id = " + employeeId, Employee.class).getSingleResult();
                employee.getAddressObject().getAddressesOfEmployers().remove(employee);
                employee.setAddress(address);
                address.getAddressesOfEmployers().add(employee);
                entityManager.persist(address);
            }

                entityManager.persist(address);
                service.commitTransaction();
                AddressComponent.createTable(selectAddresses());
                WindowComponents.createAlert(AlertType.INFORMATION, "Address has beed created.");
                WindowComponents.sendConsoleMessage("SUCCESS: Address has beed created");

        }
        catch (Exception exception) {
            WindowComponents.createAlert(AlertType.ERROR, exception.getMessage());
        }
    }

    public static void deleteAddress() {
        try {
            EntityManager entityManager = service.beginTransaction();
            String addressId = controller.getAddressIDInput().getText();
            if (!addressId.isEmpty()) {
                String query = "from Address a where a.id = " + Long.parseLong(addressId);
                Address address = entityManager.createQuery(query, Address.class).getSingleResult();
                Optional<Address> optional = Optional.ofNullable(address);
                if (optional.isPresent()) {
                    if (!address.getAddressesOfEmployers().isEmpty())
                        throw new DeleteAddressException("This object is connected with some employee");
                    if (!address.getAddressesOfClients().isEmpty())
                        throw new DeleteAddressException("This object is connected with some client");
                    entityManager.remove(address);
                    service.commitTransaction();
                    AddressComponent.createTable(selectAddresses());
                    WindowComponents.createAlert(AlertType.INFORMATION, "Address had beed deleted.");
                    WindowComponents.sendConsoleMessage("SUCCESS: Address has beed deleted.");
                }
            }
            else
                throw new DeleteAddressException("Please insert Address ID");
        } catch (Exception exception) {
            WindowComponents.sendConsoleMessage(exception.getMessage());
            WindowComponents.createAlert(AlertType.ERROR, exception.getMessage());
        }
    }

    public static void deleteAddress(Address address) {
        try {
            EntityManager entityManager = service.beginTransaction();
            Optional<Address> optional = Optional.ofNullable(address);
            if (optional.isPresent()) {
                if (!address.getAddressesOfEmployers().isEmpty())
                    throw new DeleteAddressException("This object is connected with some employee");
                if (!address.getAddressesOfClients().isEmpty())
                    throw new DeleteAddressException("This object is connected with some client");
                entityManager.remove(address);
                service.commitTransaction();
                AddressComponent.createTable(selectAddresses());
                WindowComponents.createAlert(AlertType.INFORMATION, "Address had beed deleted.");
                WindowComponents.sendConsoleMessage("SUCCESS: Address has beed deleted.");
            }
        } catch (RollbackException exception){
            WindowComponents.sendConsoleMessage(exception.getMessage());
            WindowComponents.createAlert(AlertType.ERROR, exception.getMessage());
        }
        catch (Exception exception) {
            WindowComponents.sendConsoleMessage(exception.getMessage());
            WindowComponents.createAlert(AlertType.ERROR, exception.getMessage());
        }
    }

    public static void updateAddress() {
        try {
            String addressId = controller.getAddressIDInput().getText();
            if (addressId.isEmpty()) {
                throw new UpdateAddressException("Please insert Address ID");
            }
            else {
                EntityManager entityManager = service.beginTransaction();
                Address address = entityManager.createQuery("from Address a where a.id = " + Long.parseLong(addressId), Address.class).getSingleResult();

                if (!controller.getAddressStreetInput().getText().isEmpty())
                    address.setStreet(controller.getAddressStreetInput().getText());

                if (!controller.getAddressCityInput().getText().isEmpty())
                    address.setCity(controller.getAddressCityInput().getText());

                if (!controller.getAddressHomeNumberInput().getText().isEmpty())
                    address.setHomeNumber(controller.getAddressHomeNumberInput().getText());

                if (!controller.getAddressPinCodeInput().getText().isEmpty())
                    address.setPinCode(controller.getAddressPinCodeInput().getText());

                if (!controller.getAddressClientIDInput().getText().isEmpty()){
                    Long clientId = Long.parseLong(controller.getAddressClientIDInput().getText());
                    Client client = entityManager.createQuery("from Client c where c.id = " + clientId, Client.class).getSingleResult();
                    client.getAddressObject().getAddressesOfClients().remove(client);
                    client.setAddress(address);
                    address.getAddressesOfClients().add(client);
                }
                if (!controller.getAddressEmployeeIDInput().getText().isEmpty()) {
                    Long employeeId = Long.parseLong(controller.getAddressEmployeeIDInput().getText());
                    Employee employee = entityManager.createQuery("from Employee e where e.id = " + employeeId, Employee.class).getSingleResult();
                    employee.getAddressObject().getAddressesOfEmployers().remove(employee);
                    address.getAddressesOfEmployers().add(employee);
                    employee.setAddress(address);
                }

                service.commitTransaction();
                AddressComponent.createTable(selectAddresses());
                WindowComponents.createAlert(AlertType.INFORMATION, "Address has beed updated");
                WindowComponents.sendConsoleMessage("SUCCESS: Address has beed updated");
            }
        }
        catch (Exception exception){
            WindowComponents.sendConsoleMessage(exception.getMessage());
            WindowComponents.createAlert(AlertType.ERROR, exception.getMessage());
        }
    }
}
