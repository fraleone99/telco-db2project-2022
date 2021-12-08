package it.polimi.telcodb2project2022.services;

import it.polimi.telcodb2project2022.entities.User;
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

    public String checkCredentials(String usrn, String pwd) throws CredentialsException, NonUniqueResultException {
        List<User> uList = null;
        try {
            uList = em.createNamedQuery("User.checkCredentials", User.class).setParameter(1, usrn)
                    .setParameter(2, pwd).getResultList();
        } catch (PersistenceException e) {
            throw new CredentialsException("Could not verify credentals");
        }
        if (uList.isEmpty())
            return null;
        else if (uList.size() == 1) {
            User found = uList.get(0);
            return found.getUsername();
        }
        throw new NonUniqueResultException("More than one user registered with same credentials");

    }

}
