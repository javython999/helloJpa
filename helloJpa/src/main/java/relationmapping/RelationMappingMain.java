package relationmapping;

import hellojpa.PrimaryKey;
import relationmapping.domain.Team;
import relationmapping.domain.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class RelationMappingMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        tx.begin();

        try {
            /*
            Team team = new Team();
            entityManager.persist(team);
            entityManager.flush();
            entityManager.clear();


            User user = new User();
            user.setTeam(team);
            entityManager.persist(user);

            entityManager.flush();
            entityManager.clear();
            */
            User findUser = entityManager.find(User.class, 2L);

            System.out.println("user = 2L, Team = " + findUser.getTeam().getId());



            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            entityManager.close();
        }

        emf.close();
    }
}
