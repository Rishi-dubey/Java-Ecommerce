package dev.Ecommerce.Clothes.models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class User {

    @Id
    @Column(name = "userid")
    private long id;
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;
}
