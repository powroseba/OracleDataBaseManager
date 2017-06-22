package com.database.ProjectDB.entity;


import com.database.ProjectDB.tools.Validation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by seba on 2017-05-04.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "ADDRESS")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ADDRESS_ID")
    private Long id;

    @Column(name = "CITY", nullable = false)
    private String city;

    @Column(name = "STREET", nullable = true)
    private String street;

    @Column(name = "PIN_CODE", nullable = false)
    private String pinCode;

    @Column(name = "HOME_NUMBER", nullable = false)
    private String homeNumber;

    @OneToMany(mappedBy = "address")
    @Null
    private Collection<Client> addressesOfClients = new ArrayList();

    @OneToMany(mappedBy = "address")
    @Null
    private Collection<Employee> addressesOfEmployers = new ArrayList();


    public void setCity(String city) {
        if (city.isEmpty()) this.city = null;
        else this.city = city;
    }

    public void setPinCode(String pinCode) {
        if (pinCode.isEmpty()) this.pinCode = null;
        if (Validation.isPinCodeCorrect(pinCode))
            this.pinCode = pinCode;
    }

    public void setHomeNumber(String homeNumber) {
        if (homeNumber.isEmpty()) this.homeNumber = null;
        if (Validation.isHomeNumberCorrect(homeNumber))
            this.homeNumber = homeNumber;
    }

    public void setStreet(String street) {
//        if (Validation.isStreetCorrent(street))
            this.street = street;
    }
}
