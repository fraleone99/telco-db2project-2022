package it.polimi.telcodb2project2022.entities;

import it.polimi.telcodb2project2022.entities.ids.AssociatedKey;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class ComposedOf implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private AssociatedKey id;

    @ManyToOne
    @MapsId("serviceId")
    @JoinColumn(name = "serviceId")
    private OptionalProduct optionalProduct;

    @ManyToOne
    @MapsId("packageId")
    @JoinColumn(name = "packageId")
    private ServicePackage servicePackage;
}

