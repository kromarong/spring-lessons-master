package ru.geekbrains.persistence;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.geekbrains.controller.repr.ProductRepr;
import ru.geekbrains.persistence.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> getAllByCategory_Id(Long categoryId);

    List<Product> getAllByCategory_Id(Long categoryId, Pageable pageable);

    @Query("select new ru.geekbrains.controller.repr.ProductRepr(p.id, p.name, p.description, p.price, p.category.id, p.category.name) " +
            "from Product p " +
            "where p.id = :id")
    Optional<ProductRepr> getProductReprById(@Param("id") Long id);
}
