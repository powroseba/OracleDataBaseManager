package com.database.ProjectDB.ui.components;

import com.database.ProjectDB.Controller;
import com.database.ProjectDB.DQO.OrderQueries;
import com.database.ProjectDB.entity.Order;
import com.database.ProjectDB.entity.Status;
import com.database.ProjectDB.tools.AlertType;
import com.database.ProjectDB.tools.WindowComponents;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by seba on 2017-05-11.
 */
public class OrderComponent {
    private static Controller controller = Controller.getControllerInstance();
    private static boolean show = true;
    private static boolean showDelete = true;

    public static void createTable(ObservableList lista){

        TableColumn<Order, Long> idColumn = new TableColumn("Order ID");
        idColumn.setCellValueFactory(new PropertyValueFactory("id"));

        TableColumn<Order, BigDecimal> priceColumn = new TableColumn("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory("price"));

        TableColumn<Order, Status> statusColumn = new TableColumn("Status");
        statusColumn.setCellValueFactory(new PropertyValueFactory("status"));

        TableColumn<Order, Date> dateOfReceiptColumn = new TableColumn("Date Of Receipt");
        dateOfReceiptColumn.setCellValueFactory(new PropertyValueFactory("dateOfReceipt"));

        TableColumn<Order, Date> dateOfReturnColumn = new TableColumn("Date Of Return");
        dateOfReturnColumn.setCellValueFactory(new PropertyValueFactory("dateOfReturn"));

        TableColumn<Order, String> phoneModelColumn = new TableColumn("Phone Model");
        phoneModelColumn.setCellValueFactory(new PropertyValueFactory("phoneModel"));

        TableColumn<Order, Long> employeeIDColumn = new TableColumn("Employee ID");
        employeeIDColumn.setCellValueFactory(new PropertyValueFactory("employee"));

        TableColumn<Order, Long> clientIDColumn = new TableColumn("Client ID");
        clientIDColumn.setCellValueFactory(new PropertyValueFactory("client"));

        TableColumn<Order, String> repairsIDColumn = new TableColumn("Repairs ID");
        repairsIDColumn.setCellValueFactory(new PropertyValueFactory("repairList"));

        controller.getOrderTable().getColumns().clear();
        controller.getOrderTable().setItems(lista);
        controller.getOrderTable().getColumns().addAll(idColumn, priceColumn, statusColumn, dateOfReceiptColumn,
                dateOfReturnColumn, phoneModelColumn, employeeIDColumn, clientIDColumn, repairsIDColumn);

    }

    public static void orderPaneTools(){
        if (controller.getSelectOrderButton().isSelected()) controller.getOrderPane().setDisable(true);
        else controller.getOrderPane().setDisable(false);
        if (controller.getInsertOrderButton().isSelected()) controller.getOrderIdInput().setDisable(true);
        else controller.getOrderIdInput().setDisable(false);
        if (controller.getDeleteOrderButton().isSelected()) {
            if (showDelete){
            WindowComponents.createAlert(AlertType.INFORMATION, "If you wonna delete one repair from order" +
                    " you must input the id of repair to field, this action will just delete repair from order, not all object");
                showDelete = false;
            }
            boolean zmiana = true;
            controller.getOrderPriceInput().setDisable(zmiana);
            controller.getOrderDoneInput().setDisable(zmiana);
            controller.getOrderProgressInput().setDisable(zmiana);
            controller.getOrderDateOfReceipt().setDisable(zmiana);
            controller.getOrderDateOfReturn().setDisable(zmiana);
            controller.getOrderEmployeeIDInput().setDisable(zmiana);
            controller.getOrderClientIDInput().setDisable(zmiana);
            controller.getOrderPhoneModelInput().setDisable(zmiana);

        }
        else {
            boolean zmiana = false;
            controller.getOrderPriceInput().setDisable(zmiana);
            controller.getOrderDoneInput().setDisable(zmiana);
            controller.getOrderProgressInput().setDisable(zmiana);
            controller.getOrderDateOfReceipt().setDisable(zmiana);
            controller.getOrderDateOfReturn().setDisable(zmiana);
            controller.getOrderEmployeeIDInput().setDisable(zmiana);
            controller.getOrderClientIDInput().setDisable(zmiana);
            controller.getOrderPhoneModelInput().setDisable(zmiana);
        }
        if (controller.getUpdateOrderButton().isSelected() && show) {
            WindowComponents.sendConsoleMessage("The databse will update order by id only fields with data");
            WindowComponents.createAlert(AlertType.INFORMATION, "The databse will update only fields with data");
            show = false;
            WindowComponents.clearInputs();
        }
    }

    public static void submitButtonTools(){
        if (controller.getOrderTab().isSelected() && controller.getSelectOrderButton().isSelected()) {
            ObservableList<Order> lista = OrderQueries.selectOrders();
            createTable(lista);
        }
        if (controller.getOrderTab().isSelected() && controller.getInsertOrderButton().isSelected()) {
            OrderQueries.insertOrder();
        }
        if(controller.getOrderTab().isSelected() && controller.getDeleteOrderButton().isSelected()){
            OrderQueries.deleteOrder();
        }
        if(controller.getOrderTab().isSelected() && controller.getUpdateOrderButton().isSelected()){
            OrderQueries.updateOrder();
        }
    }

    public static void injectDataToComponents(){
        if (controller.getOrderTable().getSelectionModel().isEmpty());
        else {
            Order order = (Order)controller.getOrderTable().getSelectionModel().getSelectedItem();
            controller.getOrderIdInput().setText(order.getId().toString());
            controller.getOrderPriceInput().setText(order.getPrice().toString());
            if (order.getStatus().equals(Status.DONE)) controller.getOrderDoneInput().setSelected(true);
            else controller.getOrderProgressInput().setSelected(true);
            if (order.getDateOfReceipt() != null)
            controller.getOrderDateOfReceipt().setValue(
                    new java.sql.Date(order.getDateOfReceipt().getTime()).toLocalDate()
            );
            if (order.getDateOfReturn() != null)
            controller.getOrderDateOfReturn().setValue(
                    new java.sql.Date(order.getDateOfReturn().getTime()).toLocalDate()
            );
            controller.getOrderPhoneModelInput().setText(order.getPhoneModel());
            controller.getOrderEmployeeIDInput().setText(order.getEmployee().toString());
            controller.getOrderClientIDInput().setText(order.getClient().toString());
        }
    }

    public static void deleteSelected() {
        if (controller.getOrderTab().isSelected()) {
            if (controller.getOrderTable().getSelectionModel().isEmpty()) ;
            else {
                Order order = (Order) controller.getOrderTable().getSelectionModel().getSelectedItem();
                OrderQueries.deleteOrder(order);
            }
        }
    }
}
