package ru.geekbrains.controller.repr;

import java.util.List;

public class CustomerRepr {

    private Long id;

    private String name;

    private List<ProductRepr> products;

    public CustomerRepr() {
    }

    public CustomerRepr(Long id, String name, List<ProductRepr> products) {
        this.id = id;
        this.name = name;
        this.products = products;
    }

    public CustomerRepr(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProductRepr> getProducts() {
        return products;
    }

    public void setProducts(List<ProductRepr> products) {
        this.products = products;
    }
}
