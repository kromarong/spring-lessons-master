package ru.geekbrains.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.geekbrains.persistence.CategoryRepository;
import ru.geekbrains.persistence.ProductRepository;
import ru.geekbrains.persistence.entity.Category;
import ru.geekbrains.persistence.entity.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("products")
public class ProductController {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductController(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String products(@RequestParam(name = "price", required = false) String price,
                           Model model) {
        model.addAttribute("categories", categoryRepository.findAll());


        if (price != null) {
            switch (price){
                case "all":
                    model.addAttribute("products", productRepository.findAll());
                    break;
                case "min":
                    model.addAttribute("products", productRepository.getAllByPrice(productRepository.findMin()));
                    break;
                case "max":
                    model.addAttribute("products", productRepository.getAllByPrice(productRepository.findMax()));
                    break;
                case "min_max":
                    List<Product> listMin = productRepository.getAllByPrice(productRepository.findMin());
                    List<Product> listMax = productRepository.getAllByPrice(productRepository.findMax());
                    List<Product> allResult = new ArrayList<>();
                    allResult.addAll(listMax);
                    allResult.addAll(listMin);

                    model.addAttribute("products", allResult);
                    break;
            }
        } else {
            model.addAttribute("products", productRepository.findAll());
        }

        return "products";
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String createProductFrom(@RequestParam("categoryId") Long categoryId, Model model) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalStateException("Category not found"));
        Product product = new Product();
        product.setCategory(category);
        model.addAttribute("product", product);
        return "product";
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String createProduct(@ModelAttribute("product") Product product) {
        product.setCategory(categoryRepository.findById(product.getCategoryId())
                .orElseThrow(() -> new IllegalStateException("Category not found")));
        productRepository.save(product);
        return "redirect:/categories/edit?id=" + product.getCategory().getId();
    }
}
