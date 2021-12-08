package it.polimi.telcodb2project2022.entities.ids;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ComposedKey implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "serviceId")
    private int serviceId;

    @Column(name = "packageId")
    private int packageId;

    public ComposedKey() {}
}
