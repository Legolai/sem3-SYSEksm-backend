package facades;

import dtos.FoocleSpotAvailabeDTO;
import dtos.FoocleSpotDTO;
import dtos.SpotMenuDTO;
import entities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;


public class FoocleSpotFacade {

    private static EntityManagerFactory emf;
    private static FoocleSpotFacade instance;

    private FoocleSpotFacade() {
    }

    public static FoocleSpotFacade getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new FoocleSpotFacade();
        }
        return instance;
    }

    public List<FoocleSpotAvailabeDTO> getAllFoocleSpots() {
        List<FoocleSpot> foocleSpotList = executeWithClose(em -> {
            TypedQuery<FoocleSpot> query = em.createQuery("SELECT fs FROM FoocleSpot fs", FoocleSpot.class);
            return query.getResultList();
        });
        return FoocleSpotAvailabeDTO.listToDTOs(foocleSpotList);
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
    public List<FoocleSpotAvailabeDTO> getFoocleSpotsForCVR(long businessAccountID) {
        System.out.println("before executewith");
        List<FoocleSpot> spots =  executeWithClose(em -> {
            TypedQuery<BusinessAccount> query = em.createQuery("SELECT f FROM BusinessAccount f WHERE f.id = :id", BusinessAccount.class);
            query.setParameter("id", businessAccountID);
            TypedQuery<FoocleSpot> query2 = em.createQuery("SELECT f FROM FoocleSpot f WHERE f.cvr.id = :cvr", FoocleSpot.class);
            query2.setParameter("cvr", query.getResultList().get(0).getCvr().getId());
            return query2.getResultList();
        });
        System.out.println("after executewith");
        return FoocleSpotAvailabeDTO.listToDTOs(spots);
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

