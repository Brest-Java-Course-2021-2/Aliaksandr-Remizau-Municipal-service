package com.epam.brest.model;

import com.epam.brest.model.type.LevelOfDifficulty;
import com.epam.brest.model.type.RepairType;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;


import java.time.LocalDate;
import java.util.Objects;
/**
 * POJO Repair for model.
 */

public class Repair {

    /**
     * Integer Repair ID.
     */
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Integer repairId;

    /**
     *  Enum Type of Repair
     */
    @Schema(name = "RepairType", description = "type of repair")
    private RepairType repairType;

    /**
     *  String Address of Repair
     */
    @Schema(name = "Address", description = "address of repair")
    private String address;

    /**
     *  Enum LevelOfDifficulty of Repair
     */
    @Schema(name = "LevelOfDifficulty", description = "difficulty of repair")
    private LevelOfDifficulty difficultyLevel;

    /**
     *  preference Date of the repair.
     */
    @Schema(name = "PreferenceDate", description = "preference date of repair")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate preferenceDate;

    /**
     * ID of client ordering repair.
     */
    @Schema(name = "client ID", description = "ID of the client who ordered the repair")
    private Integer clientId;

    /**
     * Constructor without arguments.
     */
    public Repair(){
    }

    /**
     * Constructor with parameters.
     * @param repairType - type of repair.
     * @param address - address of repair.
     * @param difficultyLevel - level of difficulty repair.
     * @param preferenceDate - preference Date of the repair.
     * @param clientId - ID of client ordering repair.
     */
    public Repair(RepairType repairType, String address, LevelOfDifficulty difficultyLevel, LocalDate preferenceDate, Integer clientId) {
        this.repairType = repairType;
        this.address = address;
        this.difficultyLevel = difficultyLevel;
        this.preferenceDate = preferenceDate;
        this.clientId = clientId;
    }

    /**
     * Returns <code>Integer</code> representation of this clientId.
     *
     * @return repairId Repair Id.
     */
    public Integer getRepairId() {
        return repairId;
    }

    /**
     * Set the repair's identifier.
     *
     * @param repairId Client Id.
     */
    public void setRepairId(Integer repairId) {
        this.repairId = repairId;
    }

    /**
     * Getter for RepairType.
     * @return repairType.
     */
    public RepairType getRepairType() {
        return repairType;
    }

    /**
     * Getter for Address.
     * @return address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Set the repair's address.
     *
     * @param address .
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Set the RepairType.
     *
     * @param repairType .
     */
    public void setRepairType(RepairType repairType) {
        this.repairType = repairType;
    }

    /**
     * Getter for LevelOfDifficulty.
     * @return difficultyLevel.
     */
    public LevelOfDifficulty getDifficultyLevel() {
        return difficultyLevel;
    }

    /**
     * Set the setDifficultyLevel.
     *
     * @param difficultyLevel .
     */
    public void setDifficultyLevel(LevelOfDifficulty difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    /**
     * Getter for LocalDate.
     * @return preferenceDate.
     */
    public LocalDate getPreferenceDate() {
        return preferenceDate;
    }

    /**
     * Set the  LocalDate.
     *
     * @param preferenceDate .
     */
    public void setPreferenceDate(LocalDate preferenceDate) {
        this.preferenceDate = preferenceDate;
    }

    /**
     * Getter for Client ID.
     * @return clientId.
     */
    public Integer getClientId() {
        return clientId;
    }

    /**
     * Set the Client ID.
     *
     * @param clientId .
     */
    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    @Override
    public String toString() {
        return "Repair{" +
                "repairId=" + repairId +
                ", repairType=" + repairType +
                ", address='" + address + '\'' +
                ", difficultyLevel=" + difficultyLevel +
                ", preferenceDate=" + preferenceDate +
                ", clientId=" + clientId +
                '}';
    }
}
