package jpql;

import jpql.domain.Client;
import jpql.domain.Company;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class joinmain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        tx.begin();

        try {

            for (int i = 0; i < 10; i++) {
                Company company = new Company();
                company.setName("company" + i);
                entityManager.persist(company);

                Client client = new Client();
                client.setClientname("client" + i);
                client.setAge(10 + i);
                client.changeCompany(company);
                entityManager.persist(client);
            }


            entityManager.flush();
            entityManager.clear();

            //String query = "select c from Client c inner join c.company com where com.name = 'company1'";
            //String query = "select c from Client c left join c.company com where com.name = 'company1'";
            String query = "select c from Client c, Company com where c.clientname = com.name";

            List<Client> resultList = entityManager.createQuery(query, Client.class).getResultList();

            for (Client client : resultList) {
                System.out.println(client.toString());
                System.out.println(client.getCompany().getName());
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
