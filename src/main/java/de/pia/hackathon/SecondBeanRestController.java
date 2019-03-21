package de.pia.hackathon;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

@RestController
@RequestMapping("/api")
public class SecondBeanRestController {

    @GetMapping("/profile")
    @ResponseBody
    public TchiboProfile profile(@RequestParam String profileId) {

        // ask solar

        return new TchiboProfile();
    }

    @GetMapping("/product")
    public TchiboProduct getProduct(@RequestParam byte[] image) {

        return new TchiboProduct();
    }

    @GetMapping("/image")
    public byte[] getImage(@RequestParam String imageId) {

        return new byte[0];
    }

    @GetMapping("/toplist")
    public List<TchiboProduct> topList() {
        TchiboProduct product1 = new TchiboProduct();
        product1.name = "p1";
        product1.price = 9.90;
        TchiboProduct product2 = new TchiboProduct();
        product2.name = "p2";
        product2.price = 4.99;
        return asList(product1, product2);
    }

    @GetMapping("/communitylist")
    public List<TchiboProduct> communitylist() {

        return new ArrayList<>();
    }

    @GetMapping("/topRated")
    public List<TchiboProduct> topRated() {

        return new ArrayList<>();
    }

}
class TchiboProduct {
    String name;
    Double price;

    public Double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }
}

class TchiboProfile {

    private String name;
    private List<String> boughtProducts;

    public String getName() {
        return name;
    }

    public List<String> getBoughtProducts() {
        return boughtProducts;
    }
}
