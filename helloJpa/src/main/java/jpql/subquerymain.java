package jpql;

import jpql.domain.Client;
import jpql.domain.Company;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class subquerymain {

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
                client.changeCompany(company);
                entityManager.persist(client);
            }


            entityManager.flush();
            entityManager.clear();

            String query = "select (select avg(c1.age) from Client c1) as avgAge from Client c";

            List<Double> resultList = entityManager.createQuery(query, Double.class).getResultList();
            System.out.println("!!");
            System.out.println("resultList : " + resultList);

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
