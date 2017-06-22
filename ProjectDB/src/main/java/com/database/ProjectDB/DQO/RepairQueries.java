package com.database.ProjectDB.DQO;


import com.database.ProjectDB.Controller;
import com.database.ProjectDB.DAO.ServiceDAO;
import com.database.ProjectDB.entity.Order;
import com.database.ProjectDB.entity.Repair;
import com.database.ProjectDB.exceptions.repair.DeleteRepairException;
import com.database.ProjectDB.exceptions.repair.InsertRepairException;
import com.database.ProjectDB.exceptions.repair.UpdateRepairException;
import com.database.ProjectDB.tools.AlertType;
import com.database.ProjectDB.tools.WindowComponents;
import com.database.ProjectDB.ui.components.RepairComponent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by seba on 2017-05-16.
 */
public class RepairQueries {
    private static Controller controller = Controller.getControllerInstance();
    private static ServiceDAO service = ServiceDAO.getInstance();

    public static ObservableList<Repair> selectRepairs() {
        ObservableList<Repair> repairList = FXCollections.observableArrayList();
        try {
            repairList.clear();
            EntityManager entityManager = service.beginTransaction();
            List<Repair> list = new ArrayList();
            list = entityManager.createQuery("from Repair", Repair.class).getResultList();
            list.forEach(repair ->
                    repairList.add(repair)
            );
            service.commitTransaction();
        } catch (Exception exception) {
            WindowComponents.createAlert(AlertType.ERROR, exception.getMessage());
        }
        return repairList;
    }

    public static void insertRepair() {
        try {
            EntityManager entityManager = service.beginTransaction();
            Repair repair = new Repair();
            if (controller.getRepairPriceInput().getText().isEmpty())
                throw new InsertRepairException("Please insert price of repair");
            else repair.setPriceOfDefect(controller.getRepairPriceInput().getText());

            if (controller.getRepairDefectInput().getText().isEmpty())
                throw new InsertRepairException("Please insert information about repair defect");
            else repair.setDefect(controller.getRepairDefectInput().getText());

            if (!(controller.getRepairOrderIDInput().getText().isEmpty())) {
                Long orderId = Long.parseLong(controller.getRepairOrderIDInput().getText());
                Order order = entityManager.createQuery("from Order o where o.id = " + orderId, Order.class).getSingleResult();
                if (!order.getRepairList().contains(repair)) {
                    order.addPrice(controller.getRepairPriceInput().getText());
                    repair.getOrderList().add(order);
                    order.getRepairList().add(repair);
                }
            }
            entityManager.persist(repair);
            service.commitTransaction();
            RepairComponent.createTable(selectRepairs());

            WindowComponents.createAlert(AlertType.INFORMATION, "Repair has beed created.");
            WindowComponents.sendConsoleMessage("SUCCESS: Repair has beed created");

        } catch (Exception exception) {
            WindowComponents.createAlert(AlertType.ERROR, exception.getMessage());
        }
    }

    public static void deleteRepair() {
        try {
            EntityManager entityManager = service.beginTransaction();
            String repairId = controller.getRepairIDInput().getText();
            if (!repairId.isEmpty()) {
                String query = "from Repair r where r.id = " + repairId;
                Repair repairToDelete = entityManager.createQuery(query, Repair.class).getSingleResult();
                Optional<Repair> optional = Optional.ofNullable(repairToDelete);
                if (optional.isPresent()) {
                    Repair finalRepairToDelete = repairToDelete;
                    repairToDelete.getOrderList().forEach(order -> {
                        order.subtractPrice(repairToDelete.getPriceOfDefect().toString());
                        order.getRepairList().remove(finalRepairToDelete);
                    });
                    repairToDelete.getOrderList().clear();
                    entityManager.remove(repairToDelete);
                    service.commitTransaction();
                    RepairComponent.createTable(selectRepairs());
                    WindowComponents.createAlert(AlertType.INFORMATION, "Repair had beed deleted.");
                    WindowComponents.sendConsoleMessage("SUCCESS: Repair has beed deleted.");
                }
            } else
                throw new DeleteRepairException("Please insert Repair ID");
        } catch (Exception exception) {
            WindowComponents.sendConsoleMessage(exception.getMessage());
            WindowComponents.createAlert(AlertType.ERROR, exception.getMessage());
        }
    }


    public static void deleteRepair(Repair repair) {
        try {
            EntityManager entityManager = service.beginTransaction();
            Optional<Repair> optional = Optional.ofNullable(repair);
            if (optional.isPresent()) {
                Repair finalRepairToDelete = repair;
                repair.getOrderList().forEach(order -> {
                    order.subtractPrice(repair.getPriceOfDefect().toString());
                    order.getRepairList().remove(finalRepairToDelete);
                });
                repair.getOrderList().clear();
                entityManager.remove(repair);
                service.commitTransaction();
                RepairComponent.createTable(selectRepairs());
                WindowComponents.createAlert(AlertType.INFORMATION, "Repair had beed deleted.");
                WindowComponents.sendConsoleMessage("SUCCESS: Repair has beed deleted.");
            } else
                throw new DeleteRepairException("Please insert Repair ID");
        } catch (Exception exception) {
            WindowComponents.sendConsoleMessage(exception.getMessage());
            WindowComponents.createAlert(AlertType.ERROR, exception.getMessage());
        }
    }

    public static void updateRepair() {
        try {
            String repairId = controller.getRepairIDInput().getText();
            if (repairId.isEmpty()) {
                throw new UpdateRepairException("Please insert Repair ID");
            } else {
                EntityManager entityManager = service.beginTransaction();
                Repair repair = entityManager.createQuery("from Repair r where r.id = " + repairId, Repair.class).getSingleResult();

                if (!controller.getRepairPriceInput().getText().isEmpty())
                    repair.setPriceOfDefect(controller.getRepairPriceInput().getText());

                if (!controller.getRepairDefectInput().getText().isEmpty())
                    repair.setDefect(controller.getRepairDefectInput().getText());

                if (!controller.getRepairOrderIDInput().getText().isEmpty()) {
                    Long orderId = Long.parseLong(controller.getRepairOrderIDInput().getText());
                    Order order = entityManager.createQuery("from Order o where o.id = " + orderId, Order.class).getSingleResult();
                    if (!(repair.getOrderList().contains(order))) {
                        order.addPrice(repair.getPriceOfDefect().toString());
                        repair.getOrderList().add(order);
                        order.getRepairList().add(repair);
                    }
                }
                service.commitTransaction();
                RepairComponent.createTable(selectRepairs());
                WindowComponents.createAlert(AlertType.INFORMATION, "Repair has beed updated");
                WindowComponents.sendConsoleMessage("SUCCESS: Repair has beed updated");
            }
        } catch (Exception exception) {
            WindowComponents.sendConsoleMessage(exception.getMessage());
            WindowComponents.createAlert(AlertType.ERROR, exception.getMessage());
        }
    }
}
