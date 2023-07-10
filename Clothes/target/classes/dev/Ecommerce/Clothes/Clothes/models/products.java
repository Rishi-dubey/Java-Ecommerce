package dev.Ecommerce.Clothes.models;


import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "product")
public class products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "category")
    private String category;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private String price;

    @Column(name = "image")
    private String image;
}
