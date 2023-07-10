package dev.Ecommerce.Clothes.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface BagRepository extends JpaRepository<dev.Ecommerce.Clothes.models.Bag,Integer> {

    @Query(value = "SELECT SUM(CAST(price AS DECIMAL(10,2))) FROM product", nativeQuery = true)
    Double getTotalPrice();

}
