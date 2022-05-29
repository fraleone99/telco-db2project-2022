package it.polimi.telcodb2project2022.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "serviceActivationSchedule", schema = "telco")
public class ServiceActivationSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "orderId")
    private Order order;

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date endDate;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "services",
            joinColumns = @JoinColumn(name = "activationScheduleId"),
            inverseJoinColumns = @JoinColumn(name = "serviceId"))
    private List<Service> services = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "optionals",
            joinColumns = @JoinColumn(name = "activationScheduleId"),
            inverseJoinColumns = @JoinColumn(name = "optionalId"))
    private List<OptionalProduct> products = new ArrayList<>();

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<Service> getServices() {
        return services;
    }

    public void addService(Service service) {
        services.add(service);
    }

    public List<OptionalProduct> getProducts() {
        return products;
    }

    public void addOptional(OptionalProduct product) {
        products.add(product);
    }
}
