package ru.geekbrains.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.controller.repr.CustomerRepr;
import ru.geekbrains.controller.repr.ProductRepr;
import ru.geekbrains.persistence.CustomerRepository;
import ru.geekbrains.persistence.ProductRepository;
import ru.geekbrains.persistence.entity.Customer;
import ru.geekbrains.persistence.entity.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, ProductRepository productRepository) {
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }


    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer findById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Customer not found"));
    }

    public CustomerRepr findCustomerReprById(Long id){
        return customerRepository.getCustomerReprById(id);
    }

    public void save(Customer customer) {
        customerRepository.save(customer);
    }


    public Customer buyProduct(Long productId, Long customerId){
        Product product = productRepository.findById(productId).
                orElseThrow(() -> new IllegalStateException("Product not found"));
        Customer customer = customerRepository.findById(customerId).
                orElseThrow(() -> new IllegalStateException("Customer not found"));;
        customer.addProduct(product);
        customerRepository.save(customer);
        return customer;
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public void delete(Long id) {
        customerRepository.deleteById(id);
    }

    public List<CustomerRepr> getAllCustomersReprWithoutProducts() {
        return customerRepository.getAllCustomerRepr();
    }

    public List<CustomerRepr> getAllCustomersReprWithProducts() {
        List<CustomerRepr> customerReprs= customerRepository.getAllCustomerRepr();
        for(CustomerRepr c: customerReprs){
            c.setProducts(this.getCustomerProductsRepr(c.getId()));
        }
        return customerReprs;
    }

    public List<ProductRepr> getCustomerProductsRepr(Long customerId){
        Customer customer = this.findById(customerId);
        List<ProductRepr> productReprList = new ArrayList<>();
        for(Product p: customer.getProducts()){
            productReprList.add(productRepository.getProductReprById(p.getId()).
                    orElseThrow(() -> new IllegalStateException("Product not found")));
        }
        return productReprList;
    }
}
