package ua.pro.barynova.repository;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ua.pro.barynova.entity.Like;
import ua.pro.barynova.util.HibernateUtil;

/**
 * LikeRepository - репозиторий для работы с лайками
 * Предоставляет CRUD операции
 */
public class LikeRepository {
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    /**
     * Сохранить новый лайк в БД
     * @param like - объект лайка
     * @return сохранённый лайк с ID
     */
    public Like save(Like like) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(like);
            transaction.commit();
            System.out.println("-> Like saved");
            return like;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("xx Error saving like: " + e.getMessage());
            return null;
        }
    }

    /**
     * Получить лайк по ID
     * @param id - ID лайка
     * @return объект лайка или null если не найден
     */
    public Like findById(int id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            return session.get(Like.class, id);
        } catch (Exception e) {
            System.out.println("" + e.getMessage());
            return null;
        }
    }

    /**
     * Обновить существующий лайк
     * @param like - объект лайка с обновлёнными данными
     */
    public void update(Like like) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(like);
            transaction.commit();
            System.out.println("-> Like updated");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("xx Error updating like: " + e.getMessage());
        }
    }

    /**
     * Удалить лайк по ID
     * @param id - ID лайка для удаления
     */
    public void delete(int id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Like like = session.get(Like.class, id);
            if (like != null) {
                session.remove(like);
                System.out.println("-> Like deleted");
            } else {
                System.out.println("!! Like not found");
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("xx Error deleting like: " + e.getMessage());
        }
    }
}
