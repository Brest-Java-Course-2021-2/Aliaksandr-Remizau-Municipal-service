package com.epam.brest.web_app;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public class RepairController {
    /**
     * Goto repairs list page.
     *
     * @return view name
     */
    @GetMapping(value = "/repairs")
    public final String repairs(Model model) {
        return "repairs";
    }
    /**
     * Goto edit employee page.
     *
     * @return view name
     */
    @GetMapping(value = "/repair/{id}")
    public final String gotoEditRepairPage(@PathVariable Integer id, Model model) {
        return "repair";
    }

    /**
     * Goto new repair page.
     *
     * @return view name
     */
    @GetMapping(value = "/repair/add")
    public final String gotoAddRepairPage(Model model) {
        return "repair";
    }
}
