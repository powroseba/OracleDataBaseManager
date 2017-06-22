package com.database.ProjectDB;


import com.database.ProjectDB.DAO.ServiceDAO;
import com.database.ProjectDB.entity.*;
import com.database.ProjectDB.tools.DataConverter;

import javax.persistence.EntityManager;

/**
 * Created by seba on 2017-05-31.
 */
public class injectOldData {

    public static void injectData() {
        Employee employeeOne = new Employee();
        employeeOne.setFirstName("Jan");
        employeeOne.setLastName("Kowalski");
        employeeOne.setPhoneNumber(Long.valueOf(123123123L));
        employeeOne.setPesel("90022203333");
        employeeOne.setEmail("jankow@mail.com");
        employeeOne.setSalary("2530.32");
        Order orderOne = new Order();
        orderOne.setStatus(Status.INPROGRESS);
        orderOne.setDateOfReceipt(DataConverter.getTodayDate());
        orderOne.setPrice("450.0D");
        orderOne.setPhoneModel("Huawei P8 Lite");
        Client clientOne = new Client();
        clientOne.setFirstName("Janusz");
        clientOne.setLastName("Nowak");
        clientOne.setPhoneNumber(Long.valueOf(321321321L));
        Address addressOne = new Address();
        addressOne.setCity("Rrzeszow");
        addressOne.setHomeNumber("12 a");
        addressOne.setStreet("Kolorowa");
        addressOne.setPinCode("23-141");
        Repair repairOne = new Repair();
        repairOne.setDefect("USB slot replacement");
        repairOne.setPriceOfDefect(Double.valueOf(120.0D));
        Repair repairTwo = new Repair();
        repairTwo.setDefect("Jack replacement ");
        repairTwo.setPriceOfDefect(Double.valueOf(60.0D));
        ServiceDAO service = ServiceDAO.getInstance();
        EntityManager entityManager = service.beginTransaction();
        employeeOne.getCollectionOfOrders().add(orderOne);
        orderOne.setEmployee(employeeOne);
        clientOne.getCollectionOfOrders().add(orderOne);
        orderOne.setClient(clientOne);
        clientOne.setAddress(addressOne);
        employeeOne.setAddress(addressOne);
        addressOne.getAddressesOfClients().add(clientOne);
        addressOne.getAddressesOfEmployers().add(employeeOne);
        orderOne.getRepairList().add(repairOne);
        orderOne.getRepairList().add(repairTwo);
        repairOne.getOrderList().add(orderOne);
        repairTwo.getOrderList().add(orderOne);
        entityManager.persist(employeeOne);
        entityManager.persist(clientOne);
        entityManager.persist(addressOne);
        entityManager.persist(repairOne);
        entityManager.persist(repairTwo);
        entityManager.persist(orderOne);
        service.commitTransaction();
    }
}
