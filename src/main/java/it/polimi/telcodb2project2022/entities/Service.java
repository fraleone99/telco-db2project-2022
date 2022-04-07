package it.polimi.telcodb2project2022.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "service", schema = "telco")
@NamedQuery(name = "Service.getServicesByType", query = "SELECT r FROM Service r  WHERE r.type = ?1")
public class Service {
    //If there are problem try to specify the discriminator column

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String type;

    private int gigabytesNumber;

    private float gigabytesFee;

    private int minutesNumber;

    private int smsNumber;

    private float minutesFee;

    private float smsFee;

    @ManyToMany(mappedBy = "services")
    private List<ServicePackage> servicePackages = new ArrayList<>();

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public int getGigabyteNumber() {
        return gigabytesNumber;
    }

    public List<ServicePackage> getServicePackages() {
        return servicePackages;
    }

    public float getGigabyteFee() {
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

    public void setType(String type) {
        this.type = type;
    }

    public void setGigabytesFee(float gigabytesFee) {
        this.gigabytesFee = gigabytesFee;
    }

    public void setGigabytesNumber(int gigabytesNumber) {
        this.gigabytesNumber = gigabytesNumber;
    }

    public void setMinutesFee(float minutesFee) {
        this.minutesFee = minutesFee;
    }

    public void setMinutesNumber(int minutesNumber) {
        this.minutesNumber = minutesNumber;
    }

    public void setSmsFee(float smsFee) {
        this.smsFee = smsFee;
    }

    public void setSmsNumber(int smsNumber) {
        this.smsNumber = smsNumber;
    }
}
