package dev.Ecommerce.Clothes.models;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Bag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private int orderID;

    @ManyToOne(fetch = FetchType.LAZY,targetEntity = products.class)
    @JoinColumn(name = "product_id", referencedColumnName = "item")
    private products productID;

    @Column(name = "timestamp")
    private String timestamp;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "checkout")
    private boolean checkout;

    public Bag() {

    }
}
