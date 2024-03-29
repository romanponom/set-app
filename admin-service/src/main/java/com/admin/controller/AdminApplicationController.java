package com.admin.controller;

import com.admin.client.DBClient;
import com.admin.client.ValidatorClient;
import com.admin.entity.User;
import com.admin.entity.ValidatedUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminApplicationController {

    private final DBClient dbClient;
    private final ValidatorClient validatorClient;

    @GetMapping("/")
    public String homePage(Model model) {
        List<User> users = dbClient.getUsers();
        model.addAttribute("users", users.isEmpty() ? null : users);
        return "index";
    }

    @GetMapping("/signup")
    public String showSignUpForm(Model model) {
        model.addAttribute("user", new User());
        return "add-user";
    }

    @PostMapping("/adduser")
    public String addUser(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-user";
        }

        dbClient.addUser(user);
        model.addAttribute("users", dbClient.getUsers());
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        User user = dbClient.getUserById(id);
        model.addAttribute("user", user);
        return "update-user";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid User user,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            user.setId(id);
            return "update-user";
        }

        dbClient.updateUser(user, id);
        model.addAttribute("users", dbClient.getUsers());
        return "redirect:/";
    }
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        dbClient.deleteUser(id);
        model.addAttribute("users", dbClient.getUsers());
        return "redirect:/";
    }

    @GetMapping("/validate/{id}")
    public String showValidateForm(@PathVariable("id") long id, Model model) {
        User user = dbClient.getUserById(id);
        model.addAttribute("user", user);
        return "validate-user";
    }

    @PutMapping("/validate-user/{id}")
    public String validateUser(@PathVariable Long id, Model model) {
        dbClient.getUserById(id);
        validatorClient.validateUserById(id, new ValidatedUser(true));
        model.addAttribute("users", dbClient.getUsers());
        return "redirect:/";
    }
}
