package it.polimi.telcodb2project2022.services;

import it.polimi.telcodb2project2022.entities.Service;
import it.polimi.telcodb2project2022.entities.ServicePackage;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class ServService {
    @PersistenceContext(unitName = "telcoEJB")
    private EntityManager em;

     public List<Service> findByPackageId(int id){
         ServicePackage servicePackage = em.find(ServicePackage.class, id);
         return servicePackage.getServices();
     }
}
