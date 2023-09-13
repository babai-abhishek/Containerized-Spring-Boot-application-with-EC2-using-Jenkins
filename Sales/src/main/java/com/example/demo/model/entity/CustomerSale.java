package com.example.demo.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
@Table(name = "customer_vs_total_sale")
public class CustomerSale {

    @Id
    @Column(name = "customer_id")
    long customer_id;

    @Min(value = 0, message = "Total sale field can't be missing or blank or negative value")
    @Column(name = "total_sale")
    int total_sale;

    @OneToOne(cascade = CascadeType.MERGE)
    @MapsId
    @JsonBackReference
    private Customer customer;

    public CustomerSale() {
        super();
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public long getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(long customer_id) {
        this.customer_id = customer_id;
    }

    public int getTotal_sale() {
        return total_sale;
    }

    public void setTotal_sale(int total_sale) {
        this.total_sale = total_sale;
    }
}
