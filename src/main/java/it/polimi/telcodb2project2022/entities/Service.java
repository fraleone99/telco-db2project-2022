package it.polimi.telcodb2project2022.entities;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "service", schema = "telco")
public class Service {
    //If there are problem try to specify the discriminator column

    @Id
    private int id;

    private String type;

    private int gigabytesNumber;

    private int gigabytesFee;

    private int minutesNumber;

    private int smsNumber;

    private float minutesFee;

    private float smsFee;

    @ManyToMany(mappedBy = "services")
    private Collection<ServicePackage> servicePackages;

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public int getGigabyteNumber() {
        return gigabytesNumber;
    }

    public int getGigabyteFee() {
        return gigabytesFee;
    }

    public int getMinutesNumber() {
        return minutesNumber;
    }

    public int getSmsNumber() {
        return smsNumber;
    }

    public float getMinutesFee() {
        return minutesFee;
    }

    public float getSmsFee() {
        return smsFee;
    }
}
