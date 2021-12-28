package com.epam.brest.web_app;

import com.epam.brest.model.Repair;
import com.epam.brest.service.ClientService;
import com.epam.brest.service.RepairService;
import com.epam.brest.web_app.validators.RepairValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

/**
 * Repair controller.
 */
@Controller

public class RepairController {
    /**
     * Logger for Repair controller.
     */
    private static final Logger log = LogManager.getLogger(RepairController.class);


    private final RepairService repairService;

    private final ClientService clientService;


    private final RepairValidator repairValidator;
    /**
     * Constructor with  clientId and client name.
     *
     * @param repairService
     * @param clientService
     * @param repairValidator
     */

    public RepairController(RepairService repairService, ClientService clientService, RepairValidator repairValidator) {
        this.repairService = repairService;
        this.clientService = clientService;
        this.repairValidator = repairValidator;
    }

    /**
     * Go to repairs list page.
     * @param model
     * @return view name
     */
    @GetMapping(value = "/repairs")
    public final String repairs(Model model) {
        log.debug("go to the repairs page");
        model.addAttribute("clients",clientService.findAll());
        model.addAttribute("repairs",repairService.findAll());
        return "repairs";
    }
    /**

     * Go to edit repair page.
     * @param id repair's ID
     * @param model model


     * @return view name
     */
    @GetMapping(value = "/repair/{id}")
    public final String gotoEditRepairPage(@PathVariable Integer id, Model model) {
        log.debug("go to edit repairs page");
        model.addAttribute("isNew",false);
        model.addAttribute("repair",repairService.getRepairById(id));
        model.addAttribute("clients",clientService.findAll());
        return "repair";
    }
    /**
     * Go to new repair page.
     *
     *@param model model
     * @return view name
     */
    @GetMapping(value = "/repair")
    public final String gotoAddRepairPage(Model model) {
        log.debug("gotoAddRepairPage({})",model);
        model.addAttribute("isNew",true);
        model.addAttribute("repair",new Repair());
        model.addAttribute("clients", clientService.findAll());
        return "repair";
    }
    /**
     * Go to add repair page.
     *
     * @param repair
     * @param result
     * @return redirect: repairs page
     */


    @PostMapping(value = "/repair")
    public String addRepair(Repair repair, BindingResult result) {
        log.debug("addTrack({})", repair);
        repairValidator.validate(repair, result);
        if (result.hasErrors()) {
            return "repair";
        }
        this.repairService.create(repair);
        return "redirect:/repairs";
    }

    @PostMapping(value = "/repair/{id}")
    public String updateRepair(Repair repair, BindingResult result) {
        log.debug("updateRepair({})", repair);
        repairValidator.validate(repair, result);
        if (result.hasErrors()) {
            return "repair";
        }
        this.repairService.update(repair);
        return "redirect:/repairs";
    }

    @GetMapping(value = "/repair/{id}/delete")
    public final String deleteRepairById(@PathVariable Integer id, Model model) {
        log.debug("delete({},{})", id, model);
        repairService.delete(id);
        return "redirect:/repairs";
    }

    @GetMapping(value = "/repairs/filter")
    public final String filterRepairByPreferenceDate(@RequestParam("startLimitDate")
                                                 @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startLimitDate,
                                                 @RequestParam("endLimitDate")
                                                 @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endLimitDate,
                                                 Model model) {
        log.debug("filterRepairByPreferenceDate({},{})", startLimitDate, endLimitDate);
        List<Repair> repairs = repairService.filterRepairByPreferenceDate(startLimitDate,endLimitDate);
        model.addAttribute("repairs",repairs);
        return "repairs";
    }

}
