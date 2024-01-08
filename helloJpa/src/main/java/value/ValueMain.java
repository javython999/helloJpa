package value;

import value.domain.Address;
import value.domain.AddressEntity;
import value.domain.Period;
import value.domain.ValueMember;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class ValueMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        tx.begin();

        try {
            ValueMember member = new ValueMember();
            member.setUsername("홍길동");
            member.setHomeAddress(new Address("home_city", "street", "1234"));

            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("피자");
            member.getFavoriteFoods().add("햄버거");

            member.getAddressHistory().add(new AddressEntity("old_city1", "old_street1", "old_zipcode1"));
            member.getAddressHistory().add(new AddressEntity("old_city2", "old_street2", "old_zipcode2"));

            entityManager.persist(member);

            entityManager.flush();
            entityManager.clear();

            System.out.println("=================== SELECT =====================");
            ValueMember findMember = entityManager.find(ValueMember.class, member.getId());

            System.out.println("=================== UPDATE =====================");
            findMember.setHomeAddress(new Address("new_city", "new_street", "new_zipcode"));

            findMember.getFavoriteFoods().remove("치킨");
            findMember.getFavoriteFoods().add("콜라");

            findMember.getAddressHistory().remove(new AddressEntity("old_city1", "old_street1", "old_zipcode1"));
            findMember.getAddressHistory().add(new AddressEntity("new_city1", "new_street1", "new_zipcode1"));

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
