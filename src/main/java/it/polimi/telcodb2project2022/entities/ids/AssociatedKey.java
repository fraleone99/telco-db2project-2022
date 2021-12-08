package it.polimi.telcodb2project2022.entities.ids;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class AssociatedKey implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "optionalId")
    private int optionalId;

    @Column(name = "packageId")
    private int packageId;

    public AssociatedKey() {}
}
