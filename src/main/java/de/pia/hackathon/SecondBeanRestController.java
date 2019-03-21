package de.pia.hackathon;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SecondBeanRestController {

    @GetMapping("/profile")
    @ResponseBody
    public TchiboProfile profile(@RequestParam String profileId) {

        // ask solar

        return new TchiboProfile();
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
