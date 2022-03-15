package it.polimi.telcodb2project2022.services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ServService {
    @PersistenceContext(unitName = "telcoEJB")
    private EntityManager em;
}
