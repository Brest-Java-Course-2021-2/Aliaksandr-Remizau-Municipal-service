package com.epam.brest.web_app;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ClientController {
    /**
     * Goto clients list page.
     *
     * @return view name
     */
    @GetMapping(value = "/clients")
    public final String clients(Model model) {
        return "clients";
    }

    /**
     * Goto edit client page.
     *
     * @return view name
     */
    @GetMapping(value = "/client/{id}")
    public final String gotoEditClientPage(@PathVariable Integer id, Model model) {
        return "client";
    }

    /**
     * Goto new client page.
     *
     * @return view name
     */
    @GetMapping(value = "/client/add")
    public final String gotoAddClientPage(Model model) {
        return "client";
    }
}

