package it.polimi.telcodb2project2022.services;

import it.polimi.telcodb2project2022.entities.Employee;
import it.polimi.telcodb2project2022.entities.OptionalProduct;
import it.polimi.telcodb2project2022.entities.Service;
import it.polimi.telcodb2project2022.entities.ServicePackage;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;

@Stateless
public class OptionalProductService {
    @PersistenceContext(unitName = "telcoEJB")
    private EntityManager em;

    public List<OptionalProduct> findByPackageId(int id){
        ServicePackage servicePackage = em.find(ServicePackage.class, id);
        return servicePackage.getOptionalProducts();
    }

    public OptionalProduct findById(int id){
        return em.find(OptionalProduct.class, id);
    }

    public List<OptionalProduct> getOptionalProducts (){
        return em.createNamedQuery("OptionalProduct.getOptionalProducts", OptionalProduct.class).getResultList();
    }

    public OptionalProduct insertOptionalProduct(String name, float MonthlyFee, Employee employee) throws PersistenceException, IllegalArgumentException {
        OptionalProduct optionalProduct = new OptionalProduct();
        optionalProduct.setName(name);
        optionalProduct.setMonthlyFee(MonthlyFee);
        optionalProduct.setEmployee(employee);

        em.persist(optionalProduct);
        return optionalProduct;
    }
}