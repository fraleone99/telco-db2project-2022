package it.polimi.telcodb2project2022.entities.materializedViewTable;

import javax.enterprise.inject.Default;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "packagePerValidityPeriod", schema = "telco")
@NamedQuery(name = "PackagePerValidityPeriod.find", query = "SELECT s FROM PackagePerValidityPeriod s")
public class PackagePerValidityPeriod implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private int packageId;

    @NotNull
    private String name;

    @Default
    private int soldNumber12;

    @Default
    private int soldNumber24;

    @Default
    private int soldNumber36;

    public String getName() {
        return name;
    }

    public int getPackageId() {
        return packageId;
    }

    public int getSoldNumber12() {
        return soldNumber12;
    }

    public int getSoldNumber24() {
        return soldNumber24;
    }

    public int getSoldNumber36() {
        return soldNumber36;
    }
}
