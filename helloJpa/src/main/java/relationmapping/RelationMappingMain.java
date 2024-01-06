package relationmapping;

import org.hibernate.Hibernate;
import relationmapping.domain.Team;
import relationmapping.domain.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;


public class RelationMappingMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        tx.begin();

        try {

            for (int i = 0; i < 10; i++) {
                Team team = new Team();
                team.setTeamName("team" + i);
                entityManager.persist(team);

                User user = new User();
                user.setUsername("user" + i);
                user.setTeam(team);
                entityManager.persist(user);
            }


            entityManager.flush();
            entityManager.clear();

            List<User> users = entityManager.createQuery(
                    "SELECT user from User user",
                    User.class
            ).getResultList();




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
