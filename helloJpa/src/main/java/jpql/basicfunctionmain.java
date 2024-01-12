package jpql;

import jpql.domain.Client;
import jpql.domain.ClientType;
import jpql.domain.Company;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class basicfunctionmain {

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
                client.setClientname(" client" + i);
                client.setAge(10 + i);
                client.setType(ClientType.ADMIN);
                client.changeCompany(company);
                entityManager.persist(client);
            }


            entityManager.flush();
            entityManager.clear();


            //String integerQuery = "select locate('cl', c.clientname) from Client c";
            String integerQuery = "select size(c.clients) from Company c";

            List<Integer> integerList = entityManager.createQuery(integerQuery, Integer.class).getResultList();

            for (Integer integer : integerList) {
                System.out.println("integer = " + integer);
            }


            //String stringQuery = "select concat('a', 'b') from Client c";
            //String stringQuery = "select substring(c.clientname, 0, 2) from Client c";
            //String stringQuery = "select trim(c.clientname) from Client c";

            /*
            List<String> stringList = entityManager.createQuery(stringQuery, String.class).getResultList();

            for (String string : stringList) {
                System.out.println("string = " + string);
            }
            */


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
