package it.polimi.telcodb2project2022.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
public class OptionalProduct implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private int id;

    @ManyToMany(mappedBy = "optionalProducts")
    private Collection<ServicePackage> servicePackages;

    @ManyToOne
    @JoinColumn(name = "usernameEmployee", referencedColumnName = "username", insertable = false, updatable = false)
    private Employee employee;
}
