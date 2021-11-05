package com.epam.brest;

import java.time.LocalDate;

public class Repair {
    private Integer repairId;
    private RepairType repairType;
    private LevelOfDifficulty difficultyLevel;
    private int amountOfCrew;
    LocalDate preferenceTime;
    private Integer orderId;

    public Integer getRepairId() {
        return repairId;
    }

    public void setRepairId(Integer repairId) {
        this.repairId = repairId;
    }

    public RepairType getRepairType() {
        return repairType;
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

    public int getAmountOfCrew() {
        return amountOfCrew;
    }

    public void setAmountOfCrew(int amountOfCrew) {
        this.amountOfCrew = amountOfCrew;
    }

    public LocalDate getPreferenceTime() {
        return preferenceTime;
    }

    public void setPreferenceTime(LocalDate preferenceTime) {
        this.preferenceTime = preferenceTime;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
}
