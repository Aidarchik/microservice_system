package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ru.itmentor.spring.boot_security.demo.entity.User;
import ru.itmentor.spring.boot_security.demo.service.RoleService;
import ru.itmentor.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    // ----- READ ONE -----
    @GetMapping
    public String getUser(Authentication auth, Model model) {
        User user = userService.readByUsername(auth.getName());
        model.addAttribute("user", user);
        model.addAttribute("editUrl", "/user/edit");
        model.addAttribute("showLinkBack", false);
        return "users/details"; // users/details.html
    }

    // ----- UPDATE: FORM -----
    @GetMapping("/edit")
    public String editForm(Authentication auth, Model model) {
        User user = userService.readByUsername(auth.getName());
        model.addAttribute("user", user);
        model.addAttribute("roles", user.getRoles());
        model.addAttribute("actionUrl", "/user/edit");
        model.addAttribute("backUrl", "/user");
        return "users/edit"; // users/edit.html
    }

    // ----- UPDATE: SUBMIT -----
    @PostMapping("/edit")
    public String update(Authentication auth, @ModelAttribute("user") User user,
            @RequestParam("nameRoles") String[] role) {
        Long userId = userService.readByUsername(auth.getName()).getId();
        user.setRoles(roleService.findRoleByName(role));
        user.setId(userId);
        user.setEnabled(true);
        userService.update(userId, user);
        return "redirect:/user";
    }

}
