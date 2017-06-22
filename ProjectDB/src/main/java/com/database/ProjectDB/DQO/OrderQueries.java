package com.database.ProjectDB.DQO;

import com.database.ProjectDB.Controller;
import com.database.ProjectDB.DAO.ServiceDAO;
import com.database.ProjectDB.entity.*;
import com.database.ProjectDB.exceptions.order.DeleteOrderException;
import com.database.ProjectDB.exceptions.order.InsertOrderException;
import com.database.ProjectDB.exceptions.order.UpdateOrderException;
import com.database.ProjectDB.tools.AlertType;
import com.database.ProjectDB.tools.DataConverter;
import com.database.ProjectDB.tools.WindowComponents;
import com.database.ProjectDB.ui.components.OrderComponent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Created by seba on 2017-05-11.
 */
public class OrderQueries {
    private static Controller controller = Controller.getControllerInstance();
    private static ServiceDAO service = ServiceDAO.getInstance();


    public static ObservableList<Order> selectOrders() {
        ObservableList<Order> ordersList = FXCollections.observableArrayList();
        try {
            ordersList.clear();
            EntityManager entityManager = service.beginTransaction();
            List<Order> lista = new ArrayList();
            lista = entityManager.createQuery("from Order ", Order.class).getResultList();
            lista.stream().forEach(order ->
                    ordersList.add(order)
            );
            service.commitTransaction();
        } catch (Exception hibernate) {
            WindowComponents.createAlert(AlertType.ERROR, hibernate.getMessage());
        }
        return ordersList;
    }

    public static void insertOrder() {
        try {
            EntityManager entityManager = service.beginTransaction();
            Order order = new Order();

            order.setPrice(controller.getOrderPriceInput().getText());

            if (controller.getOrderDoneInput().isSelected()) {
                order.setStatus(Status.DONE);
            } else
                order.setStatus(Status.INPROGRESS);

            if (controller.getOrderDateOfReceipt().getValue() == null)
                order.setDateOfReceipt(DataConverter.getTodayDate());
            else
                order.setDateOfReceipt(DataConverter.convertDateFromDataPicker(controller.getOrderDateOfReceipt().getValue()));

            if (controller.getOrderDateOfReturn().getValue() == null)
                order.setDateOfReturn(null);
            else if (controller.getOrderDoneInput().isSelected()) order.setDateOfReturn(DataConverter.getTodayDate());
            else
                order.setDateOfReturn(DataConverter.convertDateFromDataPicker(controller.getOrderDateOfReturn().getValue()));

            if (controller.getOrderPhoneModelInput().getText().isEmpty())
                throw new InsertOrderException("Please insert Phone Model");
            order.setPhoneModel(controller.getOrderPhoneModelInput().getText());

            if (controller.getOrderEmployeeIDInput().getText().isEmpty())
                throw new InsertOrderException("Please insert Employee ID");
            else {
                Long employeeId = Long.parseLong(controller.getOrderEmployeeIDInput().getText());
                Employee employee = entityManager.createQuery("from Employee e where e.id = " + employeeId, Employee.class).getSingleResult();
                order.setEmployee(employee);
                employee.getCollectionOfOrders().add(order);
            }

            if (controller.getOrderClientIDInput().getText().isEmpty())
                throw new InsertOrderException("Please insert Client ID");
            else {
                Long clientId = Long.parseLong(controller.getOrderClientIDInput().getText());
                Client client = entityManager.createQuery("from Client c where c.id = " + clientId, Client.class).getSingleResult();
                order.setClient(client);
                client.getCollectionOfOrders().add(order);
            }

            if (controller.getOrderRepairIDInput().getText().isEmpty());
            else {
                Long repairId = Long.parseLong(controller.getOrderRepairIDInput().getText());
                Repair repair = entityManager.createQuery("from Repair r where r.id = " + repairId, Repair.class).getSingleResult();
                if (!order.getRepairList().contains(repair)) {
                    order.addPrice(repair.getPriceOfDefect().toString());
                    order.getRepairList().add(repair);
                    repair.getOrderList().add(order);
                }
            }

                entityManager.persist(order);
                service.commitTransaction();//comit zawsze przed selectem
                OrderComponent.createTable(OrderQueries.selectOrders());
            //info zawsze po comicie
                WindowComponents.createAlert(AlertType.INFORMATION, "Orded has beed created.");
                WindowComponents.sendConsoleMessage("SUCCESS: Order has been created.");

        } catch (Exception hibernate) {
            WindowComponents.createAlert(AlertType.ERROR, hibernate.getMessage());
        }

    }

