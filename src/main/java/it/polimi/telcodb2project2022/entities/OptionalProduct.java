package it.polimi.telcodb2project2022.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "optionalproduct", schema = "telco")
public class OptionalProduct implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private int id;

    private String name;

    private float monthlyFee;

    @ManyToMany(cascade = CascadeType.ALL ,mappedBy = "optionalProducts")
    private Collection<ServicePackage> servicePackages;

    @ManyToMany(mappedBy = "selectedOptional")
    private List<Order> orders = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "usernameEmployee", referencedColumnName = "username", updatable = false)
    private Employee employee;


    public String getName() {
        return name;
    }

    public float getMonthlyFee() {
        return monthlyFee;
    }

    public int getId() {
        return id;
    }

    public List<Order> getOrders() {
        return orders;
    }
}
