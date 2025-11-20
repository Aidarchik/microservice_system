package web.spring_boot_mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import web.spring_boot_mvc.model.User;
import web.spring_boot_mvc.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ----- READ ALL -----
    @GetMapping("/")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.findAll());
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
        return "users/create"; // users/create.html
    }

    // ----- CREATE: SUBMIT -----
    @PostMapping
    public String create(@ModelAttribute("user") User user) {
        userService.create(user);
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
        userService.update(user);
        return "redirect:/users/";
    }

    // ----- DELETE -----
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/users/";
    }

}
