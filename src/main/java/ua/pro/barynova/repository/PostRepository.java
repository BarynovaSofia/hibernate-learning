package ua.pro.barynova.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ua.pro.barynova.entity.Post;
import ua.pro.barynova.util.HibernateUtil;

public class PostRepository {
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    //CREATE
    public void save(Post post) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(post);
            transaction.commit();
            System.out.println("-> Post saved: " + post.getTitle());
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("xx Error saving post: " + e.getMessage());
        }
    }

    //READ by ID
    public Post findById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Post.class, id);
        } catch (Exception e) {
            System.out.println("xx Error finding post: " + e.getMessage());
            return null;
        }
    }

    //UPDATE
    public void update(Post post) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(post);
            transaction.commit();
            System.out.println("-> Post updated: " + post.getTitle());
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("xx Error updating post: " + e.getMessage());
        }
    }

    //DELETE
    public void delete(int id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Post post = session.get(Post.class, id);
            if (post != null) {
                session.remove(post);
                System.out.println("-> Post deleted: " + post.getTitle());
            } else {
                System.out.println("! Post not found");
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("xx Error deleting post: " + e.getMessage());
        }
    }
}
