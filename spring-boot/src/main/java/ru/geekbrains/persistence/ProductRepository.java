package ru.geekbrains.persistence;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.geekbrains.controller.repr.ProductRepr;
import ru.geekbrains.persistence.entity.Category;
import ru.geekbrains.persistence.entity.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    List<Product> getAllByCategory_Id(Long categoryId);

    List<Product> getAllByCategory_Id(Long categoryId, Pageable pageable);

    @Query("select new ru.geekbrains.controller.repr.ProductRepr(p.id, p.name, p.description, p.price, p.category.id, p.category.name) " +
            "from Product p " +
            "where p.id = :id")
    Optional<ProductRepr> getProductReprById(@Param("id") Long id);

    @Query("select p, c " +
            "from Product p " +
            "left join fetch Category c on p.category.id = c.id " +
            "where (:categoryId = -1L or c.id = :categoryId) and " +
            "(:priceFrom is null or p.price >= :priceFrom) and " +
            "(:priceTo is null or p.price <= :priceTo)")
    Page<Product> filterProducts(@Param("categoryId") Long categoryId,
                                 @Param("priceFrom") BigDecimal priceFrom,
                                 @Param("priceTo") BigDecimal priceTo,
                                 Pageable pageable);

    static Specification<Product> category(Category category) {
        return (prod, cq, cb) -> cb.equal(prod.get("category"), category);
    }

    static Specification<Product> priceFrom(BigDecimal priceFrom) {
        return (prod, cq, cb) -> cb.ge(prod.get("price"), priceFrom);
    }

    static Specification<Product> priceTo(BigDecimal priceTo) {
        return (prod, cq, cb) -> cb.le(prod.get("price"), priceTo);
    }

}