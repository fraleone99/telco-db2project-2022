package it.polimi.telcodb2project2022.services;

import it.polimi.telcodb2project2022.entities.*;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
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

    public ServicePackage insertServicePackage(String name, float fee12, float fee24, float fee36, Employee employee) {
       ServicePackage servicePackage = new ServicePackage();

       servicePackage.setSoldNumber(0);
       servicePackage.setName(name);
       servicePackage.setMonthlyFee12(fee12);
       servicePackage.setMonthlyFee24(fee24);
       servicePackage.setMonthlyFee36(fee36);
       servicePackage.setEmployee(employee);

       em.persist(servicePackage);
       return servicePackage;
    }

    public void insertOptionalsAssociated(int id, List<Integer> selectedOptionals) {
        ServicePackage servicePackage = findById(id);
        for(Integer i : selectedOptionals) {
            servicePackage.addOptionalProduct((em.find(OptionalProduct.class, i)));
        }
        em.persist(servicePackage);
    }

    public void insertServices(int id, List<Integer> services) {
        ServicePackage servicePackage = findById(id);
        for(Integer i : services) {
            servicePackage.addService((em.find(Service.class, i)));
        }
        em.persist(servicePackage);
    }

    public ServicePackage findById(int id) {
        return em.find(ServicePackage.class, id);
    }
}