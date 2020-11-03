package com.controller;

import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.entity.User;

import javax.validation.Valid;
import java.util.List;

@Controller
public class AppController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String homePage(Model model) {
        List<User> users = userService.findAll();
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

        userService.addOrUpdateUser(user);
        model.addAttribute("users", userService.findAll());
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        User user = userService.getUser(id);
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

        userService.addOrUpdateUser(user);
        model.addAttribute("users", userService.findAll());
        return "redirect:/";
    }
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        User user = userService.getUser(id);
        userService.deleteUser(user);
        model.addAttribute("users", userService.findAll());
        return "redirect:/";
    }
}
