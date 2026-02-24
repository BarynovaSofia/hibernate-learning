package ua.pro.barynova.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ua.pro.barynova.entity.Comment;
import ua.pro.barynova.util.HibernateUtil;

/**
 * CommentRepository - репозиторий для работы с комментариями
 * Предоставляет CRUD операции
 */
public class CommentRepository {
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    /**
     * Сохранить новый комментарий в БД
     * @param comment -> объект комментария
     * @return сохранённый комментарий с ID
     */
    public Comment save(Comment comment) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(comment);
            transaction.commit();
            System.out.println("-> Comment saved");
            return comment;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("xx Error saving comment: " + e.getMessage());
            return null;
        }
    }

    /**
     * Комментарий по ID
     * @param id -> ID комментария
     * @return объект комментария или null если не найден
     */
    public Comment findById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Comment.class, id);
        } catch (Exception e) {
            System.out.println("xx Error finding comment: " + e.getMessage());
            return null;
        }
    }

    /**
     * Обновить существующий комментарий
     * @param comment -> объект комментария с обновлёнными данными
     */
    public void update(Comment comment) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(comment);
            transaction.commit();
            System.out.println("-> Comment updated");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("xx Error updating comment: " + e.getMessage());
        }
    }

    /**
     * Удалить комментарий по ID
     * @param id - ID комментария для удаления
     */
    public void delete(int id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Comment comment = session.get(Comment.class, id);
            if (comment != null) {
                session.remove(comment);
                System.out.println("-> Comment deleted");
            } else {
                System.out.println("! Comment not found");
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("xx Error deleting comment: " + e.getMessage());
        }
    }
}
