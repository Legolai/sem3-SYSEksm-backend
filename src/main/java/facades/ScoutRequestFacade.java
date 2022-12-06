package facades;

import dtos.FoocleScoutDTO;
import dtos.ScoutRequestDTO;
import entities.*;
import security.errorhandling.AuthenticationException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author lam@cphbusiness.dk
 */
public class ScoutRequestFacade {

    private static EntityManagerFactory emf;
    private static ScoutRequestFacade instance;

    private ScoutRequestFacade() {
    }

    /**
     *
     * @param _emf
     * @return the instance of this facade.
     */
    public static ScoutRequestFacade getScoutRequestFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new ScoutRequestFacade();
        }
        return instance;
    }

    public ScoutRequestDTO createScoutRequest(String message, String status, long spotmenuId, long fooclescoutId) {
        SpotMenu spotmenu = executeWithClose(em -> em.getReference(SpotMenu.class, spotmenuId));
        FoocleScout foocleScout = executeWithClose(em -> em.getReference(FoocleScout.class, fooclescoutId));
        ScoutRequest scoutRequest = new ScoutRequest(message, status, spotmenu, foocleScout);

        executeInsideTransaction(em -> {
            em.persist(scoutRequest);
        });

        return new ScoutRequestDTO(scoutRequest);
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
