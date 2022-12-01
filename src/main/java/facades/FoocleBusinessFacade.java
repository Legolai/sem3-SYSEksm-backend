package facades;

import dtos.*;
import entities.*;
import security.errorhandling.AuthenticationException;

import javax.persistence.*;
import java.time.LocalDateTime;
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
        Account account = new Account(businessAccountEmail, phoneNumber, password, firstname, lastname);
        BusinessAccount businessAccount = new BusinessAccount(true, account, foocleBusiness);

        executeInsideTransaction(em -> {
            em.persist(location);
            em.persist(foocleBusiness);
            em.persist(account);
            em.persist(businessAccount);
        });
//        executeInsideTransaction(em -> em.persist(location));
//        executeInsideTransaction(em -> em.persist(foocleBusiness));
//        executeInsideTransaction(em -> em.persist(account));
//        executeInsideTransaction(em -> em.persist(businessAccount));

        return new FoocleBusinessDTO(foocleBusiness);
    }

    public FoocleSpotDTO createFoocleSpot(String businessAccountID, String cvr, String address, String city, String zipCode, String country) {

        BusinessAccount bAccount = executeWithClose(em -> em.find(BusinessAccount.class, businessAccountID));
        FoocleBusiness foocleBusiness = executeWithClose(em -> em.find(FoocleBusiness.class, cvr));

        Location location = new Location(address, city, zipCode, country);
        FoocleSpot spot = new FoocleSpot(bAccount, foocleBusiness, location);

        List<Location> response = executeWithClose(em -> {
            TypedQuery<Location> query = em.createQuery("SELECT l FROM Location l WHERE l.address = :address", Location.class);
            query.setParameter("address", address);
            return query.getResultList();
        });
        executeInsideTransaction(em -> {
            if (response.isEmpty()) {
                em.persist(location);
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
