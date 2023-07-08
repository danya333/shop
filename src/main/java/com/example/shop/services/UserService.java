package com.example.shop.services;

import com.example.shop.Repositories.UserRepository;
import com.example.shop.models.Role;
import com.example.shop.models.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class UserService {

    public final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Получить текущего пользователя
    public User getCurrentUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        return userRepository.findByLogin(authentication.getName()).orElse(null);
    }

    // Создать пользователя
    public void createUser(String userName, String userSurname, String login, String password){
        User user = new User();
        user.setName(userName);
        user.setSurname(userSurname);
        user.setLogin(login);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Role.USER);
        user.setRegistrationDate(LocalDate.now());
        userRepository.save(user);
    }

}
