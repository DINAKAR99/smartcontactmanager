package cgg.smartcontactmanager.smartcontactmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cgg.smartcontactmanager.smartcontactmanager.dao.UserRepository;
import cgg.smartcontactmanager.smartcontactmanager.entities.Message;
// import cgg.smartcontactmanager.smartcontactmanager.dao.UserRepository;
import cgg.smartcontactmanager.smartcontactmanager.entities.User;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class HomeController {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserRepository userRepository;

    @GetMapping("/test")
    @ResponseBody
    public String test() {
        User user = new User();
        user.setName("Hussain");
        user.setEmail("hussainman@gmail.com");
        userRepository.save(user);
        return "Working...";
    }

    @GetMapping("/login")
    public String customLogin(Model model) {
        model.addAttribute("title", "Login Page");
        return "login";
    }

    @GetMapping("/logout")
    public String customlogout(Model model) {

        return "logout";
    }

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("title", "Home-Smart Contact Manager");
        return "home";
    }

    @GetMapping("/about")
    public String about(Model model, HttpSession hs) {
        model.addAttribute("title", "About-Smart Contact Manager");
        hs.setAttribute("message1", "this is session message");
        return "about";
    }

    @GetMapping("/services")
    public String serv(HttpSession hs) {

        // if (hs.getAttribute("message1") == null) {
        // hs.setAttribute("message1", "default message ratatata ");

        // }
        return "services";
    }

    @GetMapping("/loginfail")
    public String loginfail() {

        return "loginfail";
    }

    @RequestMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("title", "Register-Smart Contact Manager");

        model.addAttribute("user", new User());
        return "signup";
    }

    // handler for registering user
    @PostMapping("/do_register")
    public String registerUSer(@Valid @ModelAttribute("user") User user, BindingResult br,
            @RequestParam(value = "agreement", defaultValue = "false") boolean agreement, Model model,
            HttpSession session) {
        try {
            // encoding passowrd
            String encode = passwordEncoder.encode(user.getPassword());
            user.setPassword(encode);
            // encoding passowrd

            if (br.hasErrors()) {

                model.addAttribute("user", user);

                return "signup";

            }

            if (!agreement) {
                System.out.println("You have not agreed the terms and conditions");
                throw new Exception("You have not agreed the terms and conditions");
            }
            user.setRole("ROLE_USER");
            user.setEnabled(true);
            user.setImageUrl("default.png");
            System.out.println("Agreement - " + agreement);
            System.out.println("USER" + user);
            User result = this.userRepository.save(user);

            model.addAttribute("user", result);

            session.setAttribute("message", new Message("Successfully Registetred !!", "alert-success"));
            return "signup";
        } catch (Exception e) {
            e.printStackTrace();

            session.setAttribute("message", new Message("Something went wrong!!" + e.getMessage(), "alert-error"));
            return "signup";
        }
    }
}