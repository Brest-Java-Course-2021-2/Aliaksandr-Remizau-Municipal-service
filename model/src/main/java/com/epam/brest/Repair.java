package com.epam.brest;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Repair {
    private Integer repairId;
    private RepairType repairType;
    private String address;
    private LevelOfDifficulty difficultyLevel;
    private LocalDate preferenceDate;
    private Integer amountOfCrew;
    private BigDecimal repairPrice;
    private Integer clientId;


    public Integer getRepairId() {
        return repairId;
    }

    public void setRepairId(Integer repairId) {
        this.repairId = repairId;
    }

    public RepairType getRepairType() {
        return repairType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setRepairType(RepairType repairType) {
        this.repairType = repairType;
    }

    public LevelOfDifficulty getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(LevelOfDifficulty difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public LocalDate getPreferenceDate() {
        return preferenceDate;
    }

    public void setPreferenceDate(LocalDate preferenceDate) {
        this.preferenceDate = preferenceDate;
    }

    public Integer getAmountOfCrew() {
        return amountOfCrew;
    }

    public void setAmountOfCrew(Integer amountOfCrew) {
        this.amountOfCrew = amountOfCrew;
    }

    public BigDecimal getRepairPrice() {
        return repairPrice;
    }

    public void setRepairPrice(BigDecimal repairPrice) {
        this.repairPrice = repairPrice;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

}
