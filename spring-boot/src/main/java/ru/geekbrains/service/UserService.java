package ru.geekbrains.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.geekbrains.controller.repr.UserRepr;
import ru.geekbrains.persistence.RoleRepository;
import ru.geekbrains.persistence.UserRepository;
import ru.geekbrains.persistence.entity.Role;
import ru.geekbrains.persistence.entity.User;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private static final String ROLE_ADMIN = "ADMIN";
    private static final String ROLE_USER = "USER";

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void create(UserRepr userRepr) {
        User user = new User();
        user.setUsername(userRepr.getUsername());
        user.setPassword(passwordEncoder.encode(userRepr.getPassword()));
        user.setRoles(userRepr.getRoles());
        userRepository.save(user);
    }

    public Role getAdminRole(){
        return roleRepository.findByName(ROLE_ADMIN);
    }

    public Role getUserRole(){
        return roleRepository.findByName(ROLE_USER);
    }

    @PostConstruct
    public void checkGeneralRolesExistence() {
        Role checkAdmin = roleRepository.findByName(ROLE_ADMIN);
        if (checkAdmin == null){
            Role admin = new Role(ROLE_ADMIN);
            roleRepository.save(admin);
        }
        Role checkUser = roleRepository.findByName(ROLE_USER);
        if (checkUser == null){
            Role user = new Role(ROLE_USER);
            roleRepository.save(user);
        }
    }

    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }
}
