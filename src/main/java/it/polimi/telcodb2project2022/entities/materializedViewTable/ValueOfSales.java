package it.polimi.telcodb2project2022.entities.materializedViewTable;

import javax.enterprise.inject.Default;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "valueOfSales", schema = "telco")
@NamedQuery(name = "ValueOfSales.find", query = "SELECT s FROM ValueOfSales s")
public class ValueOfSales implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private int packageId;

    @NotNull
    private String name;

    @Default
    private float valueOfSalesWith;

    @Default
    private float valueOfSalesWithout;

    public int getPackageId() {
        return packageId;
    }

    public String getName() {
        return name;
    }

    public float getValueOfSalesWith() {
        return valueOfSalesWith;
    }

    public float getValueOfSalesWithout() {
        return valueOfSalesWithout;
    }
}
