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
            Address address = new Address("city", "street", "12345");

            ValueMember member1 = new ValueMember();
            member1.setUsername("홍길동");
            member1.setHomeAddress(address);
            entityManager.persist(member1);

            Address copyAddress = new Address(address.getCity(), address.getStreet(), address.getZipcode());

            ValueMember member2 = new ValueMember();
            member2.setUsername("김길동");
            member2.setHomeAddress(copyAddress);
            entityManager.persist(member2);

            member1.setHomeAddress(new Address("newCity", address.getStreet(), address.getZipcode()));
            

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
