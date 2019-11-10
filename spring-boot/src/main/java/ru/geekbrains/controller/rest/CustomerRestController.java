package ru.geekbrains.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.controller.repr.CustomerRepr;
import ru.geekbrains.controller.repr.ProductRepr;
import ru.geekbrains.persistence.entity.Customer;
import ru.geekbrains.persistence.entity.Product;
import ru.geekbrains.service.CustomerService;

import java.util.List;


@RequestMapping("/api/customer")
@RestController
public class CustomerRestController {
    private CustomerService customerService;

    @Autowired
    public CustomerRestController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(value = "/all")
    public List<CustomerRepr> getAllCustomers() {
        return customerService.getAllCustomersReprWithProducts();
    }

    @GetMapping(value = "/{id}/id")
    public CustomerRepr getCustomer (@PathVariable("id") Long id) {
        return customerService.findCustomerReprById(id);
    }

    @GetMapping(value = "/{id}/products")
    public List<ProductRepr> getCustomersProducts(@PathVariable("id") Long id){
        return customerService.getCustomerProductsRepr(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateCategory(@RequestBody Customer customer) {
        customerService.save(customer);
    }

    @DeleteMapping(value = "/{id}/id")
    public void deleteCategory(@PathVariable("id") Long id) {
        customerService.delete(id);
    }
}
