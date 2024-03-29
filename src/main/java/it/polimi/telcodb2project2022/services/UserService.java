package it.polimi.telcodb2project2022.services;

import it.polimi.telcodb2project2022.entities.Order;
import it.polimi.telcodb2project2022.entities.User;
import it.polimi.telcodb2project2022.entities.materializedViewTable.AuditingTable;
import it.polimi.telcodb2project2022.entities.materializedViewTable.ValueOfSales;
import it.polimi.telcodb2project2022.exceptions.CredentialsException;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;

@Stateless
public class UserService {
    @PersistenceContext(unitName = "telcoEJB")
    private EntityManager em;

    public UserService(){
    }

    public User checkCredentials(String usrn, String pwd) throws CredentialsException, NonUniqueResultException {
        List<User> uList;
        try {
            uList = em.createNamedQuery("User.checkCredentials", User.class).setParameter(1, usrn)
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

    public User insertUser(String username, String email, String password, boolean isInsolvent) throws PersistenceException, IllegalArgumentException{
        User user = new User();
        user.setInsolvent(isInsolvent);
        user.setEmail(email);
        user.setPassword(password);
        user.setUsername(username);

        em.persist(user);
        return user;
    }

    public Boolean isNameTaken(String username) {
        List<User> userList = em.createNamedQuery("User.findByName", User.class)
                .setParameter(1, username)
                .getResultList();

        return !(userList == null || userList.isEmpty());
    }

    public User findById(String username) {
        return (em.find(User.class, username));
    }

    public void setInsolvent(User u){
        u.setInsolvent(true);
        em.merge(u);
    }

    public void setLastFailedOrder(User u, Order o){
        u.setLastFailedOrder(o);
        em.merge(u);
    }

    public void increaseFailedPayment(User u){
        int failed = u.getNumberFailedPayment();
        u.setNumberFailedPayment(failed + 1);

        em.merge(u);
    }

    public List<User> getInsolventUsers() {
        List<User> users = null;
        users = em.createNamedQuery("User.insolvent", User.class).getResultList();
        for(User u : users) {
            em.refresh(u);
        }
        return users;
    }

    public List<AuditingTable> getAlerts() {
        List<AuditingTable> auditingTables = null;
        auditingTables = em.createNamedQuery("AuditingTable.find", AuditingTable.class).getResultList();
        for(AuditingTable a : auditingTables) {
            em.refresh(a);
        }
        return auditingTables;
    }

}
