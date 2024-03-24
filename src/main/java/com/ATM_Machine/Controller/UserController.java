package com.ATM_Machine.Controller;

import com.ATM_Machine.Entity.User;
import com.ATM_Machine.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/nationalbank.com")
    public String show_home_page(){
        return "authentication/home_page.html";
    }

    @GetMapping("/signup")
    public String showSignupForm() {
        return "authentication/signup";
    }

    @PostMapping("/signup")
    public String signUp(@RequestParam String username, @RequestParam String email, @RequestParam String password, Model model) {
        try {
            User user = new User();

            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(email);
            userRepository.save(user);
            return "redirect:/login";
        }
        catch (Exception e){
            model.addAttribute("errorMessage","Signup failed, Username Already Exist");
            return "redirect:/signup";
        }
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "authentication/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) throws Exception {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            // Login successful
            model.addAttribute("message", "Login successful!"); // Set success message
            model.addAttribute("errorMessage", ""); // Clear any previous error message
            return "redirect:/dashboard";
        } else {
            return "redirect:/dashboard";
        }
    }

    @GetMapping("/dashboard")
    public String showDashboard() {
        return "authentication/Bank_Dashboard";
    }

    @GetMapping("/home")
    public String homepage(){
        return "authentication/landing_page.html";
    }
}
