package com.database.ProjectDB.ui.components;


import com.database.ProjectDB.Controller;
import com.database.ProjectDB.DQO.RepairQueries;
import com.database.ProjectDB.entity.Repair;
import com.database.ProjectDB.tools.AlertType;
import com.database.ProjectDB.tools.WindowComponents;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;


/**
 * Created by seba on 2017-05-16.
 */
public class RepairComponent {
    private static Controller controller = Controller.getControllerInstance();
    private static boolean show = true;

    public static void createTable(ObservableList lista){

        TableColumn<Repair, Long> idColumn = new TableColumn("Repair ID");
        idColumn.setCellValueFactory(new PropertyValueFactory("id"));

        TableColumn<Repair, String> defectColumn = new TableColumn("Defect");
        defectColumn.setCellValueFactory(new PropertyValueFactory("defect"));

        TableColumn<Repair, BigDecimal> priceOfDefectColumn = new TableColumn("Price of defect");
        priceOfDefectColumn.setCellValueFactory(new PropertyValueFactory("priceOfDefect"));

        TableColumn<Repair, String> orderListColumn = new TableColumn("Orders");
        orderListColumn.setCellValueFactory(new PropertyValueFactory("orderList"));

        controller.getRepairTable().getColumns().clear();
        controller.getRepairTable().setItems(lista);
        controller.getRepairTable().getColumns().addAll(idColumn, defectColumn, priceOfDefectColumn, orderListColumn);
    }

    public static void submitButtonTools() {
        if (controller.getRepairTab().isSelected() && controller.getSelectRepairButton().isSelected()){
            ObservableList<Repair> lista = RepairQueries.selectRepairs();
            createTable(lista);
        }
        if (controller.getRepairTab().isSelected() && controller.getInsertRepairButton().isSelected())
            RepairQueries.insertRepair();
        if (controller.getRepairTab().isSelected() && controller.getDeleteRepairButton().isSelected())
            RepairQueries.deleteRepair();
        if (controller.getRepairTab().isSelected() && controller.getUpdateRepairButton().isSelected())
            RepairQueries.updateRepair();
    }

    public static void repairPaneTools(){
        if (controller.getSelectRepairButton().isSelected()) controller.getRepairPane().setDisable(true);
        else controller.getRepairPane().setDisable(false);
        if (controller.getInsertRepairButton().isSelected()) controller.getRepairIDInput().setDisable(true);
        else controller.getRepairIDInput().setDisable(false);
        if (controller.getDeleteRepairButton().isSelected()){
            boolean zmiana = true;
            controller.getRepairDefectInput().setDisable(zmiana);
            controller.getRepairPriceInput().setDisable(zmiana);
            controller.getRepairOrderIDInput().setDisable(zmiana);
        }
        else {
            boolean zmiana = false;
            controller.getRepairDefectInput().setDisable(zmiana);
            controller.getRepairPriceInput().setDisable(zmiana);
            controller.getRepairOrderIDInput().setDisable(zmiana);
        }
        if (controller.getUpdateRepairButton().isSelected() && show){
            WindowComponents.sendConsoleMessage("The databse will update order by id only fields with data");
            WindowComponents.createAlert(AlertType.INFORMATION, "The databse will update only fields with data");
            show = false;
            WindowComponents.clearInputs();
        }
    }

    public static void injectDataToComponents() {
        if (controller.getRepairTable().getSelectionModel().isEmpty());
        else {
            Repair repair = (Repair)controller.getRepairTable().getSelectionModel().getSelectedItem();
            controller.getRepairIDInput().setText(repair.getId().toString());
            controller.getRepairDefectInput().setText(repair.getDefect());
            controller.getRepairPriceInput().setText(repair.getPriceOfDefect().toString());
        }
    }

    public static void deleteSelected() {
        if (controller.getRepairTab().isSelected()){
            if (controller.getRepairTable().getSelectionModel().isEmpty());
            else {
                Repair repair = (Repair) controller.getRepairTable().getSelectionModel().getSelectedItem();
                RepairQueries.deleteRepair(repair);
            }
        }
    }
}
