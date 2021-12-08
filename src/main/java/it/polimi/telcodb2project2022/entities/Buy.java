package it.polimi.telcodb2project2022.entities;

import it.polimi.telcodb2project2022.entities.ids.BuyKey;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Buy implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private BuyKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @MapsId("packageId")
    @JoinColumn(name = "packageId")
    private ServicePackage servicePackage;
}
