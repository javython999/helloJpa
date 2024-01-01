package relationmapping;

import relationmapping.domain.Locker;
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

            Locker locker = new Locker();
            locker.setName("locker1");
            entityManager.persist(locker);

            User user = new User();
            user.setUsername("username1");
            user.setLocker(locker);
            entityManager.persist(user);


            Team team = new Team();
            team.setTeamName("team1");
            team.getUserList().add(user);
            entityManager.persist(team);

            System.out.println("locker name = " + user.getLocker().getName());




            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            entityManager.close();
        }

        emf.close();
    }
}
