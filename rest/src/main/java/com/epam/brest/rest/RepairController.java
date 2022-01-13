package com.epam.brest.rest;


import com.epam.brest.model.Repair;
import com.epam.brest.service.RepairService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
/**
 * Repair rest Controller.
 */

@RestController
public class RepairController {

    private static final Logger log = LogManager.getLogger(ClientController.class);

    private final RepairService repairService;

    public RepairController(RepairService repairService) {
        this.repairService = repairService;
    }

    @GetMapping(value = "/repairs")
    public final List<Repair> findAll(){

        log.debug("findAll()");
        return repairService.findAll();
    }

    @GetMapping(value = "/repairs/{id}")
    public final Repair getRepairById(@PathVariable Integer id) {

        log.debug("getRepairById({})",id);
        return repairService.getRepairById(id);
    }

    @PostMapping(path = "/repairs", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Integer> createRepair(@RequestBody Repair repair) {

        log.debug("createRepair({})", repair);
        Integer id = repairService.create(repair);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @PutMapping(value = "/repairs", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<Integer> updateRepair(@RequestBody Repair repair) {

        log.debug("updateRepair({})", repair);
        int result = repairService.update(repair);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @DeleteMapping(value = "/repairs/{id}", produces = {"application/json"})
    public ResponseEntity<Integer> deleterRepair(@PathVariable Integer id) {

        int result = repairService.delete(id);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping(value = "/repairs/filter")
    public final Collection<Repair> filterRepairByPreferenceDate(@RequestParam("startLimitDate")
                                                     @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startLimitDate,
                                                                 @RequestParam("endLimitDate")
                                                     @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endLimitDate,
                                                                 Model model) {
        log.debug("filterRepairByPreferenceDate({},{})", startLimitDate, endLimitDate);
        List<Repair> repairs = repairService.filterRepairByPreferenceDate(startLimitDate, endLimitDate);
        return repairs;
    }

}
