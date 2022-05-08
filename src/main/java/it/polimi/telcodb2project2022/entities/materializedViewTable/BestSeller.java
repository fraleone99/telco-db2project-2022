package it.polimi.telcodb2project2022.entities.materializedViewTable;

import javax.enterprise.inject.Default;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "bestSeller", schema = "telco")
@NamedQuery(name = "BestSeller.findBestSoldNumber", query = "SELECT s FROM BestSeller s ORDER BY s.soldNumber DESC ")
@NamedQuery(name = "BestSeller.findBestValue", query = "SELECT s FROM BestSeller s ORDER BY s.valueOfSales DESC")
public class BestSeller implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private int optionalId;

    @NotNull
    private String name;

    @Default
    private int soldNumber;

    @Default
    private float valueOfSales;

    public int getOptionalId() {
        return optionalId;
    }

    public String getName() {
        return name;
    }

    public int getSoldNumber() {
        return soldNumber;
    }

    public float getValueOfSales() {
        return valueOfSales;
    }
}
