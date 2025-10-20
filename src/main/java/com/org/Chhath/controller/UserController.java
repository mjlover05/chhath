package com.org.Chhath.controller;

import com.org.Chhath.model.User;
import com.org.Chhath.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String showLoginPage(Model model) {
        model.addAttribute("user", new User());
        return "index";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute User user,
                            RedirectAttributes redirectAttributes,
                            HttpSession session) {

        User existingUser = userService.validateUser(user.getEmail(), user.getPassword());

        if (existingUser != null) {
            session.setAttribute("loggedUser", existingUser);
            return "redirect:/dashboard";
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid email or password!");
            return "redirect:/"; // redirect to login page
        }
    }



    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user,
                               Model model,
                               RedirectAttributes redirectAttributes) {

        try {
            String generatedPassword = userService.saveUser(user);
            model.addAttribute("generatedPassword", generatedPassword);
            model.addAttribute("userName", user.getName());
            return "register-success";

        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/register";
        }
    }



}














//    @PostMapping("/register")
//    public String registerUser(@ModelAttribute User user, Model model) {
//        // Generate password and save user
//        userService.saveUser(user);
//
//        // Pass generated password to success page
//        model.addAttribute("generatedPassword", user.getPassword());
//        return "register-success";
//    }
//
//    @PostMapping("/register")
//    public String registerUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
//        return userService.saveUser(user, redirectAttributes);
//    }



//    @PostMapping("/submit")
//    public String loginUser(@ModelAttribute User user, Model model) {
//        System.out.println(user.getEmail());
//        User existingUser = userService.findByEmail(user.getEmail());
//        System.out.println(existingUser);
//        if (existingUser != null && existingUser.getPassword().equals(user.getPassword())) {
//            // Successful login
//            model.addAttribute("name", existingUser.getName());
//            return "redirect:dashboard"; // redirect to dashboard page
//        } else {
//            // Failed login
//            model.addAttribute("error", "Invalid email or password!");
//            return "index"; // return back to login page
//        }
//    }



// try {
//// Delegate logic to service
//String generatedPassword = userService.saveUser(user);
//
//// Pass data to success page
//            model.addAttribute("generatedPassword", generatedPassword);
//            model.addAttribute("userName", user.getName());
//        return "register-success";
//
//        } catch (IllegalArgumentException e) {
//        // Custom validation or duplicate errors thrown from service
//        redirectAttributes.addFlashAttribute("error", e.getMessage());
//        return "redirect:/register";
//        }


//    @PostMapping("/submit")
//    public String loginUser(@RequestParam String email,
//                            @RequestParam String password,
//                            HttpSession session,
//                            RedirectAttributes redirectAttributes) {
//
//        User user = userService.validateUser(email, password);
//
//        if (user != null) {
//            session.setAttribute("loggedUser", user);
//            return "redirect:/dashboard";
//        } else {
//            redirectAttributes.addFlashAttribute("error", "Invalid credentials!");
//            return "redirect:/"; // redirect to login page
//        }
//    }