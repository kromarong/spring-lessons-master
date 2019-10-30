package ru.geekbrains;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Arrays;
import java.util.Collections;

@Controller
public class MainController {

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String mainPage(Model model) {
        model.addAttribute("msgList", Arrays.asList(
                new Message("Hello"),
                new Message("World"),
                new Message("!!!")
        ));
        return "index";
    }
}
