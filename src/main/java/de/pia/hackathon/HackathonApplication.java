package de.pia.hackathon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@Controller
public class HackathonApplication {

    public static void main(String[] args) {
        SpringApplication.run(HackathonApplication.class, args);
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/intro")
    public String intro() {
        return "intro";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/search")
    public String search() {
        return "search";
    }

    @GetMapping("/account")
    public String account() {
        return "account";
    }

    @GetMapping("/sellProduct")
    public String sellProduct() {
        return "sellProduct";
    }

    @GetMapping("/detail")
    public String detail() {
        return "productDetail";
    }

}




