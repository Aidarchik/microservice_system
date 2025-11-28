package ru.itmentor.spring.boot_security.demo.controller;

import java.util.Set;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.repository.RoleRepository;
import ru.itmentor.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final RoleRepository roleRepository;

    public UserController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    // ----- READ ALL -----
    @GetMapping("/")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("role", userService.findAllRoles());
        return "users/list"; // users/list.html
    }

    // ----- READ ONE -----
    @GetMapping("/{id}")
    public String getUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.read(id));
        return "users/details"; // users/details.html
    }

    // ----- CREATE: FORM -----
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("role", new Role());
        return "users/create"; // users/create.html
    }

    // ----- CREATE: SUBMIT -----
    @PostMapping
    public String create(@ModelAttribute("user") User user) throws UsernameNotFoundException {
        Role role = roleRepository.findByRoleName("ROLE_ADMIN")
                .orElseThrow(() -> new UsernameNotFoundException("Role not found:"));
        user.setRoles(Set.of(role));
        user.setEnabled(true);
        userService.create(user);
        return "redirect:/users/";
    }

    // ----- CREATE: SUBMIT -----
    @PostMapping("/role")
    public String createRole(@ModelAttribute("role") Role role) {
        userService.createRole(role);
        return "redirect:/users/";
    }

    // ----- UPDATE: FORM -----
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.read(id));
        return "users/edit"; // users/edit.html
    }

    // ----- UPDATE: SUBMIT -----
    @PostMapping("/{id}")
    public String update(@PathVariable("id") Long id, @ModelAttribute("user") User user) {
        user.setId(id);
        user.setEnabled(true);
        userService.update(user);
        return "redirect:/users/";
    }

    // ----- DELETE -----
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/users/";
    }

    @PostMapping("/{id}/deleterole")
    public String deleteRole(@PathVariable("id") Long id) {
        userService.deleteRole(id);
        return "redirect:/users/";
    }

}
