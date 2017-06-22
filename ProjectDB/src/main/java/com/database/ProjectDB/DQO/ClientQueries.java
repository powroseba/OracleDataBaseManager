package com.database.ProjectDB.DQO;

import com.database.ProjectDB.Controller;
import com.database.ProjectDB.DAO.ServiceDAO;
import com.database.ProjectDB.entity.Address;
import com.database.ProjectDB.entity.Client;
import com.database.ProjectDB.exceptions.client.DeleteClientException;
import com.database.ProjectDB.exceptions.client.InsertClientException;
import com.database.ProjectDB.exceptions.client.UpdateClientException;
import com.database.ProjectDB.tools.AlertType;
import com.database.ProjectDB.tools.WindowComponents;
import com.database.ProjectDB.ui.components.ClientComponent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by seba on 2017-05-17.
 */
public class ClientQueries {
    private static Controller controller = Controller.getControllerInstance();
    private static ServiceDAO service = ServiceDAO.getInstance();

    public static ObservableList<Client> selectClients() {
        ObservableList<Client> clientList = FXCollections.observableArrayList();
        try {
            clientList.clear();
            EntityManager entityManager = service.beginTransaction();
            List<Client> list = new ArrayList();
            list = entityManager.createQuery("from Client", Client.class).getResultList();
            list.forEach(client ->
                    clientList.add(client)
            );
            service.commitTransaction();
        } catch (Exception exception) {
            WindowComponents.createAlert(AlertType.ERROR, exception.getMessage());
        }
        return clientList;
    }

    public static void insertClient() {
        try {
            EntityManager entityManager = service.beginTransaction();
            Client client = new Client();
            if (controller.getClientFirstNameInput().getText().isEmpty())
                throw new InsertClientException("Please insert first name of client");
            else client.setFirstName(controller.getClientFirstNameInput().getText());

            if (controller.getClientLastNameInput().getText().isEmpty())
                throw new InsertClientException("Please insert last name of client");
            else client.setLastName(controller.getClientLastNameInput().getText());

            client.setPhoneNumber(Long.parseLong(controller.getClientPhoneNumberInput().getText()));

            if (!(controller.getClientAddressIDInput().getText().isEmpty())) {
                Long addressId = Long.parseLong(controller.getClientAddressIDInput().getText());
                Address address = entityManager.createQuery("from Address a where a.id = " + addressId, Address.class).getSingleResult();
                client.setAddress(address);
                address.getAddressesOfClients().add(client);

                entityManager.persist(client);
                service.commitTransaction();
                ClientComponent.createTable(selectClients());

                WindowComponents.createAlert(AlertType.INFORMATION, "Client has beed created.");
                WindowComponents.sendConsoleMessage("SUCCESS: Client has beed created");
            } else
                throw new InsertClientException("Please insert Address ID");

        } catch (Exception exception) {
            WindowComponents.createAlert(AlertType.ERROR, exception.getMessage());
        }
    }

    public static void deleteClient() {
        try {
            EntityManager entityManager = service.beginTransaction();
            String clientId = controller.getClientIDInput().getText();
            if (!clientId.isEmpty()) {
                String query = "from Client c where c.id = " + Long.parseLong(clientId);
                Client client = entityManager.createQuery(query, Client.class).getSingleResult();
                Optional<Client> optional = Optional.ofNullable(client);
                if (optional.isPresent()) {
                    Address address = entityManager.createQuery("from Address a where a.id = " + client.getAddress(), Address.class).getSingleResult();
                    address.getAddressesOfClients().remove(client);
                    if (!client.getCollectionOfOrders().isEmpty())
                        throw new DeleteClientException("This object is connected with some orders");
                    entityManager.remove(client);
                    service.commitTransaction();
                    ClientComponent.createTable(ClientQueries.selectClients());
                    WindowComponents.createAlert(AlertType.INFORMATION, "Client had beed deleted.");
                    WindowComponents.sendConsoleMessage("SUCCESS: Client has beed deleted.");
                }
            } else
                throw new DeleteClientException("Please insert Client ID");

        } catch (Exception exception) {
            WindowComponents.sendConsoleMessage(exception.getMessage());
            WindowComponents.createAlert(AlertType.ERROR, exception.getMessage());
        }
    }

    public static void deleteClient(Client client) {
        try {
            EntityManager entityManager = service.beginTransaction();
            Optional<Client> optional = Optional.ofNullable(client);
            if (optional.isPresent()) {
                Address address = entityManager.createQuery("from Address a where a.id = " + client.getAddress(), Address.class).getSingleResult();
                address.getAddressesOfClients().remove(client);
                if (!client.getCollectionOfOrders().isEmpty())
                    throw new DeleteClientException("This object is connected with some orders");
                entityManager.remove(client);
                service.commitTransaction();
                ClientComponent.createTable(ClientQueries.selectClients());
                WindowComponents.createAlert(AlertType.INFORMATION, "Client had beed deleted.");
                WindowComponents.sendConsoleMessage("SUCCESS: Client has beed deleted.");
            }
        } catch (Exception exception) {
            WindowComponents.sendConsoleMessage(exception.getMessage());
            WindowComponents.createAlert(AlertType.ERROR, exception.getMessage());
        }
    }

    public static void updateClient() {
        try {
            String clientId = controller.getClientIDInput().getText();
            if (clientId.isEmpty()) {
                throw new UpdateClientException("Please insert Client ID");
            } else {
                EntityManager entityManager = service.beginTransaction();
                Client client = entityManager.createQuery("from Client c where c.id = " + Long.parseLong(clientId), Client.class).getSingleResult();

                if (!controller.getClientFirstNameInput().getText().isEmpty())
                    client.setFirstName(controller.getClientFirstNameInput().getText());

                if (!controller.getClientLastNameInput().getText().isEmpty())
                    client.setLastName(controller.getClientLastNameInput().getText());

                if (!controller.getClientPhoneNumberInput().getText().isEmpty())
                    client.setPhoneNumber(Long.parseLong(controller.getClientPhoneNumberInput().getText()));

                if (!controller.getClientAddressIDInput().getText().isEmpty()) {
                    Long addressId = Long.parseLong(controller.getClientAddressIDInput().getText());
                    Address address = entityManager.createQuery("from Address a where a.id = " + addressId, Address.class).getSingleResult();
                    client.getAddressObject().getAddressesOfClients().remove(client);
                    client.setAddress(address);
                    address.getAddressesOfClients().add(client);
                }
                service.commitTransaction();
                ClientComponent.createTable(selectClients());
                WindowComponents.createAlert(AlertType.INFORMATION, "Client has beed updated");
                WindowComponents.sendConsoleMessage("SUCCESS: Client has beed updated");
            }
        } catch (Exception exception) {
            WindowComponents.sendConsoleMessage(exception.getMessage());
            WindowComponents.createAlert(AlertType.ERROR, exception.getMessage());
        }
    }
}
