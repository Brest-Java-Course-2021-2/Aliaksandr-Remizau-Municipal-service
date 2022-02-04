package com.epam.brest.model;

import com.epam.brest.model.type.LevelOfDifficulty;
import com.epam.brest.model.type.RepairType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
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
    @NonNull
    @Schema(name = "RepairType", description = "type of repair")
    @NotEmpty(message = "Please provide type of repair!")
    private RepairType repairType;

    /**
     *  String Address of Repair
     */
    @NonNull
    @Schema(name = "Address", description = "address of repair")
    @Size(min = 1, max = 50, message = "Address  size have to be  less or equals  250 symbols!")
    private String address;

    /**
     *  Enum LevelOfDifficulty of Repair
     */
    @NonNull
    @Schema(name = "LevelOfDifficulty", description = "difficulty of repair")
    @NotEmpty(message = "Please provide level of difficulty!")
    private LevelOfDifficulty difficultyLevel;

    /**
     *  preference Date of the repair.
     */
    @NotNull(message = "Please provide your preference date of repair")
    @Schema(name = "PreferenceDate", description = "preference date of repair")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent(message = "Incorrect value. Date can not be future")
    private LocalDate preferenceDate;

    /**
     * ID of client ordering repair.
     */
    @Schema(name = "client ID", description = "ID of the client who ordered the repair")
    @NotEmpty(message = "Select repair owner")
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
