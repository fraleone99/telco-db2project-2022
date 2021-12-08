package it.polimi.telcodb2project2022.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "auditingtable", schema = "telco")
public class AuditingTable {

    @Id
    private String username;

    @OneToMany
    @PrimaryKeyJoinColumn(name = "username", referencedColumnName = "username")
    private Set<User> users;
}
