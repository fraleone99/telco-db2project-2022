package it.polimi.telcodb2project2022.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "employee", schema = "telco")
@NamedQuery(name = "Employee.checkCredentials", query = "SELECT r FROM Employee r  WHERE r.username = ?1 and r.password = ?2")
public class Employee implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String username;

    private String password;

    @OneToMany(mappedBy = "usernameEmployee")
    private Set<ServicePackage> createdServicePackages;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
    private Set<OptionalProduct> optionalProducts;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public Set<ServicePackage> getCreatedServicePackages() {
        return createdServicePackages;
    }

    public Set<OptionalProduct> getOptionalProducts() {
        return optionalProducts;
    }

    public String getPassword() {
        return password;
    }
}
