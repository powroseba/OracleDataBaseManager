package com.database.ProjectDB.ui.components;


import com.database.ProjectDB.Controller;
import com.database.ProjectDB.DQO.AddressQueries;
import com.database.ProjectDB.entity.Address;
import com.database.ProjectDB.tools.AlertType;
import com.database.ProjectDB.tools.WindowComponents;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Created by seba on 2017-05-17.
 */
public class AddressComponent {
    private static Controller controller = Controller.getControllerInstance();
    private static boolean show = true;

    public static void createTable(ObservableList<Address> lista) {
        TableColumn<Address, Long> idColumn = new TableColumn("Address ID");
        idColumn.setCellValueFactory(new PropertyValueFactory("id"));

        TableColumn<Address, String> cityColumn = new TableColumn("City");
        cityColumn.setCellValueFactory(new PropertyValueFactory("city"));

        TableColumn<Address, String> streetColumn = new TableColumn("Street");
        streetColumn.setCellValueFactory(new PropertyValueFactory("street"));

        TableColumn<Address, String> pinCodeColumn = new TableColumn("Pin Code");
        pinCodeColumn.setCellValueFactory(new PropertyValueFactory("pinCode"));

        TableColumn<Address, String> homeNumberColumn = new TableColumn("Home Number");
        homeNumberColumn.setCellValueFactory(new PropertyValueFactory("homeNumber"));

        TableColumn<Address, String> clientColumn = new TableColumn("Clients ID");
        clientColumn.setCellValueFactory(new PropertyValueFactory("addressesOfClients"));

        TableColumn<Address, String> employeeColumn = new TableColumn("Employees ID");
        employeeColumn.setCellValueFactory(new PropertyValueFactory("addressesOfEmployers"));

        controller.getAddressTable().getColumns().clear();
        controller.getAddressTable().setItems(lista);
        controller.getAddressTable().getColumns().addAll(idColumn, cityColumn, streetColumn,
                pinCodeColumn, homeNumberColumn, clientColumn, employeeColumn);
    }

    public static void submitButtonTools() {
        if (controller.getAddressTab().isSelected() && controller.getSelectAddressButton().isSelected()) {
            ObservableList<Address> lista = AddressQueries.selectAddresses();
            createTable(lista);
        }
        if (controller.getAddressTab().isSelected() && controller.getInsertAddressButton().isSelected())
            AddressQueries.insertAddress();
        if (controller.getAddressTab().isSelected() && controller.getDeleteAddressButton().isSelected())
            AddressQueries.deleteAddress();
        if (controller.getAddressTab().isSelected() && controller.getUpdateAddressButton().isSelected())
            AddressQueries.updateAddress();
    }

    public static void addressPaneTools() {
        if (controller.getSelectAddressButton().isSelected()) controller.getAddressPane().setDisable(true);
        else controller.getAddressPane().setDisable(false);
        if (controller.getInsertAddressButton().isSelected()) controller.getAddressIDInput().setDisable(true);
        else controller.getAddressIDInput().setDisable(false);
        if (controller.getDeleteAddressButton().isSelected()){
            boolean zmiana = true;
            controller.getAddressCityInput().setDisable(zmiana);
            controller.getAddressStreetInput().setDisable(zmiana);
            controller.getAddressHomeNumberInput().setDisable(zmiana);
            controller.getAddressPinCodeInput().setDisable(zmiana);
            controller.getAddressEmployeeIDInput().setDisable(zmiana);
            controller.getAddressClientIDInput().setDisable(zmiana);
        }
        else {
            boolean zmiana = false;
            controller.getAddressCityInput().setDisable(zmiana);
            controller.getAddressStreetInput().setDisable(zmiana);
            controller.getAddressHomeNumberInput().setDisable(zmiana);
            controller.getAddressPinCodeInput().setDisable(zmiana);
            controller.getAddressEmployeeIDInput().setDisable(zmiana);
            controller.getAddressClientIDInput().setDisable(zmiana);
        }
        if (controller.getUpdateAddressButton().isSelected() && show) {
            WindowComponents.sendConsoleMessage("The databse will update order by id only fields with data");
            WindowComponents.createAlert(AlertType.INFORMATION, "The databse will update order by id only fields with data");
            show = false;
            WindowComponents.clearInputs();
        }
    }

    public static void injectDataToComponents() {
        if (controller.getAddressTable().getSelectionModel().isEmpty()) ;
        else {
            Address address = (Address)controller.getAddressTable().getSelectionModel().getSelectedItem();
                controller.getAddressIDInput().setText(address.getId().toString());
                controller.getAddressCityInput().setText(address.getCity().toString());
                controller.getAddressStreetInput().setText(address.getStreet().toString());
                controller.getAddressHomeNumberInput().setText(address.getHomeNumber().toString());
                controller.getAddressPinCodeInput().setText(address.getPinCode());
        }
    }

    public static void deleteSelected() {
        if (controller.getAddressTab().isSelected()) {
            if (controller.getAddressTable().getSelectionModel().isEmpty()) ;
            else {
                Address address = (Address) controller.getAddressTable().getSelectionModel().getSelectedItem();
                AddressQueries.deleteAddress(address);
            }
        }
    }
}
