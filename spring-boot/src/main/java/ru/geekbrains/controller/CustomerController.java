package ru.geekbrains.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.persistence.entity.Customer;
import ru.geekbrains.service.CustomerService;


@Controller
@RequestMapping("customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String allCustomers(Model model) {
        model.addAttribute("customers", customerService.findAll());
        return "customers";
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String createCustomerFrom(Model model) {
        model.addAttribute("customer", new Customer());
        model.addAttribute("action", "create");
        return "customer";
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String createCategory(@ModelAttribute("customer") Customer customer) {
        customerService.save(customer);
        return "redirect:/customers";
    }

    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String editForm(@RequestParam("id") Long id, Model model) {
        Customer customer = customerService.findById(id);
        model.addAttribute("customer", customer);
        model.addAttribute("allProducts", customerService.findAllProducts());
        model.addAttribute("action", "edit");
        System.out.println(customer.getProducts());
        return "customer";
    }

    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public String editForm(@ModelAttribute("customer") Customer customer) {
        customerService.save(customer);
        return "customer";
    }

    @RequestMapping(value = "buy/{productId}/{customerId}", method = RequestMethod.GET)
    public String buyProductFrom(@PathVariable("productId") Long productId,
                                 @PathVariable("customerId") Long customerId,
                                 Model model) {
        System.out.println("-----------------------------------------------------enter buing product block");
        Customer customer = customerService.buyProduct(productId, customerId);
        model.addAttribute("customer", customer);
        return "customer";
    }

    @RequestMapping(value = "buy", method = RequestMethod.POST)
    public String buyProductFrom(@ModelAttribute("customer") Customer customer) {
        System.out.println("------------------------------------------product succesfully added!");
        customerService.save(customer);
        return "customer";
    }
}
