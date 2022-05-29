package it.polimi.telcodb2project2022.services;

import it.polimi.telcodb2project2022.entities.Employee;
import it.polimi.telcodb2project2022.entities.User;
import it.polimi.telcodb2project2022.exceptions.CredentialsException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;

@Stateless
public class EmployeeService {
    @PersistenceContext(unitName = "telcoEJB")
    private EntityManager em;

    public EmployeeService(){
    }

    public Employee checkCredentials(String usrn, String pwd) throws CredentialsException, NonUniqueResultException {
        List<Employee> uList;
        try {
            uList = em.createNamedQuery("Employee.checkCredentials", Employee.class).setParameter(1, usrn)
                    .setParameter(2, pwd).getResultList();
        } catch (PersistenceException e) {
            throw new CredentialsException("Could not verify credentals");
        }
        if (uList.isEmpty())
            return null;
        else if (uList.size() == 1) {
            return uList.get(0);
        }
        throw new NonUniqueResultException("More than one user registered with same credentials");

    }

    public Employee findById(String username) {
        return (em.find(Employee.class, username));
    }

}
