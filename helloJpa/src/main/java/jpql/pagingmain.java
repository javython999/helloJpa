package jpql;

import jpql.domain.Client;

import javax.persistence.*;
import java.util.List;

public class pagingmain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        tx.begin();

        try {

            for (int i = 0; i < 100; i++) {
                Client client = new Client();
                client.setClientname("client" + i);
                client.setAge(10 + i);
                entityManager.persist(client);
            }


            entityManager.flush();
            entityManager.clear();

            List<Client> resultList = entityManager.createQuery("select c from Client c order by c.age asc", Client.class)
                    .setFirstResult(0)
                    .setMaxResults(10)
                    .getResultList();

            for (Client client : resultList) {
                System.out.println(client.toString());
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            entityManager.close();
        }

        emf.close();

    }
}
