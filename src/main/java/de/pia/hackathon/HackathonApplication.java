package de.pia.hackathon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
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

    @GetMapping("/product")
    public TschiboProduct getProduct(@RequestParam byte[] image) {

        return new TschiboProduct();
    }

    @GetMapping("/image")
    public byte[] getImage(@RequestParam String imageId) {

        return new byte[0];
    }

    @GetMapping("/toplist")
    public List<TschiboProduct> topList() {

        return new ArrayList<>();
    }

    @GetMapping("/communitylist")
    public List<TschiboProduct> communitylist() {

        return new ArrayList<>();
    }

    @GetMapping("/topRated")
    public List<TschiboProduct> topRated() {

        return new ArrayList<>();
    }
}




class TschiboProduct {
    String name;
    Double price;
}