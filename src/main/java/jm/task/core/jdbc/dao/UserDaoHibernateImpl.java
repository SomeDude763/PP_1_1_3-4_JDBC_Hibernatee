package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static String sql = "";

    public UserDaoHibernateImpl() {
    }
    @Override
    public void createUsersTable() {
        Session session = null;
        sql = "CREATE TABLE IF NOT EXISTS users(id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,name VARCHAR(100)" +
                " NOT NULL,last_name VARCHAR(100) " +
                "NOT NULL,age TINYINT NOT NULL)";
        try {
            session = Util.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createSQLQuery(sql);
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new RuntimeException(e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    @Override
    public void dropUsersTable() {
        Session session = null;
        sql = "DROP TABLE IF EXISTS users";
        try {
            session = Util.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createSQLQuery(sql);
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new RuntimeException(e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = null;
        try {
            session = Util.getSessionFactory().openSession();
            session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new RuntimeException(e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = null;
        try {
            session = Util.getSessionFactory().openSession();
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new RuntimeException(e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.getSessionFactory().openSession()) {
            return session.createQuery("FROM User", User.class).getResultList();
        }
    }
    @Override
    public void cleanUsersTable() {
        Session session = null;
        sql = "DELETE FROM User";
        try {
            session = Util.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createQuery(sql);
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new RuntimeException(e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}