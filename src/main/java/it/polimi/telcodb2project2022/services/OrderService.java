package it.polimi.telcodb2project2022.services;

import it.polimi.telcodb2project2022.entities.OptionalProduct;
import it.polimi.telcodb2project2022.entities.Order;
import it.polimi.telcodb2project2022.entities.ServicePackage;
import it.polimi.telcodb2project2022.entities.User;
import it.polimi.telcodb2project2022.entities.materializedViewTable.AuditingTable;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Stateless
public class OrderService {
    @PersistenceContext(unitName = "telcoEJB")
    private EntityManager em;

    public OrderService(){}

    public Order insertOrder(int duration, boolean isValid, Date startDate,
                             List<OptionalProduct> selectedOptionals, ServicePackage servicePackage, User user){
        Order order = new Order();

        order.setDuration(duration);

        Date date = new Date();
        order.setDateOfOrder(date);

        order.setValid(isValid);

        float costOfOptional = 0;
        float costOfServicePackage = 0;

        if(duration == 12)
            costOfServicePackage = servicePackage.getMonthlyFee12() * 12;
        else if(duration == 24)
            costOfServicePackage = servicePackage.getMonthlyFee24() * 24;
        else
            costOfServicePackage = servicePackage.getMonthlyFee36() * 36;

        for(OptionalProduct p: selectedOptionals){
            costOfOptional += p.getMonthlyFee();
        }
        costOfOptional = costOfOptional*duration;
        order.setTotalValue( costOfOptional + costOfServicePackage);

        order.setStartDate(startDate);
        order.setUser(user);
        order.setServicePackage(servicePackage);


        em.persist(order);
        em.flush();
        return order;
    }

    public void insertOptionals(int id, List<Integer> selectedOptionals){
        Order order = findById(id);
        for(Integer p: selectedOptionals){
            System.out.println("Adding" + p);
            order.AddOptionalProduct((em.find(OptionalProduct.class, p)));
        }
        em.persist(order);
    }

    public void setValid(int id){
        Order order = findById(id);
        order.setValid(true);
        if(getInvalidOrderByUser(order.getUser().getId()).isEmpty()){
            User user = em.find(User.class,order.getUser().getUsername());
            user.setInsolvent(false);
            em.merge(user);
        }
        em.merge(order);
    }

    public Order findById(int id) {
        return em.find(Order.class, id);
    }

    public List<Order> getInvalidOrderByUser(int id){
        return em.createNamedQuery("Order.findInvalidOrderByUser", Order.class)
                .setParameter(1, id)
                .getResultList();
    }

    public List<Order> getSuspendedOrders() {
        List<Order> orders = null;
        orders = em.createNamedQuery("Order.findSuspendedOrder", Order.class).getResultList();
        for(Order o : orders) {
            em.refresh(o);
        }
        return orders;
    }
}
