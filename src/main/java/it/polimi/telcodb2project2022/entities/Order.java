package it.polimi.telcodb2project2022.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "order", schema = "telco")
@NamedQuery(name = "Order.findInvalidOrderByUser",
        query = "SELECT o FROM Order o, User u WHERE u.username=?1 and o.isValid = false ")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private int id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfOrder;

    private float totalValue;

    @Temporal(TemporalType.DATE)
    private Date startDate;

    private boolean isValid;

    @NotNull
    private int duration;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "optionalSelected",
            joinColumns = @JoinColumn(name = "orderId"),
            inverseJoinColumns = @JoinColumn(name = "optionalId"))
    private List<OptionalProduct> selectedOptional = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER) //eager because we need to fetch also the services contained in the SP
    @JoinColumn(name = "idPackage", referencedColumnName = "id", updatable = false)
    private ServicePackage servicePackage;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "idBuyer", referencedColumnName = "id", updatable = false)
    private User user;

    //@ManyToOne
    //private ServiceActivationSchedule serviceActivationSchedule;

    public Order(){};

    public Order(Date dateOfOrder, Date startDate, int duration, List<OptionalProduct> selectedOptional, ServicePackage servicePackage, User user) {
        this.dateOfOrder = dateOfOrder;
        this.startDate = startDate;
        this.duration = duration;
        this.selectedOptional = selectedOptional;
        this.servicePackage = servicePackage;
        this.user = user;

        float costOfOptional = 0;
        float costOfServicePackage = 0;

        if(duration == 12)
            costOfServicePackage = servicePackage.getMonthlyFee12() * 12;
        else if(duration == 24)
            costOfServicePackage = servicePackage.getMonthlyFee24() * 24;
        else
            costOfServicePackage = servicePackage.getMonthlyFee36() * 36;

        for(OptionalProduct p: selectedOptional){
            costOfOptional += p.getMonthlyFee();
        }
        costOfOptional = costOfOptional*duration;

        totalValue = costOfOptional + costOfServicePackage;
    }

    public int getId() {
        return id;
    }

    public Date getDateOfOrder() {
        return dateOfOrder;
    }

    public float getTotalValue() {
        return totalValue;
    }

    public Date getStartDate() {
        return startDate;
    }

    public boolean isValid() {
        return isValid;
    }

    public int getDuration() {
        return duration;
    }

    public void AddOptionalProduct(OptionalProduct optionalProduct) {
        selectedOptional.add(optionalProduct);
        optionalProduct.getOrders().add(this);
    }

    public List<OptionalProduct> getSelectedOptional() {
        return selectedOptional;
    }

    public ServicePackage getServicePackage() {
        return servicePackage;
    }

    public User getUser() {
        return user;
    }

    public void setDateOfOrder(Date dateOfOrder) {
        this.dateOfOrder = dateOfOrder;
    }

    public void setTotalValue(float totalValue) {
        this.totalValue = totalValue;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }


    public void setServicePackage(ServicePackage servicePackage) {
        this.servicePackage = servicePackage;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
