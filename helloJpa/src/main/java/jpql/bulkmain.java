package jpql;

import jpql.domain.Client;
import jpql.domain.ClientType;
import jpql.domain.Company;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class bulkmain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        tx.begin();

        try {

            Company companyA = new Company();
            companyA.setName("companyA");
            entityManager.persist(companyA);

            Company companyB = new Company();
            companyB.setName("companyB");
            entityManager.persist(companyB);

            for (int i = 0; i < 10; i++) {
                Client client = new Client();
                client.setClientname("client" + i);
                client.setAge(10 + i);
                client.setType(ClientType.ADMIN);

                if (i % 2 != 0) {
                    client.changeCompany(companyA);
                } else {
                    client.changeCompany(companyB);
                }
                entityManager.persist(client);
            }


            // flush 자동 호출
            String query = "update Client c set c.age = 100";
            int resultCount = entityManager.createQuery(query).executeUpdate();
            System.out.println(resultCount);

            // 영속성 컨텍스트는 반영이 안된 상태임 : 데이터 정합성 문제
            String selectQuery = "select c from Client c";
            List<Client> resultList = entityManager.createQuery(selectQuery, Client.class).getResultList();
            for (Client client : resultList) {
                System.out.println(client);
            }

            entityManager.clear();

            List<Client> afterClearList = entityManager.createQuery(selectQuery, Client.class).getResultList();
            for (Client client : afterClearList) {
                System.out.println(client);
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
