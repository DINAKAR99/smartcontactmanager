package cgg.smartcontactmanager.smartcontactmanager.controller;

import java.net.http.HttpRequest;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cgg.smartcontactmanager.smartcontactmanager.dao.UserRepository;
import cgg.smartcontactmanager.smartcontactmanager.entities.Contact;
import cgg.smartcontactmanager.smartcontactmanager.entities.Message;
import cgg.smartcontactmanager.smartcontactmanager.entities.User;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserRepository u1;

    @RequestMapping("/index")
    public String dashboard(Model model, Principal principal, HttpSession hs) {

        {

            String userName = principal.getName();
            System.out.println("USERNAME" + userName);
            User user = u1.findByName(userName);
            System.out.println("USER" + user);
            model.addAttribute("title", "User Dashboard");

            hs.setAttribute("user", user);
            // model.addAttribute("user", user);
            return "normal/user_dashboard";
        }
    }

    // open add from handler
    @GetMapping("/add-contact")
    public String openAddContactForm(Model model, HttpSession hs, @ModelAttribute("check") String message) {

        model.addAttribute("title", "Add Contact");
        model.addAttribute("contact", new Contact());

        System.out.println("in add contct");

        System.out.println(message.isEmpty());
        if (message.isEmpty()) {

            hs.removeAttribute("message");
        }
        return "normal/add_contact_form";
    }

    // processing add contact form
    @PostMapping("/process-contact")
    public String processContact(@ModelAttribute Contact contact, Principal principal, RedirectAttributes ra,
            HttpSession hs) {
        {
            String name = principal.getName();
            User user = this.u1.findByName(name);
            contact.setUser(user);
            user.getContacts().add(contact);
            User save = this.u1.save(user);

            if (save != null) {
                hs.setAttribute("message", new Message("contact add success", "alert-success"));

                System.out.println("Added to data base");
                ra.addFlashAttribute("check", "yup");
                return "redirect:/users/add-contact";

            }

            hs.setAttribute("message", new Message("contact add failed", "alert-danger"));

            return "redirect:/users/add-contact";
        }
    }

}
