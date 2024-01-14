package jpql;

import jpql.domain.Client;
import jpql.domain.ClientType;
import jpql.domain.Company;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class namedquerymain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        tx.begin();

        try {

            Company company = new Company();
            company.setName("company1");
            entityManager.persist(company);

            Client client = new Client();
            client.setClientname("client1");
            client.setAge(10);
            client.setCompany(company);
            client.setType(ClientType.ADMIN);

            entityManager.persist(client);

            entityManager.flush();
            entityManager.clear();

            List<Client> resultList = entityManager.createNamedQuery("Client.findByClientname", Client.class)
                    .setParameter("clientname", "client1")
                    .getResultList();

            for (Client item : resultList) {
                System.out.println(item);
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
