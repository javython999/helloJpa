package jpql;

import jpql.domain.*;
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

            for (int i = 0; i < 10; i++) {
                Client client = new Client();
                client.setClientname("client" + i);
                client.setAge(10 + i);
                entityManager.persist(client);
            }


            entityManager.flush();
            entityManager.clear();

            // 엔티티 프로젝션
            List<Client> result = entityManager.createQuery("select c from Client c", Client.class)
                    .getResultList();

            // 조인(묵시적)
            List<Company> companyList1 = entityManager.createQuery("select c.company from Client c", Company.class).getResultList();
            // 조인(명시적) 명시적으로 하는 것이 좋다.
            List<Company> companyList2 = entityManager.createQuery("select com from Client c join c.company com", Company.class).getResultList();


            // 임베디드 타입 프로젝션
            List<Office> officeList = entityManager.createQuery("select j.office from Job j", Office.class).getResultList();

            // 스칼라 프로젝션
            List<ClientDTO> resultList = entityManager.createQuery("select new jpql.domain.ClientDTO(c.clientname, c.age) from Client c", ClientDTO.class).getResultList();
            for (ClientDTO clientDTO : resultList) {
                System.out.println("age = " + clientDTO.getAge());
                System.out.println("name = " + clientDTO.getClientname());
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
