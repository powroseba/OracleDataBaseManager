package com.database.ProjectDB;


import com.database.ProjectDB.DAO.ServiceDAO;
import com.database.ProjectDB.entity.*;
import com.database.ProjectDB.tools.DataConverter;

import javax.persistence.EntityManager;
import java.text.ParseException;

/**
 * Created by seba on 2017-05-11.
 */
public class InjectData {
    private ServiceDAO service = ServiceDAO.getInstance();

    public Employee createEmployee(String firstName, String lastName, String salary, Long phoneNumber, String email, String pesel) {
        Employee employee = new Employee();
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setSalary(salary);
        employee.setPhoneNumber(phoneNumber);
        employee.setEmail(email);
        employee.setPesel(pesel);
        return employee;
    }

    public Client createClient(String firstName, String lastName, Long phoneNumber) {
        Client client = new Client();
        client.setFirstName(firstName);
        client.setLastName(lastName);
        client.setPhoneNumber(phoneNumber);
        return client;
    }

    public Address createAddress(String city, String street, String homeNumber, String pinCode) {
        Address address = new Address();
        address.setCity(city);
        address.setStreet(street);
        address.setHomeNumber(homeNumber);
        address.setPinCode(pinCode);
        return address;
    }

    public Repair createRepair(String defect, String price) {
        Repair repair = new Repair();
        repair.setPriceOfDefect(price);
        repair.setDefect(defect);
        return repair;
    }

    public Order createOrder(String status, String dateOfReceipt, String dateOfReturn, String phoneModel, String price) throws ParseException {
        Order order = new Order();
        order.setStatus(Status.valueOf(status));
        order.setDateOfReceipt(DataConverter.convertStringToDate(dateOfReceipt));
        if (dateOfReturn.isEmpty()) order.setDateOfReturn(null);
        else order.setDateOfReturn(DataConverter.convertStringToDate(dateOfReturn));
        order.setPhoneModel(phoneModel);
        order.setPrice(price);
        return order;
    }

