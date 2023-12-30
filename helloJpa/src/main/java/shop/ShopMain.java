package shop;

import shop.domain.Member;
import shop.domain.Order;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class ShopMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        tx.begin();

        try {
            Member member = new Member();

            entityManager.persist(member);
            entityManager.flush();
            entityManager.clear();

            Order order = new Order();
            order.setMember(member);

            entityManager.persist(order);

            entityManager.flush();
            entityManager.clear();


            Order findOrder = entityManager.find(Order.class, 4);

            System.out.println(findOrder.getMember().getId());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            entityManager.close();
        }

        emf.close();

    }
}
