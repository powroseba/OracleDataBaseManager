package com.database.ProjectDB.entity;


import com.database.ProjectDB.tools.Validation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by seba on 2017-05-03.
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "EMPLOYEE")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMPLOYEE_ID")
    private Long id;

    @Column(name = "FIRST_NAME", nullable = false, length = 15)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false, length = 20)
    private String lastName;

    @Column(name = "PHONE_NUMBER", nullable = false)
    private Long phoneNumber;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "PESEL", nullable = false)
    private String pesel;

    @Column(name = "SALARY", nullable = false, precision = 10, scale = 2)
    private BigDecimal salary;

    @OneToMany(mappedBy = "employee")
    private Collection<Order> collectionOfOrders = new ArrayList();

    @ManyToOne
    @JoinColumn(name = "ADDRESS_ID")
    private Address address;

    public void setPesel(String pesel) {
        if (pesel.isEmpty()) this.pesel = null;
        if (Validation.isValidPesel(pesel))
            this.pesel = pesel;
    }

    public void setPhoneNumber(Long phoneNumber) {
        if (phoneNumber.toString().isEmpty()) this.phoneNumber = null;
        if (Validation.isValidPhoneNumber(phoneNumber))
            this.phoneNumber = phoneNumber;
    }

    public Long getAddress() {
        return address.getId();
    }

    public String toString() {
        return id.toString();
    }

    public void setFirstName(String firstName) {
        if (firstName.isEmpty()) this.firstName = null;
        else this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        if (lastName.isEmpty()) this.lastName = null;
        else this.lastName = lastName;
    }

    public void setEmail(String email) {
        if (email.isEmpty()) this.email = null;
        else if (!(Validation.isValidEmailAddress(email))) ;
        else this.email = email;
    }

    public void setSalary(String salary) {
        if (salary.isEmpty()) this.salary = null;
        else this.salary = BigDecimal.valueOf(Double.parseDouble(salary));
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Address getAddressObject() {
        return address;
    }
}
