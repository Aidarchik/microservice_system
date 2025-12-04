package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itmentor.spring.boot_security.demo.entity.Role;
import ru.itmentor.spring.boot_security.demo.entity.User;
import ru.itmentor.spring.boot_security.demo.service.RoleService;
import ru.itmentor.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    // ----- READ ALL -----
    @GetMapping("/")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("role", roleService.findAllRoles());
        return "users/list"; // users/list.html
    }

    // ----- READ ONE -----
    @GetMapping("/{id}")
    public String getUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.read(id).get());
        model.addAttribute("editUrl", "/admin/" + id + "/edit");
        model.addAttribute("showLinkBack", true);
        return "users/details"; // users/details.html
    }

    // ----- CREATE: FORM -----
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.findAllRoles());
        model.addAttribute("role", new Role());
        return "users/create"; // users/create.html
    }

    // ----- CREATE: SUBMIT -----
    @PostMapping
    public String create(@ModelAttribute("user") User user, @RequestParam("nameRoles") String[] role) {
        user.setRoles(roleService.findRoleByName(role));
        userService.create(user);
        return "redirect:/admin/";
    }

    @PostMapping("/role")
    public String createRole(@ModelAttribute("role") Role role) {
        roleService.createRole(role);
        return "redirect:/admin/";
    }

    // ----- UPDATE: FORM -----
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.read(id).get());
        model.addAttribute("roles", roleService.findAllRoles());
        model.addAttribute("actionUrl", "/admin/" + id);
        model.addAttribute("backUrl", "/admin/" + id);
        return "users/edit"; // users/edit.html
    }

    // ----- UPDATE: SUBMIT -----
    @PostMapping("/{id}")
    public String update(@PathVariable("id") Long id, @ModelAttribute("user") User user,
            @RequestParam("nameRoles") String[] role) {
        user.setRoles(roleService.findRoleByName(role));
        user.setId(id);
        userService.update(id, user);
        return "redirect:/admin/";
    }

    // ----- DELETE -----
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin/";
    }

    @PostMapping("/{id}/deleterole")
    public String deleteRole(@PathVariable("id") Long id) {
        roleService.deleteRole(id);
        return "redirect:/admin/";
    }

}
