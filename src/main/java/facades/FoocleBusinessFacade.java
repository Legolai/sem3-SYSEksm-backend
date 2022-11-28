package facades;

import dtos.FoocleBusinessDTO;
import dtos.FoocleScoutDTO;
import entities.*;
import security.errorhandling.AuthenticationException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author lam@cphbusiness.dk
 */
public class FoocleBusinessFacade {

    private static EntityManagerFactory emf;
    private static FoocleBusinessFacade instance;

    private FoocleBusinessFacade() {
    }

    /**
     *
     * @param _emf
     * @return the instance of this facade.
     */
    public static FoocleBusinessFacade getFoocleBusinessFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new FoocleBusinessFacade();
        }
        return instance;
    }

    public User getVeryfiedUser(String username, String password) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        User user;
        try {
            user = em.find(User.class, username);
            if (user == null || !user.verifyPassword(password)) {
                throw new AuthenticationException("Invalid user name or password");
            }
        } finally {
            em.close();
        }
        return user;
    }

    public FoocleBusinessDTO createBusiness(String cvr, String name, String businessEmail, String description, String address, String city, String zipCode, String country, String firstname, String lastname, String businessAccountEmail, String password, String phoneNumber, String areaCode) {

        Location location = new Location(address, city, zipCode, country);
        FoocleBusiness foocleBusiness = new FoocleBusiness(cvr, name, businessEmail, description, location);
        Phone phone = new Phone(phoneNumber, areaCode);
        Account account = new Account(firstname, lastname, businessAccountEmail, password, phone);
        BusinessAccount businessAccount = new BusinessAccount(true, account, foocleBusiness);

//        executeInsideTransaction(em -> {
//            em.persist(location);
//            em.persist(foocleBusiness);
//            em.persist(phone);
//            em.persist(account);
//            em.persist(businessAccount);
//        });
        executeInsideTransaction(em -> em.persist(location));
        executeInsideTransaction(em -> em.persist(foocleBusiness));
        executeInsideTransaction(em -> em.persist(phone));
        executeInsideTransaction(em -> em.persist(account));
        executeInsideTransaction(em -> em.persist(businessAccount));

        return new FoocleBusinessDTO(foocleBusiness);
    }

    private <R> R executeWithClose(Function<EntityManager, R> action) {
        EntityManager em = emf.createEntityManager();
        R result = action.apply(em);
        em.close();
        return result;
    }
    private void executeInsideTransaction(Consumer<EntityManager> action) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            action.accept(em);
            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
    }


}
