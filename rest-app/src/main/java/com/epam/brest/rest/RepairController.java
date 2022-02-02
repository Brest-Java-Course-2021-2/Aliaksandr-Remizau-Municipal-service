package com.epam.brest.rest;


import com.epam.brest.model.Repair;
import com.epam.brest.service.RepairService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Repair rest Controller.
 */

@RestController
@Tag(name = "RepairController")
public class RepairController {
    /**
     * Logger.
     */
    private static final Logger log = LogManager.getLogger(ClientController.class);

    /**
     * Field repairService.
     */

    private final RepairService repairService;

    /**
     * Constructor RepairController.
     *
     * @param repairService .
     */

    public RepairController(RepairService repairService) {
        this.repairService = repairService;
    }
    /**
     * Find All repairs.
     *
     * @return repair's list in json.
     */
    @Operation(summary = "Get all repairs")
    @GetMapping(value = "/repairs")
    public ResponseEntity<List<Repair>> findAll(){

        log.debug("findAll()");
        final List<Repair> repairs =repairService.findAll();

        return new ResponseEntity<>(repairs,HttpStatus.OK);
    }
    /**
     * Get repair by id.
     *
     * @param id repair.
     * @return repair in json.
     */
    @Operation(summary = "Get repairs depend on its ID")
    @GetMapping(value = "/repairs/{id}", produces = {"application/json"})
    public ResponseEntity< Repair> getRepairById(@PathVariable Integer id) {

        log.debug("getRepairById({})",id);
        final Repair repair = repairService.getRepairById(id);

        return new ResponseEntity<>(repair,HttpStatus.OK);
    }

    /**
     * Create repair.
     *
     * @param repair .
     * @return id created repair,HttpStatus.OK.
     */
    @Operation(summary = "Create new repair")
    @PostMapping(path = "/repairs", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Integer> createRepair(@RequestBody Repair repair) {

        log.debug("createRepair({})", repair);
        Integer id = repairService.create(repair);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    /**
     * Update repair.
     *
     * @param  repair .
     * @return int result amount of updated repair,HttpStatus.OK.
     */
    @Operation(summary = "Update repair")
    @PutMapping(value = "/repairs", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<Integer> updateRepair(@RequestBody Repair repair) {

        log.debug("updateRepair({})", repair);
        int result = repairService.update(repair);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    /**
     * Delete repair.
     *
     * @param  id .
     * @return int result amount of deleted repair,HttpStatus.OK.
     */
    @Operation(summary = "Delete repair")
    @DeleteMapping(value = "/repairs/{id}", produces = {"application/json"})
    public ResponseEntity<Integer> deleterRepair(@PathVariable Integer id) {

        int result = repairService.delete(id);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    /**
     * Filter repairs by Preference Date.
     * @param startLimitDate - start date.
     * @param endLimitDate - end date.
     * @return - list of repairs .
     */
    @Operation(summary = "Filter repairs depend on preference date ")
    @GetMapping(value = "/repairs/filter" ,produces = {"application/json"})
    public ResponseEntity<List<Repair>> filterRepairByPreferenceDate(
            @RequestParam("startLimitDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startLimitDate,
            @RequestParam("endLimitDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endLimitDate) {

        log.debug("filterRepairByPreferenceDate({},{})", startLimitDate, endLimitDate);

        List<Repair> repairs = repairService.filterRepairByPreferenceDate(startLimitDate, endLimitDate);
        return new ResponseEntity<>(repairs,HttpStatus.OK);
    }
}
