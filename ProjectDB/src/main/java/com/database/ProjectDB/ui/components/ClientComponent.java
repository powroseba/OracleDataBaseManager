package com.database.ProjectDB.ui.components;

import com.database.ProjectDB.Controller;
import com.database.ProjectDB.DQO.ClientQueries;
import com.database.ProjectDB.entity.Client;
import com.database.ProjectDB.tools.AlertType;
import com.database.ProjectDB.tools.WindowComponents;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;


/**
 * Created by seba on 2017-05-17.
 */
public class ClientComponent {
    private static Controller controller = Controller.getControllerInstance();
    private static boolean show = true;

    public static void createTable(ObservableList<Client> lista) {
        TableColumn<Client, Long> idColumn = new TableColumn("Client ID");
        idColumn.setCellValueFactory(new PropertyValueFactory("id"));

        TableColumn<Client, String> firstNameColumn = new TableColumn("First Name");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory("firstName"));

        TableColumn<Client, String> lastNameColumn = new TableColumn("Last Name");
        lastNameColumn.setCellValueFactory(new PropertyValueFactory("lastName"));

        TableColumn<Client, Long> phoneNumberColumn = new TableColumn("Phone Number");
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory("phoneNumber"));


        TableColumn<Client, String> orderIDColumn = new TableColumn("Order ID");
        orderIDColumn.setCellValueFactory(new PropertyValueFactory("collectionOfOrders"));

        TableColumn<Client, Long> addressIDColumn = new TableColumn("Address ID");
        addressIDColumn.setCellValueFactory(new PropertyValueFactory("address"));

        controller.getClientTable().getColumns().clear();
        controller.getClientTable().setItems(lista);
        controller.getClientTable().getColumns().addAll(idColumn, firstNameColumn, lastNameColumn, phoneNumberColumn,
                orderIDColumn, addressIDColumn);
    }

    public static void sumbitButtonTools(){
        if(controller.getClientTab().isSelected() && controller.getSelectClientButton().isSelected()){
            ObservableList<Client> lista = ClientQueries.selectClients();
            createTable(lista);
        }
        if (controller.getClientTab().isSelected() && controller.getInsertClientButton().isSelected())
            ClientQueries.insertClient();
        if (controller.getClientTab().isSelected() && controller.getDeleteClientButton().isSelected())
            ClientQueries.deleteClient();
        if (controller.getClientTab().isSelected() && controller.getUpdateClientButton().isSelected())
            ClientQueries.updateClient();
    }

    public static void clientPaneTools() {
        if (controller.getSelectClientButton().isSelected()) controller.getClientPane().setDisable(true);
        else controller.getClientPane().setDisable(false);
        if (controller.getInsertClientButton().isSelected()) controller.getClientIDInput().setDisable(true);
        else controller.getClientIDInput().setDisable(false);
        if (controller.getDeleteClientButton().isSelected()){
            boolean zmiana = true;
            controller.getClientFirstNameInput().setDisable(zmiana);
            controller.getClientPhoneNumberInput().setDisable(zmiana);
            controller.getClientAddressIDInput().setDisable(zmiana);
            controller.getClientLastNameInput().setDisable(zmiana);
        }
        else
        {
            boolean zmiana = false;
            controller.getClientFirstNameInput().setDisable(zmiana);
            controller.getClientPhoneNumberInput().setDisable(zmiana);
            controller.getClientAddressIDInput().setDisable(zmiana);
            controller.getClientLastNameInput().setDisable(zmiana);
        }
        if (controller.getUpdateClientButton().isSelected() && show) {
            WindowComponents.sendConsoleMessage("The databse will update order by id only fields with data");
            WindowComponents.createAlert(AlertType.INFORMATION, "The databse will update order by id only fields with data");
            show = false;
            WindowComponents.clearInputs();
        }
    }

    public static void injectDataToComponents() {
        if (controller.getClientTable().getSelectionModel().isEmpty());
        else {
            Client client = (Client)controller.getClientTable().getSelectionModel().getSelectedItem();
            controller.getClientIDInput().setText(client.getId().toString());
            controller.getClientFirstNameInput().setText(client.getFirstName());
            controller.getClientLastNameInput().setText(client.getLastName());
            controller.getClientPhoneNumberInput().setText(client.getPhoneNumber().toString());
            controller.getClientAddressIDInput().setText(client.getAddress().toString());
        }
    }

    public static void deleteSelected() {
        if (controller.getClientTab().isSelected()){
            if (controller.getClientTable().getSelectionModel().isEmpty());
            else {
                Client client = (Client) controller.getClientTable().getSelectionModel().getSelectedItem();
                ClientQueries.deleteClient(client);
            }
        }
    }
}