    public static void deleteOrder() {
        try {
            EntityManager entityManager = service.beginTransaction();
            String orderId = controller.getOrderIdInput().getText();
            if (!orderId.isEmpty()) {
                                                                //parsowanie
                String query = "from Order o where o.id = " + Long.parseLong(orderId);
                Order order = entityManager.createQuery(query, Order.class).getSingleResult();
                Optional<Order> optional = Optional.ofNullable(order);
                if (optional.isPresent()) {
                    if ((!(controller.getOrderRepairIDInput().getText().isEmpty()))){
                        Repair repair = entityManager.createQuery("from Repair r where r.id = " + Long.parseLong(controller.getOrderRepairIDInput().getText()), Repair.class).getSingleResult();
                        order.getRepairList().remove(repair);
                        repair.getOrderList().remove(order);
                        service.commitTransaction();
                        OrderComponent.createTable(selectOrders());
                        return;
                    }
                    order.getRepairList().forEach(repair -> {
                        entityManager.remove(repair);
                    });
                    order.getClientObject().getCollectionOfOrders().remove(order);
                    order.getEmployeeobject().getCollectionOfOrders().remove(order);
                    entityManager.remove(order);
                    service.commitTransaction();        //comit przed select
                    OrderComponent.createTable(OrderQueries.selectOrders());
                    //info po select
                    WindowComponents.createAlert(AlertType.INFORMATION, "Order had beed deleted.");
                    WindowComponents.sendConsoleMessage("SUCCESS: Order has beed deleted.");
                }

            } else {
                throw new DeleteOrderException("Please insert Order ID");
            }
        } catch (Exception exception) {
            WindowComponents.sendConsoleMessage(exception.getMessage());
            WindowComponents.createAlert(AlertType.ERROR, exception.getMessage());
        }
    }

    public static void deleteOrder(Order order) {
        try {
            EntityManager entityManager = service.beginTransaction();
            //optional
            Optional<Order> optional = Optional.ofNullable(order);
            if (optional.isPresent()) {
                order.getRepairList().forEach(repair -> {
                    order.getClientObject().getCollectionOfOrders().remove(order);
                    order.getEmployeeobject().getCollectionOfOrders().remove(order);
                    entityManager.remove(repair);
                });
                entityManager.remove(order);
                service.commitTransaction();    //comit przed select
                OrderComponent.createTable(OrderQueries.selectOrders());
                //info po select
                WindowComponents.createAlert(AlertType.INFORMATION, "Order had beed deleted.");
                WindowComponents.sendConsoleMessage("SUCCESS: Order has beed deleted.");
            }
        } catch (Exception exception) {
            WindowComponents.sendConsoleMessage(exception.getMessage());
            WindowComponents.createAlert(AlertType.ERROR, exception.getMessage());
        }
    }

    public static void updateOrder() {
        try {
            String orderId = controller.getOrderIdInput().getText();
            if (orderId.isEmpty()) {
                throw new UpdateOrderException("Please insert Order ID");
            } else {
                EntityManager entityManager = service.beginTransaction();//parsowanie
                Order order = entityManager.createQuery("from Order o where o.id = " + Long.parseLong(orderId), Order.class).getSingleResult();

                if (!controller.getOrderPriceInput().getText().isEmpty())
                    order.setPrice(controller.getOrderPriceInput().getText());
                if (controller.getOrderDoneInput().isSelected()) order.setStatus(Status.DONE);
                if (controller.getOrderProgressInput().isSelected()) order.setStatus(Status.INPROGRESS);

                if (controller.getOrderDateOfReceipt().getValue() != null)
                    order.setDateOfReceipt(DataConverter.convertDateFromDataPicker(controller.getOrderDateOfReceipt().getValue()));

                if (controller.getOrderDoneInput().isSelected()) order.setDateOfReturn(DataConverter.getTodayDate());
                else order.setDateOfReturn(DataConverter.convertDateFromDataPicker(controller.getOrderDateOfReturn().getValue()));

                if (!controller.getOrderPhoneModelInput().getText().isEmpty()) {
                    order.setPhoneModel(controller.getOrderPhoneModelInput().getText());
                }

                if (!controller.getOrderEmployeeIDInput().getText().isEmpty()) {
                    Long employeeId = Long.parseLong(controller.getOrderEmployeeIDInput().getText());
                    Employee employee = entityManager.createQuery("from Employee e where e.id = " + employeeId, Employee.class).getSingleResult();
                    order.getEmployeeobject().getCollectionOfOrders().remove(order);
                    order.setEmployee(employee);
                    employee.getCollectionOfOrders().add(order);
                }


                if (!controller.getOrderClientIDInput().getText().isEmpty()) {
                    Long clientId = Long.parseLong(controller.getOrderClientIDInput().getText());
                    Client client = entityManager.createQuery("from Client c where c.id = " + clientId, Client.class).getSingleResult();
                    order.setClient(client);
                    client.getCollectionOfOrders().add(order);
                }


                if (!controller.getOrderRepairIDInput().getText().isEmpty()) {
                    Long repairId = Long.parseLong(controller.getOrderRepairIDInput().getText());
                    Repair repair = entityManager.createQuery("from Repair r where r.id = " + repairId, Repair.class).getSingleResult();
                    if (!order.getRepairList().contains(repair)) {
                        order.addPrice(repair.getPriceOfDefect().toString());
                        order.getRepairList().add(repair);
                        repair.getOrderList().add(order);
                    }
                }

                service.commitTransaction();//commit przed select
                OrderComponent.createTable(OrderQueries.selectOrders());
                //info po select
                WindowComponents.createAlert(AlertType.INFORMATION, "Order has beed updated");
                WindowComponents.sendConsoleMessage("SUCCESS: Order has beed updated");
            }
        } catch (Exception exception) {
            WindowComponents.sendConsoleMessage(exception.getMessage());
            WindowComponents.createAlert(AlertType.ERROR, exception.getMessage());

        }

    }
}
