package it.polimi.telcodb2project2022.entities;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "service", schema = "telco")
public class Service {
    //If there are problem try to specify the discriminator column

    @Id
    private int id;

    private ServiceType type;

    private int gigabyteNumber;

    private int gigabyteFee;

    private int minutesNumber;

    private int smsNumber;

    private float minutesFee;

    private float smsFee;

    @ManyToMany(mappedBy = "services")
    private Collection<ServicePackage> servicePackages;
}
