package com.example.demo.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "\"order\"")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    long id;

    @Column(name = "delivery_address")
    String deliveryAddress;

    @Column(name = "order_date")
    String orderDate;

    @Column(name = "total")
    int total;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
    @JoinColumn(name = "customerId")
    Customer customer;

    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    List<OrderLine> orderLines;


    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    List<CardPayment> cardPaymentList;


    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    List<ChequePayment> chequePaymentList;

    public List<CardPayment> getCardPaymentList() {
        return cardPaymentList;
    }

    public void setCardPaymentList(List<CardPayment> cardPaymentList) {
        for(CardPayment cardPayment: cardPaymentList){
            cardPayment.setOrder(this);
        }
        this.cardPaymentList = cardPaymentList;
    }

    public List<ChequePayment> getChequePaymentList() {
        return chequePaymentList;
    }

    public void setChequePaymentList(List<ChequePayment> chequePaymentList) {
        for(ChequePayment chequePayment: chequePaymentList){
            chequePayment.setOrder(this);
        }
        this.chequePaymentList = chequePaymentList;
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLine> orderLines) {
        for(OrderLine line: orderLines){
            line.setOrder(this);
        }
        this.orderLines = orderLines;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
