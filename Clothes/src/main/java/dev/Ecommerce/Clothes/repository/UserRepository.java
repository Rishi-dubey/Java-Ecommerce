package dev.Ecommerce.Clothes.repository;

import dev.Ecommerce.Clothes.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer>{

    User findByUsernameAndPassword(String username, String pwd);
}
