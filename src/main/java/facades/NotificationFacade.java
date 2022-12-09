package facades;

import dtos.NotificationDTO;
import entities.Account;
import entities.Notification;
import entities.StatusType;

import javax.persistence.*;
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

    public List<NotificationDTO> getNewNotificationsForAccount(Long id) {
        List<Notification> notifications = executeWithClose(em -> {
            TypedQuery<Notification> query = em.createQuery("SELECT n FROM Notification n WHERE n.account.id = :id AND n.status = :status  ORDER BY n.createdAt", Notification.class);
            query.setParameter("id", id);
            query.setParameter("status", StatusType.NEW);
            return query.getResultList();
        });
        return NotificationDTO.listToDTOs(notifications);
    }

    public NotificationDTO createNotification(long accountId, String message, String instructions) {
        Account account = executeWithClose((em) -> em.find(Account.class, accountId));
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setInstructions(instructions);
        notification.setAccount(account);
        notification.setStatus(StatusType.NEW);
        executeInsideTransaction((em) -> {
            em.persist(notification);
        });
        return new NotificationDTO(notification);
    }

    public void updateNotificationStatus(Long id, StatusType statusType){
        List<Notification> notifications = executeWithClose((em) -> {
            TypedQuery<Notification> query = em.createQuery("SELECT n FROM Notification n WHERE n.id = :id", Notification.class);
            query.setParameter("id", id);
            return query.getResultList();
        });

        if(notifications.isEmpty()) throw new EntityNotFoundException("Could not find the notification!");

        Notification notification = notifications.get(0);
        notification.setStatus(statusType);

        executeInsideTransaction((em) -> {
            em.merge(notification);
        });
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
