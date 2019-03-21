package de.pia.hackathon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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

    @GetMapping("/profile")
    public TschiboProfile profile() {

        return new TschiboProfile();
    }

    @GetMapping("/image")
    public TschiboProduct getProduct(@RequestParam byte[] image) {

        return new TschiboProduct();
    }

}


class TschiboProfile {

    String name;
    List<String> buyedProducts;
}

class TschiboProduct {
    String name;
    Double price;
    byte[] image;

}