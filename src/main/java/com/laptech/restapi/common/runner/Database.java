package com.laptech.restapi.common.runner;

import com.laptech.restapi.common.enums.Gender;
import com.laptech.restapi.model.User;
import com.laptech.restapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
        List<User> userList = new ArrayList<>();

        User user1 = new User(0L,
                "Võ Nhật Phi",
                Gender.MALE,
                LocalDate.parse("2001-04-17"),
                "+84947679570",
                null,
                "123456",
                true
        );
        userList.add(user1);

        User user2 = new User(0L,
                "Nguyễn Quang Sang",
                Gender.MALE,
                LocalDate.parse("2001-08-21"),
                "+84345290616",
                null,
                "123456",
                true
        );
        userList.add(user2);

        userList.forEach(user -> {
            User newUser = userService.insert(user);
            System.out.println(newUser.toString());
        });
    }
}
