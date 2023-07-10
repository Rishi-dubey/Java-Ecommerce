package dev.Ecommerce.Clothes.repository;

import dev.Ecommerce.Clothes.models.products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
public interface ProductRepository extends JpaRepository<products,Integer> {

    List<products> findAll();

    List<products> findAllByCategory(String s);

    List<products> findByCategory(String category);
}
