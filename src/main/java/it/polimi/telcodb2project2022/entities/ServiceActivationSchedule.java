package it.polimi.telcodb2project2022.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "serviceactivationschedule", schema = "telco")
public class ServiceActivationSchedule {

    @Id
    private int orderId;

    @OneToMany
    @PrimaryKeyJoinColumn(name = "orderId", referencedColumnName = "orderId")
    private Set<Order> orders;
}
