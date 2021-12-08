package it.polimi.telcodb2project2022.entities.services;

import it.polimi.telcodb2project2022.entities.Service;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
public class MobilePhone extends Service implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "minutesNumber")
    private int minutesNumber;

    @Column(name = "smsNumber")
    private int smsNumber;

    @Column(name = "minutesFee")
    private float minutesFee;

    @Column(name = "smsFee")
    private float smsFee;
}
