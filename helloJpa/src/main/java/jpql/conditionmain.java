package jpql;

import jpql.domain.Client;
import jpql.domain.ClientType;
import jpql.domain.Company;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class conditionmain {

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
                if (i == 0) {
                    client.setClientname(null);
                } else if (i == 1){
                    client.setClientname("client" + i);
                } else if (i == 2) {
                    client.setClientname("관리자");
                } else {
                    client.setClientname("client");
                }
                client.setAge(10 + i);
                client.setType(ClientType.ADMIN);
                client.changeCompany(company);
                entityManager.persist(client);
            }


            entityManager.flush();
            entityManager.clear();

            // case
            /*
            String query = "select " +
                                "case when c.age <= 10 then '학생' " +
                                     "when c.age >= 60 then '노인' " +
                                     "else '일반' end " +
                            "from Client c ";
            */

            // coalesce
            //String query = "select coalesce(c.clientname, '이름없음') from Client c";

            // nullif
            String query = "select nullif(c.clientname, '관리자') from Client c";

            List<String> resultList = entityManager.createQuery(query, String.class).getResultList();
            for (String string : resultList) {
                System.out.println("string = " + string);
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
