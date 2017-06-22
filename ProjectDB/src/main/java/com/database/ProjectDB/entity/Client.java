package com.database.ProjectDB.entity;

import com.database.ProjectDB.tools.Validation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by seba on 2017-05-03.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "CLIENT")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CLIENT_ID")
    private Long id;

    @Column(name = "FIRST_NAME", nullable = false, length = 15)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false, length = 20)
    private String lastName;

    @Column(name = "PHONE_NUMBER" ,nullable = false)
    private Long phoneNumber;

    @OneToMany(mappedBy = "client")
    private Collection<Order> collectionOfOrders = new ArrayList();

    @ManyToOne
    @JoinColumn(name = "ADDRESS_ID")
    private Address address;

    public Long getAddress(){
        return address.getId();
    }

    public Address getAddressObject(){
        return address;
    }

    public void setPhoneNumber(Long phoneNumber) {
        if (phoneNumber.toString().isEmpty()) this.phoneNumber = null;
        if (Validation.isValidPhoneNumber(phoneNumber))
            this.phoneNumber = phoneNumber;
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
}
