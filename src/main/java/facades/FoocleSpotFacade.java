package facades;

import dtos.FoocleSpotAvailabeDTO;
import dtos.FoocleSpotDTO;
import dtos.ScoutRequestDTO;
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
        BusinessAccount bAccount = executeWithClose(em -> em.find(BusinessAccount.class, businessAccountID));
        FoocleBusiness foocleBusiness = executeWithClose(em -> em.find(FoocleBusiness.class, bAccount.getCvr().getId()));

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
    public List<FoocleSpotAvailabeDTO> getFoocleSpotsForCVR(long businessAccountID) {
        List<FoocleSpot> spots =  executeWithClose(em -> {
            TypedQuery<BusinessAccount> query = em.createQuery("SELECT f FROM BusinessAccount f WHERE f.id = :id", BusinessAccount.class);
            query.setParameter("id", businessAccountID);
            TypedQuery<FoocleSpot> query2 = em.createQuery("SELECT f FROM FoocleSpot f WHERE f.cvr.id = :cvr", FoocleSpot.class);
            query2.setParameter("cvr", query.getResultList().get(0).getCvr().getId());
            return query2.getResultList();
        });
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
    public List<SpotMenuDTO> getAllMenusForSpot(long id) {
        List<SpotMenu> spotMenuList = executeWithClose(em -> {
            TypedQuery<SpotMenu> query = em.createQuery("SELECT m FROM SpotMenu m WHERE m.fooclespot.id = :id", SpotMenu.class);
            query.setParameter("id", id);
            return query.getResultList();
        });
        return SpotMenuDTO.listToDTOs(spotMenuList);
    }
    public List<SpotMenuDTO> getAllRelevantMenusForSpot(long id) {
        List<SpotMenu> spotMenuList = executeWithClose(em -> {
            TypedQuery<SpotMenu> query = em.createQuery("SELECT m FROM SpotMenu m WHERE m.fooclespot.id = :id AND m.pickupTimeTo > :today", SpotMenu.class);
            query.setParameter("id", id);
            query.setParameter("today", LocalDateTime.now());
            return query.getResultList();
        });
        return SpotMenuDTO.listToDTOs(spotMenuList);
    }

    public List<ScoutRequestDTO> getAllRequestsForSpot(long id) {
        List<ScoutRequest> scoutRequestList = executeWithClose(em -> {
            TypedQuery<ScoutRequest> query = em.createQuery("SELECT s FROM ScoutRequest s WHERE s.spotmenu.fooclespot.id = :id ORDER BY s.createdAt", ScoutRequest.class);
            query.setParameter("id", id);
            return query.getResultList();
        });
        return ScoutRequestDTO.listToDTOs(scoutRequestList);
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

