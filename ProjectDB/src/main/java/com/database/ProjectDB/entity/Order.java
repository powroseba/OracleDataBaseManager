package com.database.ProjectDB.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * Created by seba on 2017-05-03.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "REPAIR_ORDER")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID")
    private Long id;

    @Column(name = "PRICE", nullable = false, precision = 10, scale = 2)
    private Double price;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "DATE_OF_RECEIPT")
    @Temporal(TemporalType.DATE)
    private Date dateOfReceipt;

    @Column(name = "DATE_OF_RETURN")
    @Temporal(TemporalType.DATE)
    private Date dateOfReturn;

    @Column(name = "PHONE_MODEL", nullable = false)
    private String phoneModel;

    @ManyToOne
    @JoinColumn(name = "EMPLOYEE_ID", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "CLIENT_ID", nullable = false)
    private Client client;

    @ManyToMany
    @JoinTable(name = "ORDER_AND_REPAIR",
            joinColumns = { @JoinColumn(name = "ORDER_ID") },
            inverseJoinColumns = { @JoinColumn(name = "REPAIR_ID", nullable = true)})
    private Collection<Repair> repairList = new ArrayList();

    public Long getEmployee(){
        return employee.getId();
    }

    public Employee getEmployeeobject(){
        return employee;
    }

    public Long getClient(){
        return client.getId();
    }

    public Client getClientObject(){
        return client;
    }

    public String toString(){
        return id.toString();
    }

    public void setPrice(String price) {
        if (price.isEmpty()) this.price = 0d;
        else this.price = Double.parseDouble(price);
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setPhoneModel(String phoneModel) {
        if (phoneModel.isEmpty()) this.phoneModel = null;
        else this.phoneModel = phoneModel;
    }

    public void addPrice(String price){
        if (price.isEmpty()) this.price = this.price + 0;
        else this.price += Double.parseDouble(price);
    }

    public void subtractPrice(String price){
        if (price.isEmpty()) this.price = this.price + 0;
        else this.price = this.price - Double.parseDouble(price);
    }
}
