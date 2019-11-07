package ru.geekbrains.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.persistence.CustomerRepository;
import ru.geekbrains.persistence.ProductRepository;
import ru.geekbrains.persistence.entity.Customer;
import ru.geekbrains.persistence.entity.Product;

import java.util.List;

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
}
