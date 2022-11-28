package facades;

import dtos.FoocleScoutDTO;
import entities.Account;
import entities.FoocleScout;
import entities.Phone;
import entities.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import security.errorhandling.AuthenticationException;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author lam@cphbusiness.dk
 */
public class FoocleScoutFacade {

    private static EntityManagerFactory emf;
    private static FoocleScoutFacade instance;

    private FoocleScoutFacade() {
    }

    /**
     *
     * @param _emf
     * @return the instance of this facade.
     */
    public static FoocleScoutFacade getFoocleScoutFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new FoocleScoutFacade();
        }
        return instance;
    }

    public FoocleScoutDTO getVeryfiedScout(String email, String password) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        FoocleScoutDTO res;
        try {
            Account account = em.find(Account.class, email);
            if (account == null || !account.verifyPassword(password)) {
                throw new AuthenticationException("Invalid email or password");
            }
            Phone phone = em.find(Phone.class, account.getNumber());
            FoocleScout scout = em.find(FoocleScout.class, account.getId());
            res = new FoocleScoutDTO(scout, account, phone);
            res.setPassword("");
        } finally {
            em.close();
        }
        return res;
    }

    public FoocleScoutDTO createScout(String firstname, String lastname, String email, String password, String phoneNumber, String areaCode) {
        Phone phone = new Phone(phoneNumber, areaCode);
        Account account = new Account(firstname, lastname, email, password, phone);
        FoocleScout scout = new FoocleScout(account);

        executeInsideTransaction(em -> {
            em.persist(phone);
            em.persist(account);
            em.persist(scout);
        });

        return new FoocleScoutDTO(scout, account, phone);
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
