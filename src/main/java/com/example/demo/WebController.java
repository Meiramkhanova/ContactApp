 package com.example.demo;

import com.example.demo.repository.UserRepository;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class WebController {

    @Autowired
    UserRepository repository;

    @GetMapping("/")
    public String showUserList(@RequestParam(name = "task1", required = false, defaultValue = "") String task1,
                               @RequestParam(name = "task2", required = false, defaultValue = "") String task2,
                               @RequestParam(name = "task3", required = false, defaultValue = "") String task3,
                               @RequestParam(name = "task4", required = false, defaultValue = "") Long task4,
                               @RequestParam(name = "task5", required = false, defaultValue = "") Long task5,
                               @RequestParam(name = "task6", required = false, defaultValue = "") String task6,
                               @RequestParam(name = "task7", required = false, defaultValue = "") String task7,
                               @RequestParam(name = "task8", required = false, defaultValue = "") String task8,
                               @RequestParam(name = "task9", required = false, defaultValue = "") String task9,
                               @RequestParam(name = "task10", required = false, defaultValue = "") String task10,
                               Model model) {

        List<User> users = repository.findAll();

        if (!task1.isEmpty()) {
            users =repository.findByEmailEndsWith(task1);
        }


        if (!task2.isEmpty()) {
            users = repository.findTop2ByNameStartsWith(task2);
        }

        if (!task3.isEmpty()) {
            users = repository.findBySurnameContaining(task3);
        }

        if (task4!= null) {
            users = repository.findByOrderById();
        }
        if (task5!= null) {
            users = repository.findTop2ByOrderByIdDesc();
        }

        if (!task6.isEmpty()){
            users = repository.findByOrderByNameDesc();
        }

        if (!task7.isEmpty()) {
            users = repository.findByEmailNotContaining(task7);
        }
        if(!task8.isEmpty()){
            users = repository.findAllW();
        }

        if(!task9.isEmpty()){
            users = repository.findAllQ();
        }

        if (!task10.isEmpty()) {
            users = repository.findAllE();
        }

        model.addAttribute("users", users);
        return "index";
    }

    @PostMapping("/adduser")
    public String createUser(@ModelAttribute User user){
        addUser(user);
        return "redirect:/";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") Long id, User user) {
        updateUser(user);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/signup")
    public String showSignUpForm(User user) {
        return "add-user";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        User user = repository.getById(id);
        model.addAttribute("user", user);
        return "update-user";
    }

    private void deleteById(Long id) {
        repository.deleteById(id);
    }

    private void addUser(User newUser) {
        repository.save(newUser);
    }

    private void updateUser(User updateUser) {

        User user = repository.getById(updateUser.getId());
        user.setName(updateUser.getName());
        user.setSurname(updateUser.getSurname());
        user.setSurname(updateUser.getEmail());
        repository.save(user);
    }
}