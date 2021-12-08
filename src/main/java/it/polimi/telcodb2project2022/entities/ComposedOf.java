package it.polimi.telcodb2project2022.entities;

import it.polimi.telcodb2project2022.entities.ids.AssociatedKey;
import it.polimi.telcodb2project2022.entities.ids.ComposedKey;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "composedof", schema = "telco")
public class ComposedOf implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private ComposedKey id;

    @ManyToOne
    @MapsId("serviceId")
    @JoinColumn(name = "serviceId")
    private Service service;

    @ManyToOne
    @MapsId("packageId")
    @JoinColumn(name = "packageId")
    private ServicePackage servicePackage;
}

