package relationmapping;

import org.hibernate.Hibernate;
import relationmapping.domain.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;


public class RelationMappingMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        tx.begin();

        try {
            User user1 = new User();
            user1.setUsername("user1");
            entityManager.persist(user1);

            entityManager.flush();
            entityManager.clear();

            User findUser = entityManager.getReference(User.class, user1.getId());
            System.out.println("findUser isLoaded ? " + emf.getPersistenceUnitUtil().isLoaded(findUser));
            Hibernate.initialize(findUser); // 강제 초기화
            System.out.println("findUser isLoaded ? " + emf.getPersistenceUnitUtil().isLoaded(findUser));
            System.out.println("findUser class = " + findUser.getClass());



            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            entityManager.close();
        }

        emf.close();
    }
}
