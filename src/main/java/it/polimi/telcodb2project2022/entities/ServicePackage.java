package it.polimi.telcodb2project2022.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
public class ServicePackage implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private int id;

    @ManyToMany(mappedBy = "servicePackages")
    private Collection<User> users;

    @ManyToOne
    @JoinColumn(name = "usernameEmployee", referencedColumnName = "username", insertable = false, updatable = false)
    private Employee employee;

    @ManyToMany
    @JoinTable(name = "associatedto", joinColumns = @JoinColumn(name = "packageId"), inverseJoinColumns = @JoinColumn(name = "optionalId"))
    private Collection<OptionalProduct> optionalProducts;
}
