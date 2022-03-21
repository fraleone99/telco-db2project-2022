package it.polimi.telcodb2project2022.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "servicepackage", schema = "telco")
@NamedQuery(name = "ServicePackage.findAll", query = "SELECT s FROM ServicePackage s")

public class ServicePackage implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private int id;

    @NotNull
    private String name;

    @NotNull
    private int validityPeriod;

    private float monthlyFee;

    @ManyToMany(mappedBy = "servicePackages")
    private Collection<User> users;

    @ManyToOne
    @JoinColumn(name = "usernameEmployee", referencedColumnName = "username", insertable = false, updatable = false)
    private Employee employee;

    @ManyToMany
    @JoinTable(name = "associatedto", joinColumns = @JoinColumn(name = "packageId"), inverseJoinColumns = @JoinColumn(name = "optionalId"))
    private Collection<OptionalProduct> optionalProducts;

    @ManyToMany
    @JoinTable(name = "composedof", joinColumns = @JoinColumn(name = "packageId"), inverseJoinColumns = @JoinColumn(name = "serviceId"))
    private List<Service> services;

    @OneToMany(mappedBy = "servicePackage")
    private Set<Order> orders;

    public int getValidityPeriod() {
        return validityPeriod;
    }

    public float getMonthlyFee() {
        return monthlyFee;
    }

    public String getName() {
        return name;
    }

    public List<Service> getServices() {
        return services;
    }

    public int getId() {
        return id;
    }
}
