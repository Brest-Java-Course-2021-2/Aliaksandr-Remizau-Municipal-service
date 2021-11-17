package com.epam.brest;

import java.time.LocalDate;
import java.util.Objects;

public class Repair {
    private Integer repairId;
    private RepairType repairType;
    private String address;
    private LevelOfDifficulty difficultyLevel;
    private LocalDate preferenceDate;
    private Integer clientId;

    public Repair(){
    }

    public Repair(RepairType repairType, String address, LevelOfDifficulty difficultyLevel, LocalDate preferenceDate) {
        this.repairType = repairType;
        this.address = address;
        this.difficultyLevel = difficultyLevel;
        this.preferenceDate = preferenceDate;
    }

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

    public Integer getClientId() {
        return clientId;
    }

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
