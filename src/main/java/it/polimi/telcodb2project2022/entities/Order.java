package it.polimi.telcodb2project2022.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "order", schema = "telco")
public class Order {

    @Id
    private int id;

    @Temporal(TemporalType.DATE)
    private Date dateOfOrder;

    private float totalValue;

    @Temporal(TemporalType.DATE)
    private Date startDate;

    private boolean isValid;

    @NotNull
    private int duration;

    @ManyToOne
    @JoinColumn(name = "idPackage", referencedColumnName = "id", insertable = false, updatable = false)
    private ServicePackage servicePackage;

    @ManyToOne
    @JoinColumn(name = "idBuyer", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;

    //@ManyToOne
    //private ServiceActivationSchedule serviceActivationSchedule;


}
