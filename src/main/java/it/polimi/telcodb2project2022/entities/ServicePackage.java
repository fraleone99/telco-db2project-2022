package it.polimi.telcodb2project2022.entities;



import javax.persistence.*;
import javax.swing.text.html.Option;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "servicePackage", schema = "telco")
@NamedQuery(name = "ServicePackage.findAll", query = "SELECT s FROM ServicePackage s")

public class ServicePackage implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String name;

    @NotNull
    private float monthlyFee12;

    @NotNull
    private int soldNumber;

    @NotNull
    private float monthlyFee24;

    @NotNull
    private float monthlyFee36;

    @ManyToMany(mappedBy = "servicePackages")
    private Collection<User> users;

    @ManyToOne
    @JoinColumn(name = "usernameEmployee", referencedColumnName = "username")
    private Employee usernameEmployee;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "associatedTo",
            joinColumns = @JoinColumn(name = "packageId"),
            inverseJoinColumns = @JoinColumn(name = "optionalId"))
    private List<OptionalProduct> optionalProducts = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "composedOf",
            joinColumns = @JoinColumn(name = "packageId"),
            inverseJoinColumns = @JoinColumn(name = "serviceId"))
    private List<Service> services;

    @OneToMany(mappedBy = "servicePackage")
    private Set<Order> orders;

    public float getMonthlyFee12() {
        return monthlyFee12;
    }

    public float getMonthlyFee24() {
        return monthlyFee24;
    }

    public float getMonthlyFee36() {
        return monthlyFee36;
    }

    public String getName() {
        return name;
    }

    public void setSoldNumber(int soldNumber) {
        this.soldNumber = soldNumber;
    }

    public List<Service> getServices() {
        return services;
    }

    public List<OptionalProduct> getOptionalProducts() {return optionalProducts; }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmployee(Employee employee) {
        this.usernameEmployee = employee;
        usernameEmployee.getCreatedServicePackages().add(this);
    }

    public void setMonthlyFee12(float monthlyFee12) {
        this.monthlyFee12 = monthlyFee12;
    }

    public void setMonthlyFee24(float monthlyFee24) {
        this.monthlyFee24 = monthlyFee24;
    }

    public void setMonthlyFee36(float monthlyFee36) {
        this.monthlyFee36 = monthlyFee36;
    }

    public void setOptionalProducts(List<OptionalProduct> optionalProducts) {
        this.optionalProducts = optionalProducts;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public void addOptionalProduct(OptionalProduct optionalProduct) {
        optionalProducts.add(optionalProduct);
        optionalProduct.getServicePackages().add(this);
    }

    public void addService(Service service) {
        services.add(service);
        service.getServicePackages().add(this);
    }
}
