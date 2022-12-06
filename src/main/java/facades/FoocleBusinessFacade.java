package facades;

import dtos.*;
import entities.*;
import security.errorhandling.AuthenticationException;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author lam@cphbusiness.dk
 */
public class FoocleBusinessFacade {

    private static EntityManagerFactory emf;
    private static FoocleBusinessFacade instance;

    private FoocleBusinessFacade() {}
    public static FoocleBusinessFacade getFoocleBusinessFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new FoocleBusinessFacade();
        }
        return instance;
    }


    public BusinessAccountDTO getVeryfiedBusinessAccount(String email, String password) throws AuthenticationException {
        List<BusinessAccount> response = executeWithClose(em -> {
            TypedQuery<BusinessAccount> query = em.createQuery("SELECT f FROM BusinessAccount f WHERE f.account.email = :email", BusinessAccount.class);
            query.setParameter("email", email);
            return query.getResultList();
        });
        if (response.isEmpty() || !response.get(0).getAccount().verifyPassword(password)) {
            throw new AuthenticationException("Invalid email or password");
        }
        BusinessAccount bAccount = response.get(0);
        BusinessAccountDTO res = new BusinessAccountDTO(bAccount, bAccount.getAccount());
        res.setPassword("");
        return res;
    }

    public FoocleBusinessDTO createBusiness(String cvr, String name, String businessEmail, String businessPhoneNumber, String description, String address, String city, String zipCode, String country, String businessAccountEmail, String phoneNumber, String firstname, String lastname, String password) {
        Location location = new Location(address, city, zipCode, country);
        FoocleBusiness foocleBusiness = new FoocleBusiness(cvr, name, businessEmail, businessPhoneNumber, description, location);
        Account account = new Account(businessAccountEmail, phoneNumber, firstname, lastname, password);
        BusinessAccount businessAccount = new BusinessAccount(true, account, foocleBusiness);

        executeInsideTransaction(em -> {
            em.persist(location);
            em.persist(foocleBusiness);
            em.persist(account);
            em.persist(businessAccount);
        });

        return new FoocleBusinessDTO(foocleBusiness);
    }


    private <R> R executeWithClose(Function<EntityManager, R> action) {
        EntityManager em = emf.createEntityManager();
        R result = action.apply(em);
        em.close();
//        System.out.println("closing WithClose transaction");
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
//            System.out.println("closing transaction");
            em.close();
        }
    }
}
