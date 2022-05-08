package it.polimi.telcodb2project2022.entities.materializedViewTable;

import javax.enterprise.inject.Default;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "averageOptional", schema = "telco")
@NamedQuery(name = "AverageOptional.find", query = "SELECT s FROM AverageOptional s")
public class AverageOptional implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private int packageId;

    @NotNull
    private String name;

    @Default
    private int optionals;

    @Default
    private float average;

    public int getPackageId() {
        return packageId;
    }

    public String getName() {
        return name;
    }

    public int getOptionals() {
        return optionals;
    }

    public float getAverage() {
        return average;
    }
}
