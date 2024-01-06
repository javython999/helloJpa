package value;

import value.domain.Address;
import value.domain.Period;
import value.domain.ValueMember;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

public class ValueMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        tx.begin();

        try {

            ValueMember member = new ValueMember();
            member.setUsername("홍길동");
            member.setHomeAddress(new Address("city", "street", "12345"));
            member.setWorkPeriod(
                    new Period(
                            LocalDateTime.of(2023, 1, 1, 0, 0),
                            LocalDateTime.of(2023, 12, 31, 23, 59)
                    )
            );

            entityManager.persist(member);

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
