package facades;

import dtos.FoocleScoutDTO;
import entities.Account;
import entities.FoocleScout;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import security.errorhandling.AuthenticationException;

import java.util.List;
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

    public long getAccountId(long id) {
        return executeWithClose(em -> {
            TypedQuery<Long> query = em.createQuery("SELECT f.account.id FROM FoocleScout f WHERE f.id = :id", Long.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        });
    }

    public FoocleScoutDTO getVeryfiedScout(String email, String password) throws AuthenticationException {
        List<FoocleScout> response = executeWithClose(em -> {
            TypedQuery<FoocleScout> query = em.createQuery("SELECT f FROM FoocleScout f WHERE f.account.email = :email", FoocleScout.class);
            query.setParameter("email", email);
            return query.getResultList();
        });
        if (response.isEmpty() || !response.get(0).getAccount().verifyPassword(password)) {
            throw new AuthenticationException("Invalid email or password");
        }
        FoocleScout scout = response.get(0);
        FoocleScoutDTO res = new FoocleScoutDTO(scout, scout.getAccount());
        res.setPassword("");
        return res;
    }

    public FoocleScoutDTO createScout(String email, String phoneNumber, String firstname, String lastname, String password) {
        Account account = new Account(email, phoneNumber, firstname, lastname, password);
        FoocleScout scout = new FoocleScout(account);

        executeInsideTransaction(em -> {
            em.persist(account);
            em.persist(scout);
        });

        return new FoocleScoutDTO(scout, account);
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
