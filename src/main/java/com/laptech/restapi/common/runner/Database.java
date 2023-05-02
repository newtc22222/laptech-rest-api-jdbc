package com.laptech.restapi.common.runner;

import com.laptech.restapi.common.enums.Gender;
import com.laptech.restapi.model.User;
import com.laptech.restapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import java.time.LocalDate;

/**
 * Create sample data for User table
 *
 * @since 2023-02-12
 */
//@Component
public class Database implements CommandLineRunner {
    @Autowired
    private UserService userService;


    @Override
    public void run(String... args) {
        User user = new User();

        user.setId(0L);
        user.setName("Võ Nhật Phi");
        user.setGender(Gender.MALE);
        user.setDateOfBirth(LocalDate.parse("2001-04-17"));
        user.setPhone("+84947679570");
        user.setPassword("123456");
        user.setActive(true);

        User newUser = userService.insert(user);
        System.out.println(newUser.toString());
    }
}
