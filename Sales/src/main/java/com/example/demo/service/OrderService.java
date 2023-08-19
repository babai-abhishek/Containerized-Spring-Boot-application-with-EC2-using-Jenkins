package com.example.demo.service;

import com.example.demo.exception.InsufficientQuantityException;
import com.example.demo.exception.OrderNotFoundExcedption;
import com.example.demo.message.MessageProducer;
import com.example.demo.model.entity.*;
import com.example.demo.model.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerSaleService saleService;

    @Autowired
    ItemService itemService;

    @Autowired
    MessageProducer producer;

    @Transactional(rollbackFor = InsufficientQuantityException.class)
    public Order registerOrder(Order order){

        //check new customer
        Customer customer = order.getCustomer();

        //if new, register first
        if(customer.getId() == 0){

            customer = customerService.registerCustomer(customer);

            producer.produceMessageOnCustomerRegistration(customer);

            order.setCustomer(customer);
        }


        //update individual item stock quantity
        List<OrderLine> orderLineList = order.getOrderLines();

        for(OrderLine orderLine : orderLineList){

            Item item = orderLine.getItem();

            int currentQuantityInStock = itemService.getItemDetail(item.getId()).getStockQuantity();

            int quantityOrdered = orderLine.getQuantity();

            if(quantityOrdered > currentQuantityInStock)
                throw new InsufficientQuantityException("Not enough quantity for "+item.getName());

            int updatedQuantity = currentQuantityInStock - quantityOrdered;

            item.setStockQuantity(updatedQuantity);

            itemService.updateItem(item);
        }

        //save order
        Order ord = orderRepository.saveOrder(order);

        //update sale info for this particular customer
        CustomerSale customerSale = customer.getSale();

        int currentSale = customerSale.getTotal_sale();

        customerSale.setTotal_sale(++currentSale);

        saleService.updateTotalSale(customerSale);

        //send messages by Kafka including customer and customer sale info
        producer.produceMessageOnUpdateCustomerSale(customerSale);

        return ord;
    }

    @Transactional
    public Order removeOrderById(long orderId){

        //check id
        if(orderRepository.getOrderByID(orderId) == null){
            throw new OrderNotFoundExcedption("No such order found !");
        }

        //remove
        Order order = orderRepository.deleteOrder(orderId);

        //update sale info for the customer
        Customer customer = order.getCustomer();

        CustomerSale customerSale = customer.getSale();

        saleService.updateTotalSale(customerSale);

        //send messages by Kafka including customer sale info
        producer.produceMessageOnUpdateCustomerSale(customerSale);

        return order;
    }

    @Transactional
    public int removeAllOrdersForCustomer(String customerMobileNo){

        //get customer
        Customer customer = customerService.getCustomer(customerMobileNo);

        //get order list
        List<Order> orderList = orderRepository.getOrderList(customer.getId());

        if(orderList.size() == 0)
            throw new OrderNotFoundExcedption("No order found for : " + customerMobileNo);

        //remove orders
        for(Order order : orderList){
            orderRepository.deleteOrder(order.getId());
        }

        //update sale info
        CustomerSale customerSale = customer.getSale();

        customerSale.setTotal_sale(0);

        saleService.updateTotalSale(customerSale);

        //send messages by Kafka including customer and customer sale info
        producer.produceMessageOnUpdateCustomerSale(customerSale);

        return orderList.size();
    }

    public List<Order> getAllOrder(String mobileNo){

        Customer customer = customerService.getCustomer(mobileNo);

        List<Order> orderList = orderRepository.getOrderList(customer.getId());

        if(orderList.size() == 0)
            throw new OrderNotFoundExcedption("No order found for " + mobileNo);

        return orderList;
    }
}
