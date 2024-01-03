package relationmapping;

import relationmapping.domain.Locker;
import relationmapping.domain.Movie;
import relationmapping.domain.Team;
import relationmapping.domain.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;


public class RelationMappingMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        tx.begin();

        try {
            User user = new User();
            user.setUsername("user1");
            user.setCreateBy("Kim");
            user.setCreateDate(LocalDateTime.now());

            entityManager.persist(user);

            entityManager.flush();
            entityManager.clear();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            entityManager.close();
        }

        emf.close();
    }
}
