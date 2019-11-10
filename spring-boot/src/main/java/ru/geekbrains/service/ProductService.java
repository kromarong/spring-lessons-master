package ru.geekbrains.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.controller.repr.ProductFilter;
import ru.geekbrains.controller.repr.ProductRepr;
import ru.geekbrains.persistence.CategoryRepository;
import ru.geekbrains.persistence.ProductRepository;
import ru.geekbrains.persistence.entity.Category;
import ru.geekbrains.persistence.entity.Product;

import java.util.List;
import java.util.Optional;

import static ru.geekbrains.persistence.ProductRepository.*;

@Service
public class ProductService {

    private ProductRepository productRepository;

    private CategoryRepository categoryRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public Long count() {
        return productRepository.count();
    }

    @Transactional(readOnly = true)
    public List<Product> getAllByCategory_Id(Long categoryId) {
        return productRepository.getAllByCategory_Id(categoryId);
    }

    @Transactional(readOnly = true)
    public Optional<ProductRepr> getProductReprById(Long id) {
        return productRepository.getProductReprById(id);
    }

    @Transactional(readOnly = true)
    public ProductRepr getEmptyProductReprWithCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalStateException("Category not found"));
        ProductRepr productRepr = new ProductRepr();
        productRepr.setCategoryId(category.getId());
        productRepr.setCategoryName(category.getName());
        return productRepr;
    }

    @Transactional(readOnly = true)
    public Page<Product> filterProducts(ProductFilter filter) {
        Specification<Product> spec = Specification.where(null);

        spec = spec
                .and(filter.getCategoryId() != -1 ? category(new Category(1L)) : null)
                .and(filter.getPriceFrom() != null ? priceFrom(filter.getPriceFrom()) : null)
                .and(filter.getPriceTo() != null ? priceTo(filter.getPriceFrom()) : null);

        return productRepository.findAll(spec, PageRequest.of(filter.getCurrentPage(), filter.getPageSize()));
    }

    @Transactional
    public void save(ProductRepr productRepr) {
        Product product = new Product();
        product.setId(productRepr.getId());
        product.setName(productRepr.getName());
        product.setPrice(productRepr.getPrice());
        product.setDescription(productRepr.getDescription());
        product.setCategory(categoryRepository.findById(productRepr.getCategoryId())
                .orElseThrow(() -> new IllegalStateException("Category not found")));
        productRepository.save(product);
    }
}
