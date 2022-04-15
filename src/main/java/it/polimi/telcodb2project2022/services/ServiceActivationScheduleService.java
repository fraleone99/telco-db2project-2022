package it.polimi.telcodb2project2022.services;

import it.polimi.telcodb2project2022.entities.*;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

@Stateless
public class ServiceActivationScheduleService {
    @PersistenceContext(unitName = "telcoEJB")
    private EntityManager em;

    public ServiceActivationScheduleService() {
    }

    public ServiceActivationSchedule insertServiceActivationSchedule(Date startDate, Date endDate, List<Service> services,
                                                                     List<OptionalProduct> optionalProducts, Order order){
        System.out.println("creating service activation schedule!");
        ServiceActivationSchedule serviceActivationSchedule= new ServiceActivationSchedule();

        for(Service s:services){
            serviceActivationSchedule.addService(s);
        }

        for(OptionalProduct p: optionalProducts){
            serviceActivationSchedule.addOptional(p);
        }

        serviceActivationSchedule.setOrder(order);
        serviceActivationSchedule.setStartDate(startDate);
        serviceActivationSchedule.setEndDate(endDate);
        //serviceActivationSchedule.setUser(user);

        em.persist(serviceActivationSchedule);

        return serviceActivationSchedule;
    }
}
