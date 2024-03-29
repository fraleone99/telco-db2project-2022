package it.polimi.telcodb2project2022.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "user", schema = "telco")
@NamedQuery(name = "User.checkCredentials",
        query = "SELECT r FROM User r  WHERE r.username = ?1 and r.password = ?2")
@NamedQuery(name = "User.findByName",
        query = "SELECT u FROM User u WHERE u.username=?1")
@NamedQuery(name = "User.insolvent",
        query = "SELECT u FROM User u WHERE u.isInsolvent=true")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private int id;

    @Id
    private String username;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private boolean isInsolvent;

    @NotNull
    private int numberFailedPayment;

    @OneToOne
    @JoinColumn(name = "lastFailedId")
    private Order lastFailedOrder;

    public String getUsername() {
        return username;
    }

    public int getId() {
        return id;
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

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setInsolvent(boolean insolvent) {
        isInsolvent = insolvent;
    }

    public int getNumberFailedPayment() {
        return numberFailedPayment;
    }

    public void setNumberFailedPayment(int numberFailedPayment) {
        this.numberFailedPayment = numberFailedPayment;
    }

    public void setLastFailedOrder(Order lastFailedOrder) {
        this.lastFailedOrder = lastFailedOrder;
    }
}
