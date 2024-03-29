package it.polimi.telcodb2project2022.entities.materializedViewTable;

import it.polimi.telcodb2project2022.entities.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "auditingtable", schema = "telco")
@NamedQuery(name = "AuditingTable.find", query = "SELECT a FROM AuditingTable a")
public class AuditingTable {
    @Id
    private int userId;

    @NotNull
    private String username;

    @NotNull
    private String email;

    @NotNull
    private float lastAmount;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastDate;


    @OneToMany
    @PrimaryKeyJoinColumn(name = "username", referencedColumnName = "username")
    private Set<User> users;

    public String getUsername() {
        return username;
    }

    public int getUserId() {
        return userId;
    }
}
