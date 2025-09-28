package com.testepratico.products.repository;

import com.testepratico.products.model.Product;
import com.testepratico.users.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByUser(User user);

    Page<Product> findByUser(User user, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.user = :user AND " +
           "(LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(p.description) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<Product> findByUserAndSearch(@Param("user") User user, 
                                     @Param("search") String search, 
                                     Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.user = :user AND p.quantity <= :threshold")
    List<Product> findLowStockProducts(@Param("user") User user, @Param("threshold") Integer threshold);
}
