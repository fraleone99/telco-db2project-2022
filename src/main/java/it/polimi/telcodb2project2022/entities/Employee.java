package it.polimi.telcodb2project2022.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.Set;

@Entity
public class Employee implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String username;

    @OneToMany(mappedBy = "employee")
    private Set<ServicePackage> createdServicePackages;

    @OneToMany(mappedBy = "employee")
    private Set<OptionalProduct> optionalProducts;
}
