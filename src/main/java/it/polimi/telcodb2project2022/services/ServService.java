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

    public ServService() {}

    public List<Service> findServiceByType(String type){
        return em.createNamedQuery("Service.getServicesByType", Service.class).setParameter(1, type).getResultList();
    }

     public Service insertService(String type, int minNum, int SMSNum, float minFee, float SMSFee, int gigaNum, float gigaFee) {
        Service service = new Service();
        service.setType(type);
        service.setMinutesNumber(minNum);
        service.setMinutesFee(minFee);
        service.setSmsNumber(SMSNum);
        service.setSmsFee(SMSFee);
        service.setGigabytesNumber(gigaNum);
        service.setGigabytesFee(gigaFee);

        em.persist(service);
        return service;
     }

     public List<Service> findByPackageId(int id){
        return em.find(ServicePackage.class, id).getServices();
     }
}
