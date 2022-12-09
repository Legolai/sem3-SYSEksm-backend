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

    public FoocleSpotDTO createFoocleSpot(long businessAccountID, String address, String city, String zipCode, String country) {
        Location location = new Location(address, city, zipCode, country);

        FoocleSpot spot =  executeWithClose(em -> {
            TypedQuery<BusinessAccount> query = em.createQuery("SELECT f FROM BusinessAccount f WHERE f.id = :id", BusinessAccount.class);
            query.setParameter("id", businessAccountID);
            TypedQuery<FoocleBusiness> query2 = em.createQuery("SELECT f FROM FoocleBusiness f WHERE f.id = :cvr", FoocleBusiness.class);
            query2.setParameter("cvr", query.getResultList().get(0).getCvr().getId());
            return new FoocleSpot(query.getResultList().get(0), query2.getResultList().get(0), location);
        });
        List<Location> responseLocation = executeWithClose(em -> {
            TypedQuery<Location> query = em.createQuery("SELECT l FROM Location l " +
                    "WHERE l.address = :address AND l.city = :city AND l.country = :country", Location.class);
            query.setParameter("address", address);
            query.setParameter("city", city);
            query.setParameter("country", country);
            return query.getResultList();
        });

        executeInsideTransaction(em -> {
            if (responseLocation.isEmpty()) {
                em.persist(location);
            } else {
                spot.setLocation(responseLocation.get(0));
            }
            em.persist(spot);
        });
        return new FoocleSpotDTO(spot);
    }

    public SpotMenuDTO createSpotMenu(String description, String pictures, String foodPrefences, LocalDateTime pickupTimeFrom, LocalDateTime pickupTimeTo, long fooclespotID) {
        FoocleSpot foocleSpot = executeWithClose(em -> em.find(FoocleSpot.class, fooclespotID));
        SpotMenu spotMenu = new SpotMenu(description, pictures, foodPrefences, pickupTimeFrom, pickupTimeTo, foocleSpot);

        executeInsideTransaction(em -> {
            em.persist(spotMenu);
        });

        return new SpotMenuDTO(spotMenu);
    }

    public long getAccountId(long id) {
        return executeWithClose(em -> {
            TypedQuery<Long> query = em.createQuery("SELECT b.account.id FROM BusinessAccount b WHERE b.id = :id", Long.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        });
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
