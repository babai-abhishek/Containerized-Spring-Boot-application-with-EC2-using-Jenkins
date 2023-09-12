package com.example.demo.model.repository;

import com.example.demo.model.entity.Order;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class OrderRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(propagation = Propagation.REQUIRED)
    public Order saveOrder(Order order) {

        entityManager.persist(order);

        return order;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Order getOrderByID(long orderId) {

        return entityManager.find(Order.class, orderId);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Order> getOrderList(long customerId) {

        Query query = entityManager.createQuery("Select o from Order o where o.customer.id = "+customerId);

        List<Order> orders = query.getResultList();

        return orders;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public Order updateOrder(Order order) {

        return entityManager.merge(order);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public Order deleteOrder(long orderId) {

        Order order = getOrderByID(orderId);

        entityManager.remove(order);

        return order;
    }
}
