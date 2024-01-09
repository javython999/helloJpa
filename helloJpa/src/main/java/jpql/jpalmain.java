package jpql;

import jpql.domain.Client;
import relationmapping.domain.User;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class jpalmain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        tx.begin();

        try {

            Client client = new Client();
            client.setClientname("홍길동");
            client.setAge(30);
            entityManager.persist(client);

            //Query query = entityManager.createQuery("select c.clientname, c.age from Client c");
            Client singleResult = entityManager.createQuery("select c from Client c where c.clientname =: username", Client.class)
                    .setParameter("username", "홍길동")
                    .getSingleResult();
            System.out.println(singleResult.getClientname());
            


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            entityManager.close();
        }

        emf.close();

    }
}
