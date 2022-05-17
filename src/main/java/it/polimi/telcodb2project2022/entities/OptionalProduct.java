package it.polimi.telcodb2project2022.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "optionalproduct", schema = "telco")
@NamedQuery(name = "OptionalProduct.getOptionalProducts", query = "SELECT r FROM OptionalProduct r ")
public class OptionalProduct implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private int id;

    @Column(unique = true)
    private String name;

    private float monthlyFee;

    @ManyToMany(mappedBy = "optionalProducts")
    private List<ServicePackage> servicePackages = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "selectedOptional")
    private List<Order> orders = new ArrayList<>();

    @ManyToOne (fetch = FetchType.LAZY)
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

    public List<ServicePackage> getServicePackages() {
        return servicePackages;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMonthlyFee(float monthlyFee) {
        this.monthlyFee = monthlyFee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
        employee.getOptionalProducts().add(this);
    }
}
