package ru.geekbrains.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.geekbrains.controller.error.ResourceNotFoundException;
import ru.geekbrains.controller.repr.ProductFilter;
import ru.geekbrains.controller.repr.ProductRepr;
import ru.geekbrains.service.CategoryService;
import ru.geekbrains.service.ProductService;

import java.math.BigDecimal;


@Controller
@RequestMapping("products")
public class ProductController {

    private final ProductService productService;

    private final CategoryService categoryService;

    @Autowired
    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String products(@RequestParam(name = "categoryId", defaultValue = "-1") Long categoryId,
                           @RequestParam(name = "priceFrom", required = false) BigDecimal priceFrom,
                           @RequestParam(name = "priceTo", required = false) BigDecimal priceTo,
                           @RequestParam(name = "currentPage", defaultValue = "0") Integer currentPage,
                           @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize,
                           Model model) {
        ProductFilter productFilter = new ProductFilter(categoryId, priceFrom, priceTo,
                currentPage, pageSize);

        model.addAttribute("prodPage", productService.filterProducts(productFilter));
        model.addAttribute("filter", productFilter);
        model.addAttribute("categories", categoryService.findAllWithoutProducts());

        return "products";
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String createProductFrom(@RequestParam("categoryId") Long categoryId, Model model) {
        model.addAttribute("product", productService.getEmptyProductReprWithCategory(categoryId));
        return "product";
    }

    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String editProduct(@RequestParam("id") Long id, Model model) {
        model.addAttribute("product", productService.getProductReprById(id)
                .orElseThrow(ResourceNotFoundException::new));
        return "product";
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String createProduct(@ModelAttribute("product") ProductRepr productRepr) {
        productService.save(productRepr);
        return "redirect:/categories/edit?id=" + productRepr.getCategoryId();
    }
}