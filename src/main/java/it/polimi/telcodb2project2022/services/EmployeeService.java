package it.polimi.telcodb2project2022.services;

import it.polimi.telcodb2project2022.entities.Employee;
import it.polimi.telcodb2project2022.entities.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

@Stateless
public class EmployeeService {
    @PersistenceContext(unitName = "telcoEJB")
    private EntityManager em;

    public EmployeeService(){
    }

    public Employee insertEmployee(String username, String password) throws PersistenceException, IllegalArgumentException{
        Employee employee = new Employee();
        employee.setPassword(password);
        employee.setUsername(username);

        em.persist(employee);
        return employee;
    }

}
