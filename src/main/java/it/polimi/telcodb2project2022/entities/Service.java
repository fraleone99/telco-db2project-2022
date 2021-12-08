package it.polimi.telcodb2project2022.entities;

import javax.persistence.*;

@MappedSuperclass
@Table(name = "service")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Service {
    //If there are problem try to specify the discriminator column

    @Id
    private int id;
}
