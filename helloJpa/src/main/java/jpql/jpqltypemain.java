package jpql;

import jpql.domain.Client;
import jpql.domain.ClientType;
import jpql.domain.Company;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Objects;

public class jpqltypemain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        tx.begin();

        try {

            for (int i = 0; i < 3; i++) {
                Company company = new Company();
                company.setName("company" + i);
                entityManager.persist(company);

                Client client = new Client();
                client.setClientname("client" + i);
                client.setAge(10 + i);
                client.setType(ClientType.ADMIN);
                client.changeCompany(company);
                entityManager.persist(client);
            }


            entityManager.flush();
            entityManager.clear();

            String query = "select c.clientname, 'HELLO', true from Client c where c.type = :userType";

            List<Object[]> result = entityManager.createQuery(query)
                    .setParameter("userType", ClientType.ADMIN)
                    .getResultList();

            for (Object[] objects : result) {
                System.out.println("object = " + objects[0]);
                System.out.println("object = " + objects[1]);
                System.out.println("object = " + objects[2]);
            }

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            entityManager.close();
        }

        emf.close();

    }
}
