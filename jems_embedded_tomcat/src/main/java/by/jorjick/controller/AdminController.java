package by.jorjick.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by gora on 1/10/17.
 */
@Controller
public class AdminController {

    @RequestMapping("/admin")
    public String admin() {
        return "admin";
    }

}
