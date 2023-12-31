package hellojpa;

import relationmapping.domain.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;


public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        tx.begin();

        try {

            CriteriaBuilder cb = entityManager.getCriteriaBuilder();

            CriteriaQuery<User> query = cb.createQuery(User.class);

            Root<User> u = query.from(User.class);

            CriteriaQuery<User> criteriaQuery = query.select(u).where(cb.equal(u.get("username"), "kim"));
            List<User> resultList = entityManager.createQuery(criteriaQuery).getResultList();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            entityManager.close();
        }

        emf.close();

    }
}
