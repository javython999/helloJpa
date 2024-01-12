package jpql;

import jpql.domain.Client;
import jpql.domain.ClientType;
import jpql.domain.Company;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class pathexpressionmain {

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


            String query = "select c.company.name from Client c";

            List<String> resultList = entityManager.createQuery(query, String.class).getResultList();

            for (String companyname : resultList) {
                System.out.println("companyname = " + companyname);
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
