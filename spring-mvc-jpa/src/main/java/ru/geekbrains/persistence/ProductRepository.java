package ru.geekbrains.persistence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.geekbrains.persistence.entity.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> getAllByCategory_Id(Long categoryId);
    List<Product> getAllByPrice(BigDecimal price);

    @Query(value = "Select min(price) from product;", nativeQuery = true)
    BigDecimal findMin();

    @Query(value = "Select max(price) from product;", nativeQuery = true)
    BigDecimal findMax();




}
