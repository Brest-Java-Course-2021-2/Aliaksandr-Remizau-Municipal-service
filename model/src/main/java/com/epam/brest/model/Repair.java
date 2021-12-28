package com.epam.brest.model;

import com.epam.brest.model.type.LevelOfDifficulty;
import com.epam.brest.model.type.RepairType;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Objects;
/**
 * POJO Repair for model.
 */
public class Repair {

    /**
     * Integer Repair ID.
     */
    private Integer repairId;

    /**
     *  Enum Type of Repair
     */
    private RepairType repairType;

    /**
     *  String Address of Repair
     */
    private String address;

    /**
     *  Enum LevelOfDifficulty of Repair
     */
    private LevelOfDifficulty difficultyLevel;

    /**
     *  preference Date of the repair.
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate preferenceDate;

    /**
     * ID of client ordering repair.
     */
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Repair)) return false;
        Repair repair = (Repair) o;
        return Objects.equals(getRepairId(), repair.getRepairId()) &&
                getRepairType() == repair.getRepairType() && Objects.equals(getAddress(), repair.getAddress()) &&
                getDifficultyLevel() == repair.getDifficultyLevel() &&
                Objects.equals(getPreferenceDate(), repair.getPreferenceDate()) &&
                Objects.equals(getClientId(), repair.getClientId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRepairId(), getRepairType(), getAddress(), getDifficultyLevel(), getPreferenceDate(), getClientId());
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
