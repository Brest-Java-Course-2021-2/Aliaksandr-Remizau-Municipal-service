package com.epam.brest.web_app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
/**
 * Root controller.
 */
@Controller
public class HomeController {
    /**
     * Redirect to default page clients.
     *
     * @return redirect path
     */
    @GetMapping(value = "/")
    public String defaultPageRedirect() {
        return "redirect:clients";
    }
}
