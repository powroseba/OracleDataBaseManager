package com.database.ProjectDB.ui.components;


import com.database.ProjectDB.Controller;
import com.database.ProjectDB.DAO.ServiceDAO;
import com.database.ProjectDB.DQO.FunctionQueries;
import com.database.ProjectDB.entity.Status;
import com.database.ProjectDB.exceptions.function.FunctionNotConnectException;
import com.database.ProjectDB.tools.AlertType;
import com.database.ProjectDB.tools.WindowComponents;

import java.math.BigDecimal;
import java.time.Month;
import java.util.Optional;

/**
 * Created by seba on 2017-06-03.
 */
public class FunctionComponent {
    private static Controller controller = Controller.getControllerInstance();

    public static void injectDateToBoxes() {
        controller.getFunctionSecondBoxStatus().getItems().add(Status.DONE);
        controller.getFunctionSecondBoxStatus().getItems().add(Status.INPROGRESS);

        controller.getFunctionThirdBoxStatus().getItems().add(Status.DONE);
        controller.getFunctionThirdBoxStatus().getItems().add(Status.INPROGRESS);

        controller.getFunctionThirdBoxMonth().getItems().add(Month.JANUARY);
        controller.getFunctionThirdBoxMonth().getItems().add(Month.FEBRUARY);
        controller.getFunctionThirdBoxMonth().getItems().add(Month.MARCH);
        controller.getFunctionThirdBoxMonth().getItems().add(Month.APRIL);
        controller.getFunctionThirdBoxMonth().getItems().add(Month.MAY);
        controller.getFunctionThirdBoxMonth().getItems().add(Month.JUNE);
        controller.getFunctionThirdBoxMonth().getItems().add(Month.JULY);
        controller.getFunctionThirdBoxMonth().getItems().add(Month.AUGUST);
        controller.getFunctionThirdBoxMonth().getItems().add(Month.SEPTEMBER);
        controller.getFunctionThirdBoxMonth().getItems().add(Month.NOVEMBER);
        controller.getFunctionThirdBoxMonth().getItems().add(Month.OCTOBER);
        controller.getFunctionThirdBoxMonth().getItems().add(Month.DECEMBER);
    }

    public static void managerFunctions() {
        controller.getFunctionFirstSubmit().setOnAction(event -> {
            try {
                throwWindowIfDissconnected();
                if (!(controller.getFunctionFirstEmployeeIDInput().getText().isEmpty() &&
                        controller.getFunctionFirstYearInput().getText().isEmpty())) {
                    Long employeeId = Long.parseLong(controller.getFunctionFirstEmployeeIDInput().getText());
                    Long year = Long.parseLong(controller.getFunctionFirstYearInput().getText());
                    BigDecimal result = FunctionQueries.firstFunction(employeeId, year);
                    Optional<BigDecimal> optional = Optional.ofNullable(result);
                    if (optional.isPresent())
                        controller.getFunctionFirstResult().setText(result.toString());
                    else controller.getFunctionFirstResult().setText("Brak danych");
                } else WindowComponents.createAlert(AlertType.WARNING, "Wypelnij wszystkie wartosci!");
            } catch (Exception exception) {
                WindowComponents.createAlert(AlertType.ERROR, exception.getMessage());
            }
        });
        controller.getFunctionSecondSubmit().setOnAction(event -> {
            try {
                throwWindowIfDissconnected();
                if (controller.getFunctionSecondBoxStatus().getValue() != null) {
                    String status = controller.getFunctionSecondBoxStatus().getValue().toString();
                    BigDecimal result = FunctionQueries.secondFunction(status);
                    Optional<BigDecimal> optional = Optional.ofNullable(result);
                    if (optional.isPresent())
                        controller.getFunctionSecondResult().setText(result.toString());
                    else controller.getFunctionSecondResult().setText("Brak danych");
                } else WindowComponents.createAlert(AlertType.WARNING, "Wypelnij wszystkie wartosci!");
            } catch (Exception exception) {
                WindowComponents.createAlert(AlertType.ERROR, exception.getMessage());
            }
        });
        controller.getFunctionThirdSubmit().setOnAction(event -> {
            try {
                throwWindowIfDissconnected();
                if (controller.getFunctionThirdBoxStatus().getValue() != null && controller.getFunctionThirdBoxMonth().getValue() != null) {
                    String status = controller.getFunctionThirdBoxStatus().getValue().toString();
                    int month = controller.getFunctionThirdBoxMonth().getValue().getValue();
                    BigDecimal result = FunctionQueries.thirdFunction(status, month);
                    Optional<BigDecimal> optional = Optional.ofNullable(result);
                    if (optional.isPresent())
                        controller.getFunctionThirdResult().setText(result.toString());
                    else controller.getFunctionThirdResult().setText("Brak danych");
                } else WindowComponents.createAlert(AlertType.WARNING, "Wypelnij wszystkie wartosci!");
            } catch (Exception exception) {
                WindowComponents.createAlert(AlertType.ERROR, exception.getMessage());
            }
        });
    }

    public static void clearFunctionInput() {
        controller.getFunctionThirdResult().clear();
        controller.getFunctionSecondResult().clear();
        controller.getFunctionFirstResult().clear();
        controller.getFunctionThirdBoxMonth().setValue(null);
        controller.getFunctionThirdBoxStatus().setValue(null);
        controller.getFunctionSecondBoxStatus().setValue(null);
        controller.getFunctionFirstEmployeeIDInput().clear();
        controller.getFunctionFirstYearInput().clear();
    }

    public static void throwWindowIfDissconnected() throws FunctionNotConnectException {
        if (!ServiceDAO.isConnected)
            throw new FunctionNotConnectException("Database is dissconnected");
    }
}