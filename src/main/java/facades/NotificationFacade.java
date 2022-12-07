package facades;

import dtos.NotificationDTO;
import entities.Notification;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class NotificationFacade {

    private static EntityManagerFactory emf;
    private static NotificationFacade instance;

    private NotificationFacade() {
    }

    public static NotificationFacade getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new NotificationFacade();
        }
        return instance;
    }

    public List<NotificationDTO> getNotificationsForAccount(Long id) {
        List<Notification> notifications = executeWithClose(em -> {
            TypedQuery<Notification> query = em.createQuery("SELECT n FROM Notification n WHERE n.account.id = :id ORDER BY n.createdAt", Notification.class);
            query.setParameter("id", id);
            return query.getResultList();
        });
        return NotificationDTO.listToDTOs(notifications);
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
