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

}
