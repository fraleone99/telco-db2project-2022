package it.polimi.telcodb2project2022.entities.services;

import it.polimi.telcodb2project2022.entities.Service;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
public class MobileInternet extends Service implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "gigabyteNumber")
    private int gigabyteNumber;

    @Column(name = "gigabyteFee")
    private int gigabyteFee;
}
