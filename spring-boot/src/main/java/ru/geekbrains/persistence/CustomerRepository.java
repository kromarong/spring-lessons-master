package ru.geekbrains.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.geekbrains.controller.repr.CustomerRepr;
import ru.geekbrains.persistence.entity.Customer;

import java.util.List;


public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("select new ru.geekbrains.controller.repr.CustomerRepr(c.id, c.name) " +
            "from Customer c " +
            "where c.id = :id")
    CustomerRepr getCustomerReprById(@Param("id") Long id);

    @Query("select new ru.geekbrains.controller.repr.CustomerRepr(c.id, c.name) " +
            "from Customer c ")
    List<CustomerRepr> getAllCustomerRepr();
}
