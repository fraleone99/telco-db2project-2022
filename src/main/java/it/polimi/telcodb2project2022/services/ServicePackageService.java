package it.polimi.telcodb2project2022.services;

import it.polimi.telcodb2project2022.entities.ServicePackage;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class ServicePackageService {
    @PersistenceContext(unitName = "telcoEJB")
    private EntityManager em;

    public ServicePackageService(){}

    public List<ServicePackage> getAllServicePackages(){
        List<ServicePackage> servicePackages = null;
        servicePackages = em.createNamedQuery("ServicePackage.findAll", ServicePackage.class).getResultList();
        return servicePackages;
    }
}