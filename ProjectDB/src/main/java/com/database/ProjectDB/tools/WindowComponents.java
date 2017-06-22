package com.database.ProjectDB.tools;


import com.database.ProjectDB.Controller;
import com.database.ProjectDB.ui.components.FunctionComponent;
import javafx.scene.control.Alert;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;

/**
 * Created by seba on 2017-05-12.
 */
public class WindowComponents {
    private static Controller controller = Controller.getControllerInstance();
    public static void createAlert(AlertType type, String message){
        Optional<String> optional = Optional.ofNullable(message);
        if (optional.isPresent()) {
            Alert alert = null;
            if (type.equals(AlertType.ERROR)) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
            }
            if (type.equals(AlertType.INFORMATION)) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
            }
            if (type.equals(AlertType.WARNING)) {
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
            }
            if (type.equals(AlertType.CONFIRMATION)) {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
            }
            if (type.equals(AlertType.NONE)) {
                alert = new Alert(Alert.AlertType.NONE);
                alert.setTitle("Dialog");
            }
            alert.setHeaderText("");
            alert.setContentText(message);
            alert.showAndWait();
        }
    }

    public static void sendConsoleMessage(String message){
        controller.getMessageConsole().appendText(DataConverter.getTime() + message);
    }

    public static void clearInputs(){
        if (controller.getOrderTab().isSelected()) {
            controller.getOrderIdInput().clear();
            controller.getOrderPriceInput().clear();
            controller.getOrderDoneInput().setSelected(false);
            controller.getOrderProgressInput().setSelected(false);
            controller.getOrderDateOfReceipt().setValue(null);
            controller.getOrderDateOfReturn().setValue(null);
            controller.getOrderEmployeeIDInput().clear();
            controller.getOrderClientIDInput().clear();
            controller.getOrderPhoneModelInput().clear();
            controller.getOrderRepairIDInput().clear();
        }
        if (controller.getEmployeeTab().isSelected()) {
            controller.getEmployeeIdInput().clear();
            controller.getEmployeeSalaryInput().clear();
            controller.getEmployeePeselInput().clear();
            controller.getEmployeeEmailInput().clear();
            controller.getEmployeeFirstNameInput().clear();
            controller.getEmployeePhoneNumberInput().clear();
            controller.getEmployeeAddresssIDInput().clear();
            controller.getEmployeeLastNameInput().clear();
        }
        if (controller.getRepairTab().isSelected()) {
            controller.getRepairIDInput().clear();
            controller.getRepairDefectInput().clear();
            controller.getRepairPriceInput().clear();
            controller.getRepairOrderIDInput().clear();
        }
        if (controller.getClientTab().isSelected()) {
            controller.getClientIDInput().clear();
            controller.getClientFirstNameInput().clear();
            controller.getClientPhoneNumberInput().clear();
            controller.getClientAddressIDInput().clear();
            controller.getClientLastNameInput().clear();
        }
        if (controller.getAddressTab().isSelected()) {
            controller.getAddressIDInput().clear();
            controller.getAddressCityInput().clear();
            controller.getAddressStreetInput().clear();
            controller.getAddressHomeNumberInput().clear();
            controller.getAddressPinCodeInput().clear();
            controller.getAddressEmployeeIDInput().clear();
            controller.getAddressClientIDInput().clear();
        }
        if (controller.getFunctionTab().isSelected()){
            FunctionComponent.clearFunctionInput();
        }
    }

    public void openUserGuide() {
        File myFile = new File("UserGuide.pdf");
        try {
            Desktop.getDesktop().open(myFile);
        } catch (IOException ex) {
            WindowComponents.createAlert(AlertType.ERROR, ex.getMessage());
        }
    }

}

