package jpql;

import jpql.domain.Client;
import jpql.domain.ClientType;
import jpql.domain.Company;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class fetchjoinmain {

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


            entityManager.flush();
            entityManager.clear();

            /*
            String query = "select c from Client c join fetch c.company";

            List<Client> resultList = entityManager.createQuery(query, Client.class).getResultList();

            for (Client client : resultList) {
                System.out.println("client name = " + client.getClientname() + " | company name = " + client.getCompany().getName());
            }
            */

            /*
            String query = "select distinct c from Company c join fetch c.clients";

            List<Company> resultList = entityManager.createQuery(query, Company.class).getResultList();

            for (Company company : resultList) {
                System.out.println("client name = " + company.getClients().size() + " | company name = " + company.getName());
                for (Client client : company.getClients()) {
                    System.out.println("└─" + client.getClientname());
                }
            }
            */

            String query = "select c from Company c";
            List<Company> resultList = entityManager.createQuery(query, Company.class)
                    .setFirstResult(0)
                    .setMaxResults(2)
                    .getResultList();

            for (Company company : resultList) {
                System.out.println("client name = " + company.getClients().size() + " | company name = " + company.getName());
                for (Client client : company.getClients()) {
                    System.out.println("└─" + client.getClientname());
                }
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
