package dev.Ecommerce.Clothes.models;

import jakarta.persistence.*;
import lombok.Data;


import javax.validation.constraints.Pattern;
import java.lang.reflect.Array;
import java.util.List;

@Data
@Entity
public class Confirm {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "orderID")
    private int orderFinalID;

    @ElementCollection
    private List<Integer> pIDs;

    @Column(name = "price")
    private String price;

    @Column(name = "timestamp")
    private String timestamp;

    @Column(name = "email")
    private String email;

    @Column(name = "cName")
    private String name;

    @Column(name = "address")
    private String address;

    @Transient
    @Pattern(regexp="(^$|[0-9]{10})",message = "Number must be 10 digit")
    private String credit;

}
