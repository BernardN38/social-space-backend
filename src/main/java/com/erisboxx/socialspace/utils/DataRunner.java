package com.erisboxx.socialspace.utils;


import com.erisboxx.socialspace.entity.Role;
import com.erisboxx.socialspace.entity.User;
import com.erisboxx.socialspace.exception.ResourceNotFoundException;
import com.erisboxx.socialspace.repository.RoleRepository;
import com.erisboxx.socialspace.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

@Component
public class DataRunner implements CommandLineRunner {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        Role role = new Role("ROLE_USER");
        Role role2 = new Role("ROLE_ADMIN");
        roleRepository.save(role);
        roleRepository.save(role2);

        Role roles = roleRepository.findByName("ROLE_ADMIN").get();

        User user = new User("bernard","bernard","bernard@gmail.com", passwordEncoder.encode("password"));
        user.setRoles(Collections.singleton(roles));
        user = userRepository.save(user);


        User userToFind = null;

        for (int i =2; i< 10000; i++){
            System.out.println(i);
            User newUser = new User(String.format("user %d",i),String.format("user %d",i),String.format("user %d",i), "password");
            newUser.setRoles(Collections.singleton(roles));
            newUser = userRepository.save(newUser);
            user.getFriends().add(newUser);
            if (i == 999){
                userToFind = newUser;
            }
        }
        userRepository.save(user);
        System.out.println(user.getFriends());


    }
}
