package it.polimi.telcodb2project2022.entities.ids;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class BuyKey implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "userId")
    private int userId;

    @Column(name = "packageId")
    private int packageId;

    public BuyKey() {}
}
