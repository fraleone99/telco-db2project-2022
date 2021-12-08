package it.polimi.telcodb2project2022.entities;



import javax.persistence.*;
import java.util.Collection;

@Entity
public class ServicePackage {

    @Id
    private int id;



    @ManyToMany(mappedBy = "servicePackages")
    private Collection<User> users;
}
