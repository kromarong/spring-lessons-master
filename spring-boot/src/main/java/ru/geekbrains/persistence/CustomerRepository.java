package ru.geekbrains.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.persistence.entity.Customer;


public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