    public static void injectData() throws ParseException {
        InjectData injectData = new InjectData();

        Employee employee1 = injectData.createEmployee("Paweł", "Fiołek", "2300", 728901230l, "pawfiol@servise.com", "90022002917");
        Employee employee2 = injectData.createEmployee("Karol", "Kwiatek", "2550", 572039002l, "karkwiat@servise.com", "89102102911");
        Employee employee3 = injectData.createEmployee("Lucjan", "Stasicki", "2100", 720192023l, "lucjan.stasicki@servise.com", "91031502391");
        Employee employee4 = injectData.createEmployee("Daniel", "Kląb", "2230", 712029881l, "danielos@servise.com", "93091702901");
        Employee employee5 = injectData.createEmployee("Emil", "Wąs", "2410", 532910281l, "emilWas@servise.com", "87082102918");


        Client client1 = injectData.createClient("Dominika", "Pliszka", 530931029l);
        Client client2 = injectData.createClient("Czarek", "Dlugi", 533029120l);
        Client client3 = injectData.createClient("Stanisław", "Pisarz", 512920017l);
        Client client4 = injectData.createClient("Grzegorz", "Jagoda", 720918772l);
        Client client5 = injectData.createClient("Alicja", "Czarna", 712029739l);
        Client client6 = injectData.createClient("Filip", "Jozwik", 533729011l);
        Client client7 = injectData.createClient("Emilia", "Lass", 530928122l);
        Client client8 = injectData.createClient("Radoslaw", "Kalinowski", 720918227l);
        Client client9 = injectData.createClient("Halina", "Lisowska", 505390192l);
        Client client10 = injectData.createClient("Ksaweryn", "Belkot", 507810921l);


        Address address1 = injectData.createAddress("Rzeszów", "Rejtana 2", "19 a", "35-623");
        Address address2 = injectData.createAddress("Rzeszów", "Powstańców warszawy 12", "5e", "35-623");
        Address address3 = injectData.createAddress("Rzeszów", "Podwisłocze 5", "70 a", "35-623");
        Address address4 = injectData.createAddress("Rzeszów", "Pelczara 3", "18 e", "35-623");
        Address address5 = injectData.createAddress("Rzeszów", "Ofiar katynia 3e", "18 e", "35-623");
        Address address6 = injectData.createAddress("Rzeszów", "Lisa kuli 2", "24 g", "35-623");
        Address address7 = injectData.createAddress("Rzeszów", "Cieplinskiego 5", "102", "35-623");
        Address address8 = injectData.createAddress("Rzeszów", "Maczka 10", "54 f", "35-623");
        Address address9 = injectData.createAddress("Rzeszów", "Rejtana 9", "23", "35-623");
        Address address10 = injectData.createAddress("Rzeszów", "Podwislocze 2/4", "19 c", "35-623");
        Address address11 = injectData.createAddress("Rzeszów", "Graniczna 9", "29 d", "35-623");
        Address address12 = injectData.createAddress("Rzeszów", "Ofiar katynia 5", "172", "35-623");
        Address address13 = injectData.createAddress("Rzeszów", "Plac wolności", "12 e", "35-623");
        Address address14 = injectData.createAddress("Rzeszów", "Rejtana 3", "82 f", "35-623");
        Address address15 = injectData.createAddress("Rzeszów", "Mikołajczuka 7/8", "20", "35-623");


        Repair repair1 = injectData.createRepair("Battery replacement", "200");

        Repair repair2 = injectData.createRepair("Digitizer replacement", "250");
        Repair repair3 = injectData.createRepair("Glass replacement", "250");

        Repair repair4 = injectData.createRepair("GPS module repair", "150");

        Repair repair5 = injectData.createRepair("Volume keys replacement", "100");
        Repair repair6 = injectData.createRepair("Fingerprint reader repair", "230");

        Repair repair7 = injectData.createRepair("AUX slot repair", "135");

        Repair repair8 = injectData.createRepair("Rear camera repair", "215");
        Repair repair9 = injectData.createRepair("Bluetooth repair", "155");

        Repair repair10 = injectData.createRepair("Wi-Fi repair", "165");

        Repair repair11 = injectData.createRepair("Reinstall the system", "220");

        Repair repair12 = injectData.createRepair("Data recovery", "150");

        Repair repair13 = injectData.createRepair("USB slot repair", "125");

        Order order1 = injectData.createOrder("INPROGRESS", "2017-02-20", "", "LG L90", "200");
        Order order2 = injectData.createOrder("DONE", "2017-03-01", "2017-03-10", "iPhone 5", "500");
        Order order3 = injectData.createOrder("DONE", "2017-04-01", "2017-04-20", "Nokia Lumia 930 Gold", "150");
        Order order4 = injectData.createOrder("INPROGRESS", "2017-04-15", "", "Xiaomi Mi 6", "330");
        Order order5 = injectData.createOrder("INPROGRESS", "2017-04-13", "", "Samsung Galaxy C5 Pro", "135");
        Order order6 = injectData.createOrder("DONE", "2017-04-10", "2017-05-13", "Lenovo Moto G5", "370");
        Order order7 = injectData.createOrder("INPROGRESS", "2017-04-20", "2017-04-25", "HTC U11", "165");
        Order order8 = injectData.createOrder("DONE", "2017-04-03", "2017-04-20", "Honor 6C", "220");
        Order order9 = injectData.createOrder("DONE", "2017-03-20", "2017-03-29", "iPhone 6S", "150");
        Order order10 = injectData.createOrder("INPROGRESS", "2017-05-20", "", "LG K7", "125");



        //RELATIONS
        client1.setAddress(address1);
        address1.getAddressesOfClients().add(client1);

        client2.setAddress(address2);
        address2.getAddressesOfClients().add(client2);

        client3.setAddress(address3);
        address3.getAddressesOfClients().add(client3);

        client4.setAddress(address4);
        address4.getAddressesOfClients().add(client4);

        client5.setAddress(address5);
        address5.getAddressesOfClients().add(client5);

        client6.setAddress(address6);
        address6.getAddressesOfClients().add(client6);

        client7.setAddress(address7);
        address7.getAddressesOfClients().add(client7);

        client8.setAddress(address8);
        address8.getAddressesOfClients().add(client8);

        client9.setAddress(address9);
        address9.getAddressesOfClients().add(client9);

        client10.setAddress(address10);
        address10.getAddressesOfClients().add(client10);

        employee1.setAddress(address11);
        address11.getAddressesOfEmployers().add(employee1);

        employee2.setAddress(address12);
        address12.getAddressesOfEmployers().add(employee2);

        employee3.setAddress(address13);
        address13.getAddressesOfEmployers().add(employee3);

        employee4.setAddress(address14);
        address14.getAddressesOfEmployers().add(employee4);

        employee5.setAddress(address15);
        address15.getAddressesOfEmployers().add(employee5);

        repair1.getOrderList().add(order1);
        order1.getRepairList().add(repair1);

        repair2.getOrderList().add(order2);
        repair3.getOrderList().add(order2);
        order2.getRepairList().add(repair2);
        order2.getRepairList().add(repair3);

        repair4.getOrderList().add(order3);
        order3.getRepairList().add(repair4);

        repair5.getOrderList().add(order4);
        repair6.getOrderList().add(order4);
        order4.getRepairList().add(repair5);
        order4.getRepairList().add(repair6);

        repair7.getOrderList().add(order5);
        order5.getRepairList().add(repair7);

        repair8.getOrderList().add(order6);
        repair9.getOrderList().add(order6);
        order6.getRepairList().add(repair8);
        order6.getRepairList().add(repair9);

        repair10.getOrderList().add(order7);
        order7.getRepairList().add(repair10);

        repair11.getOrderList().add(order8);
        order8.getRepairList().add(repair11);

        repair12.getOrderList().add(order9);
        order9.getRepairList().add(repair12);

        repair13.getOrderList().add(order10);
        order10.getRepairList().add(repair13);

        order1.setClient(client1);
        client1.getCollectionOfOrders().add(order1);

        order2.setClient(client2);
        client2.getCollectionOfOrders().add(order2);

        order3.setClient(client3);
        client3.getCollectionOfOrders().add(order3);

        order4.setClient(client4);
        client4.getCollectionOfOrders().add(order4);

        order5.setClient(client5);
        client5.getCollectionOfOrders().add(order5);

        order6.setClient(client6);
        client6.getCollectionOfOrders().add(order6);

        order7.setClient(client7);
        client7.getCollectionOfOrders().add(order7);

        order8.setClient(client8);
        client8.getCollectionOfOrders().add(order8);

        order9.setClient(client9);
        client9.getCollectionOfOrders().add(order9);

        order10.setClient(client10);
        client10.getCollectionOfOrders().add(order10);

        order1.setEmployee(employee1);
        employee1.getCollectionOfOrders().add(order1);

        order10.setEmployee(employee1);
        employee1.getCollectionOfOrders().add(order10);

        order2.setEmployee(employee2);
        employee2.getCollectionOfOrders().add(order2);

        order3.setEmployee(employee2);
        employee2.getCollectionOfOrders().add(order3);

        order4.setEmployee(employee2);
        employee2.getCollectionOfOrders().add(order4);

        order5.setEmployee(employee3);
        employee3.getCollectionOfOrders().add(order5);

        order6.setEmployee(employee4);
        employee4.getCollectionOfOrders().add(order6);

        order7.setEmployee(employee5);
        employee5.getCollectionOfOrders().add(order7);

        order8.setEmployee(employee5);
        employee5.getCollectionOfOrders().add(order8);

        order9.setEmployee(employee5);
        employee5.getCollectionOfOrders().add(order9);
        //CONNEEEEEECTION
        ServiceDAO service = injectData.service;
        EntityManager entityManager = service.beginTransaction();



        //SECOND INJECTION
        entityManager.persist(address1);
        entityManager.persist(address2);
        entityManager.persist(address3);
        entityManager.persist(address4);
        entityManager.persist(address5);
        entityManager.persist(address6);
        entityManager.persist(address7);
        entityManager.persist(address8);
        entityManager.persist(address9);
        entityManager.persist(address10);
        entityManager.persist(address11);
        entityManager.persist(address12);
        entityManager.persist(address13);
        entityManager.persist(address14);
        entityManager.persist(address15);

        entityManager.persist(client1);
        entityManager.persist(client2);
        entityManager.persist(client3);
        entityManager.persist(client4);
        entityManager.persist(client5);
        entityManager.persist(client6);
        entityManager.persist(client7);
        entityManager.persist(client8);
        entityManager.persist(client9);
        entityManager.persist(client10);

        entityManager.persist(employee1);
        entityManager.persist(employee2);
        entityManager.persist(employee3);
        entityManager.persist(employee4);
        entityManager.persist(employee5);

        entityManager.persist(repair1);
        entityManager.persist(repair2);
        entityManager.persist(repair3);
        entityManager.persist(repair4);
        entityManager.persist(repair5);
        entityManager.persist(repair6);
        entityManager.persist(repair7);
        entityManager.persist(repair8);
        entityManager.persist(repair9);
        entityManager.persist(repair10);
        entityManager.persist(repair11);
        entityManager.persist(repair12);
        entityManager.persist(repair13);

        entityManager.persist(order1);
        entityManager.persist(order2);
        entityManager.persist(order3);
        entityManager.persist(order4);
        entityManager.persist(order5);
        entityManager.persist(order6);
        entityManager.persist(order7);
        entityManager.persist(order8);
        entityManager.persist(order9);
        entityManager.persist(order10);

        service.commitTransaction();
    }
}
