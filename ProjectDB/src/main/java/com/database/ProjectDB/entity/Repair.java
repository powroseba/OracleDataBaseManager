package com.database.ProjectDB.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Null;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by seba on 2017-05-04.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "REPAIR")
public class Repair {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REPAIR_ID")
    private Long id;

    @Column(name = "DEFECT", nullable = false)
    @Lob
    private String defect;

    @Column(name = "PRICE_OF_DEFECT", precision = 10, scale = 2, nullable = false)
    private Double priceOfDefect;

    @ManyToMany(mappedBy = "repairList")
    @Null
    private Collection<Order> orderList = new ArrayList();

    public String toString(){
        if (id.toString().equals(null))return "Brak";
        else return id.toString();
    }

    public void setDefect(String defect) {
        if (defect.isEmpty()) this.defect = null;
        else this.defect = defect;
    }

    public void setPriceOfDefect(String priceOfDefect) {
        if (priceOfDefect.isEmpty()) this.priceOfDefect = null;
        else this.priceOfDefect = Double.parseDouble(priceOfDefect);
    }

    public void setPriceOfDefect(Double priceOfDefect) {
        this.priceOfDefect = priceOfDefect;
    }
}
