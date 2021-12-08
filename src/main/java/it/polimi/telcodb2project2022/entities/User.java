package it.polimi.telcodb2project2022.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "user", schema = "telco")
@NamedQuery(name = "User.checkCredentials", query = "SELECT r FROM User r  WHERE r.username = ?1 and r.password = ?2")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String username;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private boolean isInsolvent;

    @ManyToMany
    @JoinTable(name = "buy", joinColumns = @JoinColumn(name = "userId"), inverseJoinColumns = @JoinColumn(name = "packageId"))
    private Collection<ServicePackage> servicePackages;


    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isInsolvent() {
        return isInsolvent;
    }

    public Collection<ServicePackage> getServicePackages() {
        return servicePackages;
    }
}
