package it.polimi.telcodb2project2022.services;

import it.polimi.telcodb2project2022.entities.*;
import it.polimi.telcodb2project2022.entities.materializedViewTable.AverageOptional;
import it.polimi.telcodb2project2022.entities.materializedViewTable.BestSeller;
import it.polimi.telcodb2project2022.entities.materializedViewTable.PackagePerValidityPeriod;
import it.polimi.telcodb2project2022.entities.materializedViewTable.ValueOfSales;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class ServicePackageService {
    @PersistenceContext(unitName = "telcoEJB")
    private EntityManager em;

    public ServicePackageService(){}

    public List<ServicePackage> getAllServicePackages(){
        List<ServicePackage> servicePackages = null;
        servicePackages = em.createNamedQuery("ServicePackage.findAll", ServicePackage.class).getResultList();
        for(ServicePackage s : servicePackages){
            em.refresh(s);
        }
        return servicePackages;
    }

    public List<PackagePerValidityPeriod> getAllPackagePerValidityPeriod() {
        List<PackagePerValidityPeriod> packagePerValidityPeriods = null;
        packagePerValidityPeriods = em.createNamedQuery("PackagePerValidityPeriod.find", PackagePerValidityPeriod.class).getResultList();
        for(PackagePerValidityPeriod p : packagePerValidityPeriods) {
            em.refresh(p);
        }
        return packagePerValidityPeriods;
    }

    public List<ValueOfSales> getAllValueOfSales() {
        List<ValueOfSales> valueOfSales = null;
        valueOfSales = em.createNamedQuery("ValueOfSales.find", ValueOfSales.class).getResultList();
        for(ValueOfSales v : valueOfSales) {
            em.refresh(v);
        }
        return valueOfSales;
    }

    public List<AverageOptional> getAllAverageOptional() {
        List<AverageOptional> averageOptionals = null;
        averageOptionals = em.createNamedQuery("AverageOptional.find", AverageOptional.class).getResultList();
        for(AverageOptional a : averageOptionals) {
            em.refresh(a);
        }
        return averageOptionals;
    }

    public BestSeller getBestSoldNumber() {
        List<BestSeller> bestSoldNumber = null;
        bestSoldNumber = em.createNamedQuery("BestSeller.findBestSoldNumber", BestSeller.class).getResultList();
        for(BestSeller b : bestSoldNumber) {
            em.refresh(b);
        }
        if(bestSoldNumber.size()>=1)
            return bestSoldNumber.get(0);
        else
            return null;
    }

    public BestSeller getBestValue() {
        List<BestSeller> bestValue = null;
        bestValue = em.createNamedQuery("BestSeller.findBestValue", BestSeller.class).getResultList();
        for(BestSeller b : bestValue) {
            em.refresh(b);
        }
        if(bestValue.size()>=1)
            return bestValue.get(0);
        else
            return null;
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