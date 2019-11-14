package ru.geekbrains.persistence;

import org.springframework.data.repository.CrudRepository;
import ru.geekbrains.persistence.entity.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByName(String name);
}
